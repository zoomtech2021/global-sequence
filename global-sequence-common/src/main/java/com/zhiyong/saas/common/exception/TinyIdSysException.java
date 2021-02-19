package com.zhiyong.saas.common.exception;

/**
 * @author du_imba
 */
public class TinyIdSysException extends RuntimeException {

    public TinyIdSysException() {
        super();
    }

    public TinyIdSysException(String message) {
        super(message);
    }

    public TinyIdSysException(String message, Throwable cause) {
        super(message, cause);
    }

    public TinyIdSysException(Throwable cause) {
        super(cause);
    }
}
