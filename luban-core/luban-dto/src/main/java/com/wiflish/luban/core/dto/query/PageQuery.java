/*-
 * ************
 * luban-cloud
 * ------------
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ************
 */
package com.wiflish.luban.core.dto.query;

import com.wiflish.luban.core.dto.constant.BaseConstant;

import java.io.Serial;

/**
 * 分页查询参数.
 *
 * @author wiflish
 * @since 2022-03-17
 */
public class PageQuery extends Query {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer pageNo = BaseConstant.DEFAULT_PAGE_NO;
    private Integer pageSize = BaseConstant.DEFAULT_PAGE_SIZE;

    /**
     * 是否需要总记录数.
     */
    private Boolean needTotalCount = false;

    private Integer total;

    public Integer getPageNo() {
        return pageNo;
    }

    public PageQuery setPageNo(Integer pageNo) {
        this.pageNo = (pageNo == null || pageNo < 1) ? BaseConstant.DEFAULT_PAGE_NO : pageNo;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public PageQuery setPageSize(Integer pageSize) {
        this.pageSize = (pageSize == null || pageSize < 1) ? BaseConstant.DEFAULT_PAGE_SIZE : pageSize;

        return this;
    }

    public int getOffset() {
        if (this.pageNo == null || this.pageSize == null) {
            return 0;
        }
        return (this.pageNo - 1) * this.pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public PageQuery setTotal(Integer total) {
        this.total = total;
        return this;
    }

    public Boolean getNeedTotalCount() {
        return needTotalCount;
    }

    public PageQuery setNeedTotalCount(Boolean needTotalCount) {
        this.needTotalCount = (needTotalCount != null && needTotalCount);
        return this;
    }
}
