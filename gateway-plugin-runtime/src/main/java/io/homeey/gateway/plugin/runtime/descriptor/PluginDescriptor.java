package io.homeey.gateway.plugin.runtime.descriptor;

import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.plugin.GatewayPlugin;

import java.util.Set;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 20:52 2025-12-07
 **/
public record PluginDescriptor(String id,
                               String name,
                               String version,
                               String description,
                               Class<? extends GatewayPlugin> pluginClass,
                               ClassLoader classLoader,
                               Set<ExecutionPhase> supportedPhases,
                               boolean blocking) {
}
