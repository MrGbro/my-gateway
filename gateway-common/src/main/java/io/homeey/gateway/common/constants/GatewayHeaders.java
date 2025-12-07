package io.homeey.gateway.common.constants;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:54 2025-12-05
 **/
public class GatewayHeaders {
    private GatewayHeaders() {
    }

    public static final String X_GATEWAY_REQUEST_ID = "X-Request-Id";

    private static final String X_GATEWAY_TRACKING_ID = "X-Trace-Id";

    public static final String X_GATEWAY_SPAN_ID= "X-Span-Id";
}
