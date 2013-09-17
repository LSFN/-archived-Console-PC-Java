package org.lsfn.console_pc.data_store.local;

import java.awt.image.BufferedImage;

import org.lsfn.console_pc.data_store.sinks.ISinkBufferedImage;
import org.lsfn.console_pc.data_store.sources.ISourceBufferedImage;

public class LocalBufferedImage implements ISourceBufferedImage, ISinkBufferedImage {

    private BufferedImage bufferedImage;
    
    public LocalBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
    
    @Override
    public BufferedImage getData() {
        return this.bufferedImage;
    }

}
