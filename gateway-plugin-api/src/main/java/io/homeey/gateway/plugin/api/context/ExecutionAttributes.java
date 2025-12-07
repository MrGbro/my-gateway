package io.homeey.gateway.plugin.api.context;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 13:15 2025-12-06
 **/
public interface ExecutionAttributes {
    /**
     * 将指定的键值对存储到执行属性中
     *
     * @param key   属性的键，不能为null
     * @param value 属性的值，可以为null
     */
    void put(String key, Object value);

    /**
     * 根据指定的键获取对应的值
     *
     * @param key 属性的键，不能为null
     * @param <T> 值的类型
     * @return 返回与指定键关联的值，如果不存在则返回null
     */
    <T> T get(String key);

    /**
     * 根据指定的键移除对应的键值对
     *
     * @param key 属性的键，不能为null
     * @return 返回被移除的值，如果不存在则返回null
     */
    Object remove(String key);

    /**
     * 检查是否包含指定的键
     *
     * @param key 属性的键，不能为null
     * @return 如果包含指定的键则返回true，否则返回false
     */
    boolean contains(String key);
}
