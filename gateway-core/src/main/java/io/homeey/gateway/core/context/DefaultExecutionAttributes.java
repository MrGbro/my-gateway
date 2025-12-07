package io.homeey.gateway.core.context;

import io.homeey.gateway.plugin.api.context.ExecutionAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:48 2025-12-06
 **/
public class DefaultExecutionAttributes implements ExecutionAttributes {
    private final Map<String, Object> attributes = new HashMap<>();

    @Override
    public void put(String key, Object value) {
        attributes.put(key, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key) {
        return (T) attributes.get(key);
    }

    @Override
    public Object remove(String key) {
        return attributes.remove(key);
    }

    @Override
    public boolean contains(String key) {
        return attributes.containsKey(key);
    }

    public void clear() {
        attributes.clear();
    }
}
