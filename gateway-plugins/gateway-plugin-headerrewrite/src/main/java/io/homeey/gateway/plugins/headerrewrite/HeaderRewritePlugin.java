package io.homeey.gateway.plugins.headerrewrite;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.context.ExecutionAttributes;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.plugin.PluginConfig;
import io.homeey.gateway.plugin.api.plugin.PluginResult;
import io.homeey.gateway.plugin.api.plugin.SyncGatewayPlugin;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * 明天的你会因今天的努力而幸运
 *
 * @author jt4mrg@qq.com
 * @since 0:08 2025-12-09
 **/
public class HeaderRewritePlugin implements SyncGatewayPlugin {

    private Map<String, String> requestAdd = Collections.emptyMap();
    private Map<String, String> responseAdd = Collections.emptyMap();

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

    @SuppressWarnings("unchecked")
    @Override
    public void init(PluginConfig config) throws GatewayException {
        this.requestAdd = (Map<String, String>) config.configData().getOrDefault("requestAdd", null);
        this.responseAdd = (Map<String, String>) config.configData().getOrDefault("responseAdd", null);
    }

    @Override
    public void destroy() {
        //do nothing
    }


    @Override
    public PluginResult executeSync(GatewayContext context) throws Exception {
        switch (context.getCurrentPhase()) {
            case BEFORE_ROUTING -> {
                System.out.println("Before routing");
                if (requestAdd != null) {
                    requestAdd.forEach((k, v) -> {
                        ExecutionAttributes attributes = context.getAttributes();
                        attributes.put(k, v);
                    });
                }
            }
            case AFTER_UPSTREAM -> {
                System.out.println("After upstream");
                if (responseAdd != null) {
                    responseAdd.forEach((k, v) -> {
                        context.getResponse().setHeaders(k, v);
                    });
                }
            }
            default -> {
            }
        }
        return PluginResult.success();
    }
}
