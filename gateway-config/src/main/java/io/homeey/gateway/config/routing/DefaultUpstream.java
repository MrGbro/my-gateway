package io.homeey.gateway.config.routing;

import io.homeey.gateway.routing.api.Upstream;

import java.util.List;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 21:39 2025-12-07
 **/
public record DefaultUpstream(String name, List<String> endpoints) implements Upstream {
}
