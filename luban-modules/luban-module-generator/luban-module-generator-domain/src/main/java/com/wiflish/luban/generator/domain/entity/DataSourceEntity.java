package com.wiflish.luban.generator.domain.entity;

import com.wiflish.luban.core.domain.entity.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

/**
 * @author wiflish
 * @since 2023-10-10
 */
@Setter
@Getter
public class DataSourceEntity extends Entity {
    @Serial
    private static final long serialVersionUID = 449764082978501538L;

    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
}
