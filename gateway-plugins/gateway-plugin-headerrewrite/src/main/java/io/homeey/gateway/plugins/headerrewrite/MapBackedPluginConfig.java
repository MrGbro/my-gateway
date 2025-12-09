package io.homeey.gateway.plugins.headerrewrite;

import io.homeey.gateway.plugin.api.plugin.PluginConfig;

import java.util.Collections;
import java.util.Map;

/**
 * 明天的你会因今天的努力而幸运
 *
 * @author jt4mrg@qq.com
 * @since 0:10 2025-12-09
 **/
public class MapBackedPluginConfig implements PluginConfig {
    private final Map<String, Object> config;

    public MapBackedPluginConfig(Map<String, Object> config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getMapDefault(String key) {
        Object value = config.get(key);
        if (value instanceof Map<?, ?> map) {
            return (Map<String, String>) map;
        }
        return Collections.emptyMap();
    }
}
