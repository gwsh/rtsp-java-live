//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.util;

import com.sun.jna.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessUtil {
    private static final Logger log = LoggerFactory.getLogger(ProcessUtil.class);

    public ProcessUtil() {
    }

    public static String getProcessId(Process process) {
        long pid = -1L;
        Field field = null;
        if (Platform.isWindows()) {
            try {
                field = process.getClass().getDeclaredField("handle");
                field.setAccessible(true);
                pid = Kernel32.INSTANCE.GetProcessId((Long)field.get(process));
            } catch (Exception var6) {
                var6.printStackTrace();
            }
        } else if (Platform.isLinux() || Platform.isAIX()) {
            try {
                Class<?> clazz = Class.forName("java.lang.UNIXProcess");
                field = clazz.getDeclaredField("pid");
                field.setAccessible(true);
                pid = (long)(Integer)field.get(process);
            } catch (Throwable var5) {
                var5.printStackTrace();
            }
        }

        return String.valueOf(pid);
    }

    public static boolean killProcessByPid(String pid) {
        if (!StringUtils.isEmpty(pid) && !"-1".equals(pid)) {
            Process process = null;
            BufferedReader reader = null;
            String command = "";
            boolean result = false;
            if (Platform.isWindows()) {
                command = "cmd.exe /c taskkill /PID " + pid + " /F /T ";
            } else if (Platform.isLinux() || Platform.isAIX()) {
                command = "kill -9 " + pid;
            }

            try {
                process = Runtime.getRuntime().exec(command);
                reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));
                String line = null;

                while((line = reader.readLine()) != null) {
                    log.info("kill PID return info -----> " + line);
                }

                result = true;
            } catch (Exception var14) {
                log.info("杀进程出错：", var14);
                result = false;
            } finally {
                if (process != null) {
                    process.destroy();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException var13) {
                    }
                }

            }

            return result;
        } else {
            throw new RuntimeException("Pid ==" + pid);
        }
    }
}
