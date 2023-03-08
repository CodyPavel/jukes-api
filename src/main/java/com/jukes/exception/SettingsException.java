package com.jukes.exception;

public class SettingsException extends RuntimeException {

    public SettingsException() {
        super();
    }

    public SettingsException(Throwable exception) {
        super(exception);
    }

    public SettingsException(String exceptionMessage) {
        super(exceptionMessage);
    }

    public SettingsException(String message, Throwable exception) {
        super(message, exception);
    }

}