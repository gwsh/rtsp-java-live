
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.util;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    public static final String CODE_TAG = "code";
    public static final String MSG_TAG = "msg";
    public static final String DATA_TAG = "data";

    public AjaxResult() {
    }

    public AjaxResult(AjaxResult.Type type, String msg) {
        super.put("code", type.value);
        super.put("msg", msg);
    }

    public AjaxResult(AjaxResult.Type type, String msg, Object data) {
        super.put("code", type.value);
        super.put("msg", msg);
        if (StringUtils.isNotNull(data)) {
            super.put("data", data);
        }

    }

    public static AjaxResult success() {
        return success("操作成功");
    }

    public static AjaxResult success(Object data) {
        return success("操作成功", data);
    }

    public static AjaxResult success(String msg) {
        return success(msg, (Object)null);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(AjaxResult.Type.SUCCESS, msg, data);
    }

    public static AjaxResult warn(String msg) {
        return warn(msg, (Object)null);
    }

    public static AjaxResult warn(String msg, Object data) {
        return new AjaxResult(AjaxResult.Type.WARN, msg, data);
    }

    public static AjaxResult error() {
        return error("操作失败");
    }

    public static AjaxResult error(String msg) {
        return error(msg, (Object)null);
    }

    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(AjaxResult.Type.ERROR, msg, data);
    }

    public static enum Type {
        SUCCESS(0),
        WARN(301),
        ERROR(500);

        private final int value;

        private Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
