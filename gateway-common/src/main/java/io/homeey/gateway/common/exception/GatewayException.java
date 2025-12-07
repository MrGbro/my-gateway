package io.homeey.gateway.common.exception;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:48 2025-12-05
 **/
public class GatewayException extends RuntimeException{

    public GatewayException() {
    }

    public GatewayException(String message) {
        super(message);
    }

    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }

    public GatewayException(Throwable cause) {
        super(cause);
    }
}
