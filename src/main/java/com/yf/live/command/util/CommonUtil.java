//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.util;

import java.io.File;
import java.util.UUID;

public class CommonUtil {
    public static final String rootPath = getProjectRootPath();
    public static final String TRUE = "true";
    public static final String NULL_STRING = "";
    public static final String H_LINE = "-";

    public CommonUtil() {
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "");
    }

    public static boolean isNull(String str) {
        return str == null || "".equals(str.trim());
    }

    public static boolean isTrue(String str) {
        return "true".equals(str);
    }

    public static String getRootPath() {
        return rootPath;
    }

    public static String getProjectRootPath() {
        String path = null;

        try {
            path = CommonUtil.class.getResource("/").getPath();
        } catch (Exception var3) {
            File directory = new File("");
            path = directory.getAbsolutePath() + File.separator;
        }

        return path;
    }

    public static String getClassPath(Class<?> cla) {
        return cla.getResource("").getPath();
    }
}
