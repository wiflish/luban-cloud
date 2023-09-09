package com.wiflish.luban.samples.ddd.domain.enums;

/**
 * @author wiflish
 * @since 2023-08-28
 */
public enum TaskStatusEnum {
    TODO(1, "待开始"),
    DOING(2, "进行中"),
    DONE(3, "已完成"),;
    private final String name;
    private final Integer value;

    TaskStatusEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }
}
