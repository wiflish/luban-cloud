package com.wiflish.luban.generator.enums;

import com.wiflish.luban.generator.CodeGenerator;
import com.wiflish.luban.generator.dto.GeneratorDTO;
import lombok.Getter;

import java.util.function.BiFunction;

/**
 * @author wiflish
 * @since 2023-11-19
 */
@Getter
public enum LubanGeneratorEnum {
    CONTROLLER("Controller.java", "app.controller", "templates/ftl/Controller.java.ftl", null),
    APP_SERVICE("AService.java", "api.service", "templates/ftl/Service.java.ftl", CodeGenerator.apiFunction),
    APP_SERVICE_IMPL("AServiceImpl.java", "app.service.impl", "templates/ftl/ServiceImpl.java.ftl", null),
    ASSEMBLER("Assembler.java", "app.assembler", "templates/ftl/Assembler.java.ftl", null),
    DTO("DTO.java", "api.dto", "templates/ftl/DTO.java.ftl", CodeGenerator.apiFunction),
    Cmd("EditCmd.java", "api.dto.cmd", "templates/ftl/Cmd.java.ftl", CodeGenerator.apiFunction),
    Query("Query.java", "api.dto.query", "templates/ftl/Query.java.ftl", CodeGenerator.apiFunction),
    Entity(".java", "domain.entity", "templates/ftl/Entity.java.ftl", CodeGenerator.entityFunction),
    Repository("Repository.java", "domain.repository", "templates/ftl/Repository.java.ftl", CodeGenerator.entityFunction),
    Converter("Converter.java", "infra.converter", "templates/ftl/Converter.java.ftl", null),
    QueryWrapper("QueryWrapper.java", "infra.query", "templates/ftl/QueryWrapper.java.ftl", null),
    Repository_IMPL("RepositoryImpl.java", "infra.repository.impl", "templates/ftl/RepositoryImpl.java.ftl", null),;

    private final String type;
    private final String packageName;
    private final String templatePath;
    private final BiFunction<String, GeneratorDTO, String> filePathFunction;

    LubanGeneratorEnum(String type, String packageName, String templatePath, BiFunction<String, GeneratorDTO, String> filePathFunction) {
        this.type = type;
        this.packageName = packageName;
        this.templatePath = templatePath;
        this.filePathFunction = filePathFunction;
    }
}
