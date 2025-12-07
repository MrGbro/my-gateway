package io.homeey.gateway.config.routing;

import io.homeey.gateway.routing.api.RouteTable;

import java.util.concurrent.atomic.AtomicReference;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 21:40 2025-12-07
 **/
public class RouteTableHolder {
    private final AtomicReference<RouteTable> ref = new AtomicReference<>();

    public RouteTable current() {
        return ref.get();
    }

    public void update(RouteTable newTable) {
        ref.set(newTable);
    }
}
