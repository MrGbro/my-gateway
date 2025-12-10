package io.homeey.gateway.plugin.api.plugin;

import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:28 2025-12-06
 **/
public interface PluginConfig {
    /**
     * 获取插件配置数据
     * todo 修复数据
     *
     * @return 插件配置数据
     */
    Map<String, Object> configData();
}
