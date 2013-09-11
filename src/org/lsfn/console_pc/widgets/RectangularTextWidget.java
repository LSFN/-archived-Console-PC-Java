package org.lsfn.console_pc.widgets;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.List;

import org.lsfn.console_pc.data_store.DataPath;
import org.lsfn.console_pc.data_store.IDataStore;
import org.lsfn.console_pc.data_store.sinks.ISinkString;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2;
import org.lsfn.console_pc.screen.ScreenFile2.ScreenConfig2.WidgetLayout.WidgetType;

public class RectangularTextWidget implements IWidget {
    
    private boolean verticalWidget;
    private Integer ratio;
    private Integer ratioSum;
    private String name;
    private List<IWidget> subWidgets;
    private Color colour;
    private String text;
    private Color textColour;
    private ISinkString dataSource;
    
    private Rectangle bounds;

    public RectangularTextWidget(ScreenConfig2.WidgetLayout widgetLayout, IDataStore dataStore) {
        this.verticalWidget = widgetLayout.getVertical();
        this.ratio = widgetLayout.getRatio();
        
        if(widgetLayout.hasName()) this.name = widgetLayout.getName();
        if(widgetLayout.hasColour()) this.colour = Color.decode(widgetLayout.getColour());
        if(widgetLayout.hasText()) this.text = widgetLayout.getText();
        if(widgetLayout.hasTextColour()) this.textColour = Color.decode(widgetLayout.getTextColour());
        if(widgetLayout.hasStringDataPath()) this.dataSource = dataStore.findSinkString(new DataPath(widgetLayout.getStringDataPath()));
        
        this.ratioSum = 0;
        for(ScreenConfig2.WidgetLayout subWidgetLayout : widgetLayout.getWidgetLayoutList()) {
            if(subWidgetLayout.getWidgetType() == WidgetType.RECTANGULAR_TEXT) {
                this.subWidgets.add(new RectangularTextWidget(subWidgetLayout, dataStore));
            }
            this.ratioSum += subWidgetLayout.getRatio();
        }
    }
    
    @Override
    public void updateBounds(Rectangle bounds) {
        this.bounds = bounds;
        
        // Calculate the bounds of the sub widgets
        if(this.verticalWidget) {
            // The width of the resulting bounds rectangle will be the same
            int y = bounds.y;
            double baseHeight = bounds.getHeight() / this.ratioSum;
            for(IWidget widget : this.subWidgets) {
                int height = (int)(widget.getRatio() * baseHeight);
                Rectangle subWidgetBounds = new Rectangle(bounds.x, y, bounds.width, height);
                widget.updateBounds(subWidgetBounds);
                y += height;
            }
        } else {
            // The height of the resulting bounds rectangle will be the same
            int x = bounds.x;
            double baseWidth = bounds.getWidth() / this.ratioSum;
            for(IWidget widget : this.subWidgets) {
                int width = (int)(widget.getRatio() * baseWidth);
                Rectangle subWidgetBounds = new Rectangle(x, bounds.y, width, bounds.height);
                widget.updateBounds(subWidgetBounds);
                x += width;
            }
        }
    }

    @Override
    public void drawWidget(Graphics2D g) {
        g.setColor(this.colour);
        g.fill(this.bounds);
        if(this.text != null) {
            g.setColor(this.textColour);
            Rectangle2D stringRect = g.getFontMetrics().getStringBounds(this.text, g);
            g.drawString(this.text, (int)(bounds.getCenterX() - stringRect.getWidth()/2), (int)(bounds.getCenterY() - stringRect.getHeight()/2));
        }
        if(this.dataSource != null) {
            g.setColor(this.textColour);
            String stringData = this.dataSource.getData();
            Rectangle2D stringRect = g.getFontMetrics().getStringBounds(stringData, g);
            g.drawString(stringData, (int)(bounds.getCenterX() - stringRect.getWidth()/2), (int)(bounds.getCenterY() - stringRect.getHeight()/2));
        }
    }

    @Override
    public IWidgetInfo getWidgetInfoForPoint(Point point) {
        if(this.bounds.contains(point)) {
            Iterator<IWidget> iterator = this.subWidgets.iterator();
            IWidgetInfo subWidgetInfo = null;
            while(subWidgetInfo == null && iterator.hasNext()) {
                IWidget subWidget = iterator.next();
                subWidgetInfo = subWidget.getWidgetInfoForPoint(point);
            }
            if(this.name != null) {
                if(subWidgetInfo == null) {
                    Point pt = new Point(point.x - this.bounds.x, point.y - this.bounds.y);
                    subWidgetInfo = new WidgetInfo(this.name, 0.0, pt);
                } else {
                    subWidgetInfo.wrapWidgetPath(this.name);
                }
            }
            return subWidgetInfo;
        } else {
            return null;
        }
    }

    @Override
    public int getRatio() {
        return this.ratio;
    }

}
