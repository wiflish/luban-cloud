package com.wiflish.luban.initializr.generator.constants;

import lombok.Getter;

import java.util.Arrays;

/**
 * @author wiflish
 * @since 2023-08-25
 */
@Getter
public enum ArchitectureEnum {
    DDD("ddd", "基于ddd的微服务架构"),
    MVC("mvc", "mvc架构"),
    NONE("none", "Spring Initializr默认的架构"),
    ;

    private final String id;
    private final String desc;

    ArchitectureEnum(String id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public static ArchitectureEnum getArchitecture(String architecture) {
        return Arrays.stream(ArchitectureEnum.values())
                .filter(architect -> architect.name().equalsIgnoreCase(architecture)).findFirst().orElse(NONE);
    }
}
