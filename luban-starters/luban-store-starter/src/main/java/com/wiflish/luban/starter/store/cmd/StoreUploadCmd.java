package com.wiflish.luban.starter.store.cmd;

import lombok.Data;

/**
 * @author xiezhengrong
 * @since 2023-06-05
 */
@Data
public class StoreUploadCmd {
    /**
     * 上传的bucket名称
     */
    private String name;

    /**
     * 指定上传目录
     */
    private String path;

    /**
     * 文件类型, 1=图片;2=视频;3=音频;4=文本；10=其他
     */
    private Integer type;
}