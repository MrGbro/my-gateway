package io.homeey.gateway.common.exception;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 18:52 2025-12-06
 **/
public class PluginException extends RuntimeException {
    public PluginException(String message) {
        super(message);
    }

    public PluginException(String message, Throwable cause) {
        super(message, cause);
    }
}
