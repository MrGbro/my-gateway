package io.homeey.gateway.transport.netty.bridge;

import io.homeey.gateway.plugin.api.context.GatewayRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 22:06 2025-12-07
 **/
public class NettyGatewayRequest implements GatewayRequest {

    private final String method;
    private final String path;
    private final Map<String, List<String>> queryParams;
    private final Map<String, List<String>> headers;
    private final byte[] bodyBytes;

    public NettyGatewayRequest(FullHttpRequest request) {
        this.method = request.method().name();
        String uri = request.uri();
        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(uri);
        this.path = queryStringDecoder.path();
        this.queryParams = Collections.unmodifiableMap(queryStringDecoder.parameters());

        Map<String, List<String>> headerMap = new HashMap<>();
        request.headers()
                .forEach(h -> headerMap.computeIfAbsent(h.getKey(), k -> new ArrayList<>())
                        .add(h.getValue()));

        this.headers = Collections.unmodifiableMap(headerMap);
        if (request.content().isReadable()) {
            bodyBytes = new byte[request.content().readableBytes()];
            request.content().readBytes(bodyBytes);
        } else {
            bodyBytes = new byte[0];
        }
    }

    @Override
    public String method() {
        return method;
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public Map<String, List<String>> queryParams() {
        return this.queryParams;
    }

    @Override
    public String getHeader(String name) {
        List<String> values = headers.get(name);
        return (values == null || values.isEmpty()) ? null : values.get(0);
    }

    @Override
    public Map<String, List<String>> headers() {
        return headers;
    }

    @Override
    public InputStream body() {
        return new ByteArrayInputStream(bodyBytes);
    }

    public String getBodyAsString() {
        return new String(bodyBytes, StandardCharsets.UTF_8);
    }
}
