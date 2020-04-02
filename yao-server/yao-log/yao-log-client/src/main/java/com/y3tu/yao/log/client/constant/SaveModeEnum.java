package com.y3tu.yao.log.client.constant;

/**
 * 日志保存方式
 *
 * @author y3tu
 */
public enum SaveModeEnum {
    NONE(""),
    DB("db"),
    ES("es");

    private String value;

    SaveModeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
