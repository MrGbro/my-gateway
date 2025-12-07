package io.homeey.gateway.plugin.api.context;

import io.homeey.gateway.plugin.api.ExecutionPhase;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:11 2025-12-06
 **/
public interface GatewayContext {
    /**
     * 获取网关请求对象
     *
     * @return 网关请求对象
     */
    GatewayRequest getRequest();

    /**
     * 获取网关响应对象
     *
     * @return 网关响应对象
     */
    GatewayResponse getResponse();

    /**
     * 获取路由ID
     *
     * @return 路由ID
     */
    String getRouterId();

    /**
     * 设置路由ID
     *
     * @param routerId 路由ID
     */
    void setRouterId(String routerId);

    /**
     * 获取当前执行阶段
     *
     * @return 当前执行阶段
     */
    ExecutionPhase getCurrentPhase();

    /**
     * 设置当前执行阶段
     *
     * @param phase 执行阶段
     */
    void setCurrentPhase(ExecutionPhase phase);

    /**
     * 获取执行属性
     *
     * @return 执行属性集合
     */
    ExecutionAttributes getAttributes();

    /**
     * 判断是否在事件循环中
     *
     * @return 如果在事件循环中返回true，否则返回false
     */
    boolean isInEventLoop();

    /**
     * 获取开始时间（纳秒）
     *
     * @return 开始时间戳（纳秒）
     */
    long getStartTimeNanos();

    /**
     * 获取错误信息
     *
     * @return 异常对象，如果没有错误则返回null
     */
    Throwable getError();

    /**
     * 设置错误信息
     *
     * @param error 异常对象
     */
    void setError(Throwable error);
}
