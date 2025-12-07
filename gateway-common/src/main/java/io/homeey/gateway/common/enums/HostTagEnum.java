package io.homeey.gateway.common.enums;

/**
 *
 * @author jt4mrg@gmail.com <Just for fun.Let's do it>
 * @since 23:17 2025-12-07
 **/
public enum HostTagEnum {
    LOCALHOST("localhost/127.0.0.1"),
    ;

    private final String value;

    HostTagEnum(String value) {
        this.value = value;
    }


    /**
     * 根据给定的值查找匹配的HostTagEnum枚举项。
     * 匹配规则为：枚举项的value值以给定值开头或结尾。
     *
     * @param value 要匹配的字符串值
     * @return 匹配的HostTagEnum枚举项，如果没有找到匹配项则返回null
     */
    public static HostTagEnum fromValue(String value) {
        for (HostTagEnum tag : values()) {
            if (tag.value.startsWith(value) || tag.value.endsWith(value)) {
                return tag;
            }
        }
        return null;
    }
}
