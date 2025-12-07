package io.homeey.gateway.core.filter;

import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.filter.GatewayFilter;
import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.runtime.api.PluginManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:49 2025-12-06
 **/
public class FilterChainFactory {
    private final PluginManager pluginManager;
    private final ExecutorService businessExecutor;

    public FilterChainFactory(PluginManager pluginManager,
                              ExecutorService businessExecutor) {
        this.pluginManager = pluginManager;
        this.businessExecutor = businessExecutor;
    }

    public List<GatewayFilter> buildPreUpstreamFilters(Route route) {
        List<GatewayFilter> filters = new ArrayList<>();

        addPhaseFilters(filters, route, ExecutionPhase.BEFORE_ROUTING);
        addPhaseFilters(filters, route, ExecutionPhase.AFTER_ROUTING);
        addPhaseFilters(filters, route, ExecutionPhase.BEFORE_UPSTREAM);

        return filters;
    }

    private void addPhaseFilters(List<GatewayFilter> filters,
                                 Route route,
                                 ExecutionPhase phase) {
        pluginManager.getPluginsForRouteAndPhase(route, phase)
                .stream()
                .map(plugin -> new PluginFilterAdapter(plugin, phase, businessExecutor))
                .forEach(filters::add);
    }
}
