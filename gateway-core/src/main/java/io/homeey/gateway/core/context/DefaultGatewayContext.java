package io.homeey.gateway.core.context;

import io.homeey.gateway.plugin.api.*;
import io.homeey.gateway.plugin.api.context.ExecutionAttributes;
import io.homeey.gateway.plugin.api.context.GatewayContext;
import io.homeey.gateway.plugin.api.context.GatewayRequest;
import io.homeey.gateway.plugin.api.context.GatewayResponse;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:47 2025-12-06
 **/
public class DefaultGatewayContext implements GatewayContext {

    private final GatewayRequest request;
    private final GatewayResponse response;
    private final ExecutionAttributes attributes = new DefaultExecutionAttributes();
    private String routeId;
    private ExecutionPhase phase;
    private final boolean inEventLoop;
    private final long startTimeNanos;
    private Throwable error;

    public DefaultGatewayContext(GatewayRequest request,
                                 GatewayResponse response,
                                 boolean inEventLoop) {
        this.request = request;
        this.response = response;
        this.inEventLoop = inEventLoop;
        this.startTimeNanos = System.nanoTime();
    }

    @Override
    public GatewayRequest getRequest() {
        return request;
    }

    @Override
    public GatewayResponse getResponse() {
        return response;
    }

    @Override
    public String getRouterId() {
        return this.routeId;
    }

    @Override
    public void setRouterId(String routerId) {
        this.routeId = routerId;
    }

    @Override
    public ExecutionPhase getCurrentPhase() {
        return this.phase;
    }

    @Override
    public void setCurrentPhase(ExecutionPhase phase) {
        this.phase = phase;
    }

    @Override
    public ExecutionAttributes getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean isInEventLoop() {
        return this.inEventLoop;
    }

    @Override
    public long getStartTimeNanos() {
        return this.startTimeNanos;
    }

    @Override
    public Throwable getError() {
        return this.error;
    }

    @Override
    public void setError(Throwable error) {
        this.error = error;
    }
}
