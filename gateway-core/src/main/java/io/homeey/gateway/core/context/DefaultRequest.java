package io.homeey.gateway.core.context;

import io.homeey.gateway.plugin.api.context.GatewayRequest;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:48 2025-12-06
 **/
public record DefaultRequest(String method, String path, Map<String, List<String>> queryParams,
                             Map<String, List<String>> headers, InputStream body) implements GatewayRequest {

    @Override
    public String getHeader(String name) {
        List<String> values = headers.get(name);
        return (values == null || values.isEmpty()) ? null : values.get(0);
    }

    @Override
    public Map<String, List<String>> headers() {
        return this.headers;
    }

    @Override
    public InputStream body() {
        return this.body;
    }
}
