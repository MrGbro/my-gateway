package io.homeey.gateway.transport.netty.bridge;

import io.homeey.gateway.plugin.api.context.GatewayResponse;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 22:06 2025-12-07
 **/
public class NettyGatewayResponse implements GatewayResponse {

    private int statusCode = 200;
    private final Map<String, String> headers = new HashMap<>();
    private final ByteArrayOutputStream bodyOut = new ByteArrayOutputStream();

    @Override
    public void setStatus(int statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public int getStatus() {
        return statusCode;
    }

    @Override
    public void setHeaders(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public Map<String, String> getHeaders() {
        return Collections.unmodifiableMap(headers);
    }

    @Override
    public OutputStream getBodyOutput() {
        return bodyOut;
    }

    public byte[] getBodyBytes() {
        return bodyOut.toByteArray();
    }
}
