package io.homeey.gateway.plugin.runtime.config;

import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 20:52 2025-12-07
 **/
public record PluginConfigModel(String pluginId, boolean enabled, Map<String, Object> config) {
}
