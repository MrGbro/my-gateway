package io.homeey.gateway.plugin.runtime.manager;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.plugin.GatewayPlugin;
import io.homeey.gateway.plugin.api.plugin.PluginConfig;
import io.homeey.gateway.plugin.runtime.config.PluginConfigModel;
import io.homeey.gateway.plugin.runtime.descriptor.PluginDescriptor;
import io.homeey.gateway.routing.api.Route;
import io.homeey.gateway.runtime.api.PluginManager;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:25 2025-12-06
 **/
public class DefaultPluginManager implements PluginManager {
    /**
     * pluginId->LoadedPlugin
     */
    private final Map<String, LoadedPlugin> plugins = new HashMap<>();
    /**
     * routeId->phase->pluginIds
     */
    private final Map<String, Map<ExecutionPhase, List<String>>> routePhasePluginIds = new HashMap<>();

    @Override
    public List<GatewayPlugin> getPluginsForRouteAndPhase(Route route, ExecutionPhase phase) {
        Map<ExecutionPhase, List<String>> phaseListMap = routePhasePluginIds.get(route.id());
        if (phaseListMap == null) {
            return Collections.emptyList();
        }
        return phaseListMap.getOrDefault(phase, Collections.emptyList())
                .stream()
                .map(plugins::get)
                .filter(Objects::nonNull)
                .filter(it -> it.enabled)
                .map(it -> it.instance)
                .sorted(Comparator.comparing(GatewayPlugin::getOrder))
                .toList();
    }

    public void registerPlugin(PluginDescriptor descriptor) {
        try {
            GatewayPlugin gatewayPlugin = descriptor.pluginClass()
                    .getDeclaredConstructor()
                    .newInstance();
            LoadedPlugin loadedPlugin = new LoadedPlugin(descriptor, gatewayPlugin);
            plugins.put(descriptor.id(), loadedPlugin);
        } catch (Exception e) {
            throw new GatewayException("Failed to instantiate plugin: " + descriptor.id(), e);
        }
    }

    public void initOrUpdatePluginConfig(String pluginId, PluginConfigModel cfgModel) {
        LoadedPlugin loadedPlugin = plugins.get(pluginId);
        if (loadedPlugin == null) {
            throw new GatewayException("Plugin not found: " + pluginId);
        }
        try {
            if (loadedPlugin.initialized) {
                loadedPlugin.instance.destroy();
            }
            PluginConfig config = new SimplePluginConfig(cfgModel.config());
            loadedPlugin.instance.init(config);
            loadedPlugin.enabled = cfgModel.enabled();
            loadedPlugin.configModel = cfgModel;
            loadedPlugin.initialized = true;
        } catch (Exception e) {
            throw new GatewayException("Failed to init plugin config: " + pluginId, e);
        }
    }


    public void rebuildRoutePlugins(List<Route> routes) {
        ConcurrentHashMap<String, Map<ExecutionPhase, List<String>>> newCache = new ConcurrentHashMap<>();
        for (Route route : routes) {
            EnumMap<ExecutionPhase, List<String>> phaseMap = new EnumMap<ExecutionPhase, List<String>>(ExecutionPhase.class);
            if (route.pluginBindings() != null) {
                for (Route.PluginBinding binding : route.pluginBindings()) {
                    if (!binding.enabled()) {
                        continue;
                    }
                    LoadedPlugin lp = plugins.get(binding.pluginId());
                    if (lp == null || !lp.enabled) {
                        continue;
                    }
                    for (ExecutionPhase phase : lp.instance.getSupportedPhases()) {
                        phaseMap.computeIfAbsent(phase, k -> new ArrayList<>())
                                .add(binding.pluginId());
                    }
                }
            }
            newCache.put(route.id(), phaseMap);
        }
        routePhasePluginIds.clear();
        routePhasePluginIds.putAll(newCache);
    }

    public void rebuildRoutePlugins(Route route) {
        EnumMap<ExecutionPhase, List<String>> phaseMap = new EnumMap<ExecutionPhase, List<String>>(ExecutionPhase.class);
        if (route.pluginBindings() != null) {
            for (Route.PluginBinding binding : route.pluginBindings()) {
                if (!binding.enabled()) {
                    continue;
                }
                LoadedPlugin lp = plugins.get(binding.pluginId());
                if (lp == null || !lp.enabled) {
                    continue;
                }
                for (ExecutionPhase phase : lp.instance.getSupportedPhases()) {
                    phaseMap.computeIfAbsent(phase, k -> new ArrayList<>())
                            .add(binding.pluginId());
                }
            }
        }
        routePhasePluginIds.put(route.id(), phaseMap);
    }

    private static class LoadedPlugin {
        final PluginDescriptor descriptor;
        final GatewayPlugin instance;

        volatile boolean initialized;
        volatile boolean enabled;
        volatile PluginConfigModel configModel;

        public LoadedPlugin(PluginDescriptor descriptor, GatewayPlugin instance) {
            this.descriptor = descriptor;
            this.instance = instance;
        }
    }

    static class SimplePluginConfig implements PluginConfig {
        private final Map<String, Object> values;

        public SimplePluginConfig(Map<String, Object> values) {
            this.values = values != null ? values : Collections.emptyMap();
        }

        @SuppressWarnings("unchecked")
        public <T> T get(String key) {
            return (T) values.get(key);
        }

        public Map<String, Object> getAll() {
            return values;
        }
    }
}
