package io.homeey.gateway.core.context;

import io.homeey.gateway.plugin.api.context.GatewayResponse;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:48 2025-12-06
 **/
public class DefaultResponse implements GatewayResponse {
    private int statusCode = 200;
    private final Map<String, String> headers = new HashMap<>();
    private final OutputStream bodyOutput;

    public DefaultResponse(OutputStream bodyOutput) {
        this.bodyOutput = bodyOutput;
    }

    @Override
    public void setStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int getStatus() {
        return this.statusCode;
    }

    @Override
    public void setHeaders(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public OutputStream getBodyOutput() {
        return bodyOutput;
    }
}
