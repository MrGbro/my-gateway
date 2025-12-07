package io.homeey.gateway.config.routing;

import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.routing.api.Upstream;

import java.util.List;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 21:36 2025-12-07
 **/
public record DefaultRoute(String id, String host, String path, String method, Upstream upstream,
                           List<PluginBinding> pluginBindings) implements Route {

}
