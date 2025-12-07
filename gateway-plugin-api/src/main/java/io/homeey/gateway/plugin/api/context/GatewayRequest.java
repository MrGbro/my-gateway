package io.homeey.gateway.plugin.api.context;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 0:00 2025-12-06
 **/
public interface GatewayRequest {
    /**
     * 获取HTTP请求方法
     *
     * @return 请求方法，如GET、POST等
     */
    String method();

    /**
     * 获取请求路径
     *
     * @return 请求的URL路径部分
     */
    String path();

    /**
     * 获取查询参数
     *
     * @return 包含所有查询参数的键值对映射
     */
    Map<String, List<String>> queryParams();

    /**
     * 根据名称获取请求头
     *
     * @param name 请求头名称
     * @return 对应的请求头值，如果不存在则返回null
     */
    String getHeader(String name);

    /**
     * 获取所有请求头
     *
     * @return 包含所有请求头的多值映射
     */
    Map<String, List<String>> headers();

    /**
     * 获取请求体输入流
     *
     * @return 请求体的输入流对象
     */
    InputStream body();
}
