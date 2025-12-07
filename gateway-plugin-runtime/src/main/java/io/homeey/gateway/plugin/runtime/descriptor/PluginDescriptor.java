package io.homeey.gateway.plugin.runtime.descriptor;

import io.homeey.gateway.plugin.api.plugin.GatewayPlugin;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 20:52 2025-12-07
 **/
public record PluginDescriptor(String id, String name, String version, Class<? extends GatewayPlugin> pluginClass,
                               ClassLoader classLoader) {
}
