package io.homeey.gateway.routing.api;

import java.util.List;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:12 2025-12-06
 **/
public interface Upstream {
    /**
     * 获取上游服务名称
     * 
     * @return 上游服务名称
     */
    String getName();

    /**
     * 获取上游服务端点列表
     * 
     * @return 上游服务端点URL列表
     */
    List<String> getEndpoints();
}
