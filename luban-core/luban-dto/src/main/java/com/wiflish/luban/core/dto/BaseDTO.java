package com.wiflish.luban.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * @author wiflish
 * @since 2023-11-08
 */
@Getter
@Setter
public class BaseDTO extends DTO {
    @Serial
    private static final long serialVersionUID = 8135849640271505259L;

    private Long id;
    private Long createId;
    private Long updateId;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long deleted;
}
