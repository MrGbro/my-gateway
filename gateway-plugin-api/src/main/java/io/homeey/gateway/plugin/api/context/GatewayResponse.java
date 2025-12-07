package io.homeey.gateway.plugin.api.context;

import java.io.OutputStream;
import java.util.Map;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:08 2025-12-06
 **/
public interface GatewayResponse {
    /**
     * 设置HTTP响应状态码
     *
     * @param statusCode HTTP状态码，如200、404、500等
     */
    void setStatus(int statusCode);

    /**
     * 获取HTTP响应状态码
     *
     * @return 当前设置的HTTP状态码
     */
    int getStatus();

    /**
     * 设置HTTP响应头
     *
     * @param name  头部字段名称
     * @param value 头部字段值
     */
    void setHeaders(String name, String value);

    /**
     * 获取所有HTTP响应头
     *
     * @return 包含所有响应头的Map集合，键为头部名称，值为头部值
     */
    Map<String, String> getHeaders();

    /**
     * 获取响应体输出流
     *
     * @return 用于写入响应体数据的OutputStream对象
     */
    OutputStream getBodyOutput();
}
