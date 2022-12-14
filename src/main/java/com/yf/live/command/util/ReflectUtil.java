//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ReflectUtil {
    public static final String SET = "set";
    public static final String GET = "get";

    public ReflectUtil() {
    }

    public static Object mapToObj(Map<String, Object> map, Class<?> oc) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Method[] ms = oc.getDeclaredMethods();
        if (ms != null && ms.length >= 1) {
            Object obj = getObject(oc);
            Method[] var4 = ms;
            int var5 = ms.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Method m = var4[var6];
                String methodName = m.getName();
                String fieldName = getMethodField(methodName, "set");
                Object value = map.get(fieldName);
                if (value != null) {
                    setMethodValue(m, obj, typeConvert(value, m));
                }
            }

            return obj;
        } else {
            return null;
        }
    }

    public static Object typeConvert(Object obj, Method m) {
        return typeConvert(obj, m.getParameterTypes()[0].getName());
    }

    public static Object typeConvert(Object obj, Field f) {
        return typeConvert(obj, f.getType().getName());
    }

    public static Object typeConvert(Object obj, String typeName) {
        String str = String.valueOf(obj);
        if (!"int".equals(typeName) && !"java.lang.Integer".equals(typeName)) {
            if (!"long".equals(typeName) && !"java.lang.Long".equals(typeName)) {
                if (!"byte".equals(typeName) && !"java.lang.Byte".equals(typeName)) {
                    if (!"short".equals(typeName) && !"java.lang.Short".equals(typeName)) {
                        if (!"float".equals(typeName) && !"java.lang.Float".equals(typeName)) {
                            if (!"double".equals(typeName) && !"java.lang.Double".equals(typeName)) {
                                if (!"boolean".equals(typeName) && !"java.lang.Boolean".equals(typeName)) {
                                    if (!"char".equals(typeName) && !"java.lang.Character".equals(typeName)) {
                                        return "java.lang.String".equals(typeName) ? str : null;
                                    } else {
                                        return str.trim().charAt(0);
                                    }
                                } else {
                                    return "true".equals(str);
                                }
                            } else {
                                return Double.valueOf(str.trim());
                            }
                        } else {
                            return Float.valueOf(str.trim());
                        }
                    } else {
                        return Short.valueOf(str.trim());
                    }
                } else {
                    return Byte.valueOf(str.trim());
                }
            } else {
                return Long.valueOf(str.trim());
            }
        } else {
            return Integer.valueOf(str.trim());
        }
    }

    public static Class<?> getFieldType(Class<?> cl, String fieldName) throws NoSuchFieldException, SecurityException {
        Field f = cl.getDeclaredField(fieldName);
        return f.getType();
    }

    public static Field findField(Class<?> cl, String fieldName) throws NoSuchFieldException, SecurityException {
        return cl.getDeclaredField(fieldName);
    }

    public static Object setMethodValue(Method m, Object obj, Object... value) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        m.getParameterTypes();
        return m.invoke(obj, value);
    }

    public static Object getFieldValue(Class<?> obj, String FieldName) throws NoSuchFieldException, SecurityException {
        return obj.getDeclaredField(FieldName);
    }

    public static Object getObject(Class<?> oc) throws InstantiationException, IllegalAccessException {
        return oc.newInstance();
    }

    public static String getMethodField(String methodName, String prefix) {
        String m = null;
        if (prefix != null && methodName.indexOf(prefix) >= 0) {
            m = methodName.substring(prefix.length());
            return stringFirstLower(m);
        } else {
            return m;
        }
    }

    public static String stringFirstUpper(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char)(ch[0] - 32);
        }

        return new String(ch);
    }

    public static String stringFirstLower(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'A' && ch[0] <= 'Z') {
            ch[0] = (char)(ch[0] + 32);
        }

        return new String(ch);
    }
}
