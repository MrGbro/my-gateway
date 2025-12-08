package io.homeey.gateway.plugins.headerrewrite;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.plugin.PluginConfig;
import io.homeey.gateway.plugin.api.plugin.PluginResult;
import io.homeey.gateway.plugin.api.plugin.SyncGatewayPlugin;

import java.util.Set;

/**
 * 明天的你会因今天的努力而幸运
 *
 * @author jt4mrg@qq.com
 * @since 0:08 2025-12-09
 **/
public class HeaderRewritePlugin implements SyncGatewayPlugin {
    @Override
    public PluginResult executeSync(GatewayContext context) throws Exception {
        return null;
    }

    @Override
    public String getId() {
        return "header-rewrite";
    }

    @Override
    public String getName() {
        return "Header Rewrite Plugin";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public Set<ExecutionPhase> getSupportedPhases() {
        return Set.of(ExecutionPhase.BEFORE_ROUTING, ExecutionPhase.AFTER_UPSTREAM);
    }

    @Override
    public void init(PluginConfig config) throws GatewayException {
        //todo
    }

    @Override
    public void destroy() {
        //todo
    }
}
