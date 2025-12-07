package io.homeey.gateway.config.routing;

import io.homeey.gateway.routing.api.Route;

import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 21:39 2025-12-07
 **/
public record DefaultPluginBinding(String pluginId, boolean enabled,
                                   Map<String, Object> config) implements Route.PluginBinding {

    @Override
    public String pluginId() {
        return pluginId;
    }

    @Override
    public boolean enabled() {
        return enabled;
    }

    @Override
    public Map<String, Object> config() {
        return config;
    }
}
