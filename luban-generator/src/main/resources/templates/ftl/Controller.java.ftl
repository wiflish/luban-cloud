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
package ${package.Parent}.app.controller;

import cn.hutool.core.collection.ListUtil;
import ${package.Parent}.domain.entity.${luban.entityName};
import ${package.Parent}.api.dto.${luban.entityName}DTO;
import ${package.Parent}.api.dto.query.${luban.entityName}Query;
import ${package.Parent}.api.dto.cmd.${luban.entityName}EditCmd;
import ${package.Parent}.api.service.${luban.entityName}AService;
import com.wiflish.luban.core.dto.ListResponse;
import com.wiflish.luban.core.dto.OneResponse;
import com.wiflish.luban.core.dto.Pager;
import com.wiflish.luban.core.dto.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ${table.comment!} Controller
 *
 * @author ${author}
 * @since ${date}
 */
@Tag(name = "${table.comment!}接口")
@RestController
@RequestMapping("${luban.mapping!}")
@RequiredArgsConstructor
public class ${luban.entityName}Controller {
    private final ${luban.entityName}AService<${luban.entityName}EditCmd, ${luban.entityName}Query, ${luban.entityName}DTO, ${luban.entityName}> ${luban.entityName}AService;

    /**
     * 新增${table.comment!}.
     *
     * @param ${luban.entityName}EditCmd ${luban.entityName}EditCmd
     * @return Response
     */
    @PostMapping("")
    @Operation(summary = "新增")
    @PreAuthorize("hasAuthority('${luban.mapping!}:POST')")
    public OneResponse<Long> add${luban.entityName}(@Valid @RequestBody ${luban.entityName}EditCmd ${luban.entityName}EditCmd) {
        ${luban.entityName}EditCmd.setPassword(passwordEncoder.encode(${luban.entityName}EditCmd.getPassword()));
        return ${luban.entityName}AService.save(${luban.entityName}EditCmd);
    }

    /**
     * 编辑${table.comment!}.
     *
     * @param ${luban.entityName}EditCmd ${luban.entityName}EditCmd
     * @return Response
     */
    @PutMapping("")
    @Operation(summary = "编辑")
    @PreAuthorize("hasAuthority('${luban.mapping!}:PUT')")
    public OneResponse<Long> edit${luban.entityName}(@Valid @RequestBody ${luban.entityName}EditCmd ${luban.entityName}EditCmd) {
        return ${luban.entityName}AService.save(${luban.entityName}EditCmd);
    }

    /**
     * 删除${table.comment!}.
     *
     * @param id id
     * @return Response
     */
    @DeleteMapping("{id}")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority('${luban.mapping!}:DELETE')")
    public Response remove${luban.entityName}(@Valid @PathVariable Long id) {
        return ${luban.entityName}AService.remove(ListUtil.of(id));
    }

    /**
     * 批量删除${table.comment!}.
     *
     * @param ids ids
     * @return Response
     */
    @DeleteMapping("batch")
    @Operation(summary = "删除批量")
    @PreAuthorize("hasAuthority('${luban.mapping!}:DELETE')")
    public Response remove${luban.entityName}s(@Valid @RequestBody List<Long> ids) {
        return ${luban.entityName}AService.remove(ids);
    }

    /**
     * 根据id查询${table.comment!}.
     *
     * @param id id
     * @return OneResponse
     */
    @GetMapping("{id}")
    @Operation(summary = "详情")
    @PreAuthorize("hasAuthority('${luban.mapping!}/detail:GET')")
    public OneResponse<${luban.entityName}DTO> get${luban.entityName}(@PathVariable Long id) {
        return ${luban.entityName}AService.get(id);
    }

    /**
     * 分页查询所有${table.comment!}.
     *
     * @param query ${luban.entityName}Query
     * @param pager Pager
     * @return ListResponse
     */
    @GetMapping("")
    @Operation(summary = "分页")
    @PreAuthorize("hasAuthority('${luban.mapping!}/list:GET')")
    public ListResponse<${luban.entityName}DTO> get${luban.entityName}s(${luban.entityName}Query query, Pager pager) {
        return ${luban.entityName}AService.listPage(query, pager);
    }
}
