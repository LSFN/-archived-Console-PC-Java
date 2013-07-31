package org.lsfn.console_pc;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.List;

import org.lsfn.console_pc.STS.STSdown;

public interface View extends KeyListener, MouseListener {

    public void drawView(Graphics2D g, Rectangle bounds);
}
