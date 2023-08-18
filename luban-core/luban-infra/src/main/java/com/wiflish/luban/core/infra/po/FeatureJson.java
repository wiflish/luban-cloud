/*-
 * ************
 * luban-cloud
 * ************
 * Copyright (C) 2023 - 2023 the original author or authors.
 * ************
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
package com.wiflish.luban.core.infra.po;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;

import java.io.Serial;
import java.io.Serializable;
public class FeatureJson implements Serializable {
    @Serial
    private static final long serialVersionUID = 7276217004810051310L;

    private String value;

    public FeatureJson() {
    }

    public FeatureJson(String value) {
        this.value = value;
    }

    public <T> FeatureJson(T obj) {
        if (obj == null) {
            return;
        }
        this.value = JSON.toJSONString(obj, JSONWriter.Feature.WriteClassName);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public <T> void setValue(T obj) {
        if (obj == null) {
            return;
        }
        this.value = JSON.toJSONString(obj, JSONWriter.Feature.WriteClassName);
    }

    /**
     * 通过json结构包含的类型信息，直接返回对象.
     *
     * @return 返回指定对象.
     */
    public <T> T getObject() {
        try {
            Object object = JSON.parse(this.value, JSONReader.Feature.SupportAutoType);
            if (object instanceof JSONObject) {
                return null;
            }
            return (T) object;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将json结构字符串解析为Java对象。
     *
     * @param clazz
     * @return 返回java对象。如果解析出现异常，返回<span class="code">NULL</span>
     */
    public <T> T toJavaObject(Class<T> clazz) {
        try {
            return JSON.parseObject(value, clazz);
        } catch (Exception e) {
            return null;
        }
    }
}
