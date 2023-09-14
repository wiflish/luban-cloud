package com.wiflish.luban.initializr.generator.project;

import io.spring.initializr.generator.project.MutableProjectDescription;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-08-28
 */
@Setter
@Getter
public class LubanProjectDescription extends MutableProjectDescription {
    private String author;
    private String architecture;
    private String port;

    public LubanProjectDescription() {
    }

    public LubanProjectDescription(String architecture) {
        super();
        this.architecture = architecture;
    }
}
