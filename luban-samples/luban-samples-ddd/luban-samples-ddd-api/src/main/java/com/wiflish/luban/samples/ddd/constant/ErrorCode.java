package com.wiflish.luban.samples.ddd.constant;

/**
 * 异常枚举.
 *
 * @author wiflish
 * @since 2023-08-28
 */
public enum ErrorCode {
    EE_TASK_taskNameConflict("1001", "重复的任务"),
    EE_TASK_notExists("1002", "不存在的任务"),
    EE_TASK_notExistsStatus("1003", "不存在的任务状态"),
    ;
    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}