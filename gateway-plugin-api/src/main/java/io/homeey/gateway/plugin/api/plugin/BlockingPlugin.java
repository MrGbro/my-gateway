package io.homeey.gateway.plugin.api.plugin;

import java.lang.annotation.*;

/**
 * 标记为阻塞插件
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:41 2025-12-06
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface BlockingPlugin {
}
