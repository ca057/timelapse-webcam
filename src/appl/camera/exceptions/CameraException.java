/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appl.camera.exceptions;

/**
 *
 * @author ca
 */
public class CameraException extends Exception {

    public CameraException() {
    }

    public CameraException(String message) {
        super(message);
    }

    public CameraException(String message, Throwable cause) {
        super(message, cause);
    }

    public CameraException(Throwable cause) {
        super(cause);
    }

    public CameraException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
