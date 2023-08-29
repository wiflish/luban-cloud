package com.wiflish.luban.core.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * @author wiflish
 * @since 2023-08-29
 */
@Getter
@Setter
public class Pager {
    @Min(1)
    @Parameter(description = "页号要大于0", schema = @Schema(type = "integer", defaultValue = "1"))
    private Integer page;

    @Min(1)
    @Parameter(description = "每页记录数最低为1", schema = @Schema(type = "integer", defaultValue = "20"))
    private Integer size;

    public Pager(int page, int size) {
        this.page = page;
        this.size = size;
    }
}