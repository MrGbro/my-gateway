package io.homeey.gateway.plugin.api.plugin;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:28 2025-12-06
 **/
public class PluginResult {
    /**
     * 插件执行是否成功
     */
    private final boolean success;

    /**
     * 是否继续执行后续插件链
     */
    private final boolean continueChain;

    /**
     * 提前返回的响应对象，如果不为null则会中断后续执行并返回此响应
     */
    private final Object earlyResponse;

    public PluginResult(boolean success, boolean continueChain, Object earlyResponse) {
        this.success = success;
        this.continueChain = continueChain;
        this.earlyResponse = earlyResponse;
    }

    public static PluginResult interrupt() {
        return new PluginResult(true, false, null);
    }

    public static PluginResult interruptWith(Object response) {
        return new PluginResult(true, false, response);
    }

    public static PluginResult failure() {
        return new PluginResult(false, false, null);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isContinueChain() {
        return continueChain;
    }

    public Object getEarlyResponse() {
        return earlyResponse;
    }
}
