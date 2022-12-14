//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

public class PropertiesUtil {
    public PropertiesUtil() {
    }

    public static <T> T load(String path, Class<T> cl) {
        InputStream is = null;

        try {
            is = getInputStream(path);
        } catch (FileNotFoundException var11) {
            String newpath = CommonUtil.getProjectRootPath() + path;
            System.err.println("尝试从web目录读取配置文件：" + newpath);

            try {
                is = getInputStream(newpath);
                System.err.println("web目录读取到配置文件：" + newpath);
            } catch (FileNotFoundException var10) {
                System.err.println("没找到配置文件，读取默认配置文件");
                ClassLoader classloader = Thread.currentThread().getContextClassLoader();

                try {
                    is = classloader.getResourceAsStream("/loadFFmpeg.properties");
                    System.err.println("读取默认配置文件：defaultFFmpegConfig.properties");
                } catch (Exception var9) {
                    System.err.println("没找到默认配置文件:defaultFFmpegConfig.properties");
                    return null;
                }
            }
        }

        if (is != null) {
            Properties pro = new Properties();

            try {
                System.err.println("加载配置文件...");
                pro.load(is);
                System.err.println("加载配置文件完毕");
                return (T)load(pro, cl);
            } catch (IOException var8) {
                System.err.println("加载配置文件失败");
                return null;
            }
        } else {
            return null;
        }
    }

    public static Object load(Properties pro, Class<?> cl) {
        try {
            Map<String, Object> map = getMap(pro);
            System.err.println("读取的配置项：" + map);
            Object obj = ReflectUtil.mapToObj(map, cl);
            System.err.println("转换后的对象：" + obj);
            return obj;
        } catch (InstantiationException var4) {
            System.err.println("加载配置文件失败");
            return null;
        } catch (IllegalAccessException var5) {
            System.err.println("加载配置文件失败");
            return null;
        } catch (IllegalArgumentException var6) {
            System.err.println("加载配置文件失败");
            return null;
        } catch (InvocationTargetException var7) {
            System.err.println("加载配置文件失败");
            return null;
        }
    }

    public static InputStream getInputStream(String path) throws FileNotFoundException {
        return new FileInputStream(path);
    }

    public static Map<String, Object> getMap(String path) {
        Properties pro = new Properties();

        try {
            pro.load(getInputStream(path));
            return getMap(pro);
        } catch (IOException var3) {
            return null;
        }
    }

    public static Map<String, Object> getMap(String path, boolean isRootPath) {
        return getMap(isRootPath ? CommonUtil.getProjectRootPath() + path : path);
    }

    public static Map<String, Object> getMap(Properties pro) {
        if (pro != null && !pro.isEmpty() && pro.size() >= 1) {
            Map<String, Object> map = new HashMap();
            Iterator var2 = pro.entrySet().iterator();

            while(var2.hasNext()) {
                Entry<Object, Object> en = (Entry)var2.next();
                String key = (String)en.getKey();
                Object value = en.getValue();
                map.put(key, value);
            }

            return map;
        } else {
            return null;
        }
    }
}
