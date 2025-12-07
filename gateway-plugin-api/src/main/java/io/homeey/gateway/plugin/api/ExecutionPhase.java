package io.homeey.gateway.plugin.api;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:14 2025-12-06
 **/
public enum ExecutionPhase {
    /**
     * 执行阶段：路由之前
     */
    BEFORE_ROUTING,

    /**
     * 执行阶段：路由之后
     */
    AFTER_ROUTING,

    /**
     * 执行阶段：上游调用之前
     */
    BEFORE_UPSTREAM,

    /**
     * 执行阶段：上游调用之后
     */
    AFTER_UPSTREAM,

    /**
     * 执行阶段：发生错误时
     */
    ON_ERROR,
}
