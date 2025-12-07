package io.homeey.gateway.config.routing;

import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.routing.api.RouteTable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 21:39 2025-12-07
 **/
public class DefaultRouteTable implements RouteTable {

    private final Map<String, Route> routesById;

    public DefaultRouteTable(Map<String, Route> routesById) {
        this.routesById = Collections.unmodifiableMap(routesById);
    }

    @Override
    public Route getById(String id) {
        return routesById.get(id);
    }

    @Override
    public List<Route> getAllRoutes() {
        return List.copyOf(routesById.values());
    }
}
