package com.vharia.screenshoot.exceptions;

public class ScreenShotServiceException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ScreenShotServiceException(String message, Exception e) {
        super(message, e);
    }

}