package io.homeey.gateway.plugin.runtime.loader;

import io.homeey.gateway.common.exception.GatewayException;
import io.homeey.gateway.plugin.api.ExecutionPhase;
import io.homeey.gateway.plugin.api.plugin.GatewayPlugin;
import io.homeey.gateway.plugin.runtime.descriptor.PluginDescriptor;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:41 2025-12-07
 **/
public class PluginLoader {
    private final ClassLoader parentClassLoader;

    public PluginLoader(ClassLoader parentClassLoader) {
        this.parentClassLoader = parentClassLoader;
    }

    public List<PluginDescriptor> loadFromDirectory(Path pluginDir) {
        List<PluginDescriptor> descriptors = new ArrayList<>();

        if (!Files.exists(pluginDir) || !Files.isDirectory(pluginDir)) {
            return descriptors;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pluginDir)) {
            for (Path jarPath : stream) {
                PluginDescriptor descriptor = loadSingleJar(jarPath);
                if (descriptor != null) {
                    descriptors.add(descriptor);
                }
            }
        } catch (Exception e) {
            throw new GatewayException("Failed to load plugins from directory: " + pluginDir, e);
        }
        return descriptors;
    }

    private PluginDescriptor loadSingleJar(Path jarPath) {
        try {
            URL url = jarPath.toUri().toURL();
            URLClassLoader pluginClassLoader = new URLClassLoader(new URL[]{url}, parentClassLoader);
            try (InputStream in = pluginClassLoader.getResourceAsStream("plugin.yaml")) {
                if (in == null) {
                    throw new GatewayException("plugin.yaml descriptor not found in: " + jarPath);
                }
                //解析yaml
                Yaml yaml = new Yaml();
                Map<String, Object> root = yaml.load(in);
                Map<String, Object> plugin = (Map<String, Object>) root.get("plugin");
                String id = (String) plugin.get("id");
                String name = (String) plugin.getOrDefault("name", id);
                String version = (String) plugin.getOrDefault("version", "1.0.0");
                String description = (String) plugin.getOrDefault("description", "");
                String entryClass = (String) plugin.get("entryClass");
                List<String> phaseNames = (List<String>) plugin.get("supportedPhase");
                boolean blocking = (boolean) plugin.getOrDefault("blocking", Boolean.FALSE);

                if (id == null || entryClass == null || phaseNames == null) {
                    throw new GatewayException("Invalid plugin descriptor in: " + jarPath);
                }

                //加载插件类
                Class<?> pluginClass = pluginClassLoader.loadClass(entryClass);
                if (!GatewayPlugin.class.isAssignableFrom(pluginClass)) {
                    throw new GatewayException("Entry class does not implement GatewayPlugin " + entryClass);
                }
                @SuppressWarnings("unchecked")
                Class<? extends GatewayPlugin> gatewayPluginClass = (Class<? extends GatewayPlugin>) pluginClass;
                Set<ExecutionPhase> supportedPhases = new LinkedHashSet<>();
                for (String phaseName : phaseNames) {
                    supportedPhases.add(ExecutionPhase.valueOf(phaseName));
                }
                return new PluginDescriptor(id,
                        name,
                        version,
                        description,
                        gatewayPluginClass,
                        pluginClassLoader,
                        supportedPhases,
                        blocking);
            }
        } catch (Exception e) {
            //日志框架待实现
            return null;
        }
    }
}
