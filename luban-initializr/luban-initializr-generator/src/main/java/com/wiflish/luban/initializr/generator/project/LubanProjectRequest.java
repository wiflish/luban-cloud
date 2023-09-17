package com.wiflish.luban.initializr.generator.project;

import io.spring.initializr.web.project.WebProjectRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-08-25
 */
@Getter
@Setter
public class LubanProjectRequest extends WebProjectRequest {
    private String author;
    private String architecture;
    private String port;
}
