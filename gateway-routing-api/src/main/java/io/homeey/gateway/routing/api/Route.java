package io.homeey.gateway.routing.api;

import java.util.List;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:07 2025-12-06
 **/
public interface Route {
    /**
     * 获取路由的唯一标识符
     *
     * @return 路由ID
     */
    String getId();

    /**
     * 获取目标主机地址
     *
     * @return 主机地址
     */
    String getHost();

    /**
     * 获取路由路径
     *
     * @return 路径信息
     */
    String getPath();

    /**
     * 获取HTTP方法
     *
     * @return HTTP方法 (如 GET, POST, PUT, DELETE等)
     */
    String getMethod();

    /**
     * 获取上游服务配置
     *
     * @return 上游服务对象
     */
    Upstream getUpstream();

    /**
     * 获取绑定的插件列表
     *
     * @return 插件绑定列表
     */
    List<PluginBinding> getPluginBindings();

    /**
     *
     * @author jt4mrg@gmail.com <Just for fun.Let's do it>
     * @since 23:13 2025-12-06
     **/
    interface PluginBinding {
        /**
         * 获取插件ID
         *
         * @return 插件ID字符串
         */
        String getPluginId();

        /**
         * 检查插件是否启用
         *
         * @return 如果插件已启用返回true，否则返回false
         */
        boolean isEnabled();

        /**
         * 获取插件配置信息
         *
         * @return 包含插件配置的键值对映射
         */
        Map<String, Object> getConfig();
    }
}
