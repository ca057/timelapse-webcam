/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.controller;

import gui.controller.exceptions.ControllerException;
import gui.view.applicationpane.imageviewer.ImageViewer;
import java.awt.image.BufferedImage;

/**
 *
 * @author ca
 */
public class ImageViewerController {

    private final ImageViewer imageViewer;

    public ImageViewerController() {
        this.imageViewer = new ImageViewer(this);
    }

    public boolean isCameraReadyForLiveImage() throws ControllerException {
        return false;
    }

    public BufferedImage getLiveImage() {
        return null;
    }

    public ImageViewer getImageViewer() {
        return imageViewer;
    }

}
