package org.lsfn.console_pc;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lsfn.console_pc.ScreenFile.ScreenConfig.WidgetLayout;

import com.google.protobuf.TextFormat;

public class Widget {

    private String name;
    private boolean verticalWidget;
    private List<Widget> subWidgets;
    private Integer ratio;
    private Integer ratioSum;
    private Color colour;
    private String text;
    private Integer spacing;
    
    private Rectangle bounds;
    
    public Widget(WidgetLayout layout) {
        this.verticalWidget = layout.getVertical();
        this.ratio = layout.getRatio();
        this.spacing = layout.getSpacing();
        
        if(layout.hasText()) {
            this.text = layout.getText();
        } else {
            this.text = null;
        }
        
        if(layout.hasColour()) {
            this.colour = Color.decode(layout.getColour());
        } else {
            this.colour = Color.BLACK;
        }
        
        this.subWidgets = new ArrayList<Widget>();
        this.ratioSum = 0;
        for(WidgetLayout subLayout : layout.getWidgetLayoutList()) {
            Widget widget = new Widget(subLayout);
            this.subWidgets.add(widget);
            this.ratioSum += widget.getRatio();
        }
    }
    
    public boolean isVerticalWidget() {
        return verticalWidget;
    }

    public Integer getThisWidgetRatio() {
        return ratio;
    }
    
    public List<Widget> getSubWidgets() {
        return subWidgets;
    }

    public Integer getRatio() {
        return ratio;
    }

    public Color getColour() {
        return colour;
    }

    public String getText() {
        return text;
    }

    public Integer getSpacing() {
        return spacing;
    }
    
    public Rectangle getBounds() {
        return this.bounds;
    }
    
    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
        
        // Calculate the bounds of the subwidgets
        if(this.verticalWidget) {
            // The width of the resulting bounds rectangle will be the same
            int x = (int)bounds.getX() + this.spacing;
            int width = (int)bounds.getWidth() - (2 * this.spacing);
            
            int y = (int)bounds.getY() + this.spacing;
            double baseHeight = (bounds.getHeight() - this.spacing) / this.ratioSum;
            for(Widget widget : this.subWidgets) {
                double rectangleTopDifference = widget.getRatio() * baseHeight;
                int height = (int)(rectangleTopDifference - this.spacing);
                Rectangle subWidgetBounds = new Rectangle(x, y, width, height);
                widget.setBounds(subWidgetBounds);
                y += rectangleTopDifference;
            }
        } else {
            // The height of the resulting bounds rectangle will be the same
            int y = (int)bounds.getY() + this.spacing;
            int height = (int)bounds.getHeight() - (2 * this.spacing);
            
            int x = (int)bounds.getX() + this.spacing;
            double baseWidth = (bounds.getWidth() - this.spacing) / this.ratioSum;
            for(Widget widget : this.subWidgets) {
                double rectangleLeftDifference = widget.getRatio() * baseWidth;
                int width = (int)(rectangleLeftDifference - this.spacing);
                Rectangle subWidgetBounds = new Rectangle(x, y, width, height);
                widget.setBounds(subWidgetBounds);
                y += rectangleLeftDifference;
            }
        }
    }
    
    public String getWidgetPath(Point p) {
        for(Widget widget : subWidgets) {
            if(widget.getBounds().contains(p)) {
                return this.name + "/" + widget.getWidgetPath(p);
            }
        }
        return this.name;
    }
    
    public void drawWidget(Graphics2D g) {
        // Draw this widget first
        g.setColor(this.colour);
        g.fill(bounds);
        if(this.text != null) {
            g.setColor(invertColour(this.colour));
            Rectangle2D stringRect = g.getFontMetrics().getStringBounds(this.text, g);
            g.drawString(this.text, (int)(bounds.getCenterX() - stringRect.getWidth()/2), (int)(bounds.getCenterY() - stringRect.getHeight()/2));
        }
        
        for(Widget widget : this.subWidgets) {
            widget.drawWidget(g);
        }
    }
    
    public static Widget loadWidgetFromFile(String widgetLayoutFileName) {
        FileReader widgetFileReader;
        try {
            widgetFileReader = new FileReader(widgetLayoutFileName);
        } catch (FileNotFoundException e) {
            return null;
        }
        WidgetLayout.Builder layout = WidgetLayout.newBuilder();
        try {
            TextFormat.merge(widgetFileReader, layout);
        } catch (IOException e) {
            return null;
        }
        
        return new Widget(layout.build());
    }
    
    private static Color invertColour(Color colour) {
        return new Color(255 - colour.getRed(), 255 - colour.getGreen(), 255 - colour.getBlue());
    }
}
