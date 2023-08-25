package com.wiflish.luban.initializr.generator.controller;

import com.wiflish.luban.initializr.generator.project.LubanProjectRequest;
import io.spring.initializr.metadata.InitializrMetadataProvider;
import io.spring.initializr.web.controller.ProjectGenerationController;
import io.spring.initializr.web.project.ProjectGenerationInvoker;

import java.util.Map;

/**
 * @author wiflish
 * @since 2023-08-25
 */
public class LubanProjectGenerationController extends ProjectGenerationController<LubanProjectRequest> {
    public LubanProjectGenerationController(InitializrMetadataProvider metadataProvider, ProjectGenerationInvoker<LubanProjectRequest> projectGenerationInvoker) {
        super(metadataProvider, projectGenerationInvoker);
    }

    @Override
    public LubanProjectRequest projectRequest(Map<String, String> headers) {
        LubanProjectRequest request = new LubanProjectRequest();
        request.getParameters().putAll(headers);
        request.initialize(getMetadata());

        return request;
    }
}
