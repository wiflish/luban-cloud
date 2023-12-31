package com.wiflish.luban.starter.wechat.dto.cmd;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.Map;

/**
 * @author wiflish
 * @since 2023-12-28
 */
@Data
public class WechatQrCodeCmd {
    private String scene;
    private String page;
    @JSONField(name="env_version")
    private String envVersion;
    private Integer width;

    @JSONField(name = "auto_color")
    private Boolean autoColor;

    @JSONField(name = "line_color")
    private Map<String, Integer> lineColor;

    @JSONField(name = "is_hyaline")
    private Boolean isHyaline;
}
