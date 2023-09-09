package com.wiflish.luban.core.dto;

import com.wiflish.luban.core.dto.constant.BaseConstant;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * 分页对象.
 *
 * @author wiflish
 * @since 2023-08-29
 */
@Getter
@Setter
public class Pager {
    /**
     * 是否需要总记录数.
     */
    private Boolean needTotalCount = false;

    @Min(1)
    @Parameter(description = "页号要大于0", schema = @Schema(type = "integer", defaultValue = "1"))
    private Integer page = BaseConstant.DEFAULT_PAGE_NO;

    @Min(1)
    @Parameter(description = "每页记录数最低为1", schema = @Schema(type = "integer", defaultValue = "20"))
    private Integer size = BaseConstant.DEFAULT_PAGE_SIZE;

    public Pager() {
    }

    public Pager(int page, int size) {
        this.page = page;
        this.size = size;
    }
    public Pager(int page, int size, boolean needTotalCount) {
        this.page = page;
        this.size = size;
        this.needTotalCount = needTotalCount;
    }
}