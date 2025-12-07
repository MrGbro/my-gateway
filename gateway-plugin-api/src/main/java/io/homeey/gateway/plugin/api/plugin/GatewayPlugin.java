package io.homeey.gateway.plugin.api.plugin;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.plugin.api.ExecutionPhase;

import java.util.Set;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:22 2025-12-06
 **/
public interface GatewayPlugin {
    String getId();

    String getName();

    String getVersion();

    default int getOrder() {
        return 100;
    }

    Set<ExecutionPhase> getSupportedPhases();

    void init(PluginConfig config) throws GatewayException;

    void destroy();
}

