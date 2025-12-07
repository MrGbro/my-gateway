package io.homeey.gateway.common.exception;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:49 2025-12-05
 **/
public class ConfigurationException extends GatewayException{
    public ConfigurationException(String message) {
        super(message);
    }

    public ConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}
