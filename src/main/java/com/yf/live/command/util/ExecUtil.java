//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.util;

import com.yf.live.command.data.CommandTasker;
import com.yf.live.command.handler.OutHandler;
import com.yf.live.command.handler.OutHandlerMethod;
import java.io.IOException;

public class ExecUtil {
    public ExecUtil() {
    }

    public static Process exec(String cmd) throws IOException {
        Runtime runtime = Runtime.getRuntime();
        Process process = runtime.exec(cmd);
        return process;
    }

    public static boolean stop(Process process) {
        if (process != null) {
            process.destroy();
            return true;
        } else {
            return false;
        }
    }

    public static boolean stop(Thread outHandler) {
        if (outHandler != null && outHandler.isAlive()) {
            outHandler.stop();
            outHandler.destroy();
            return true;
        } else {
            return false;
        }
    }

    public static void stop(CommandTasker tasker) {
        if (tasker != null) {
            stop((Thread)tasker.getThread());
            stop(tasker.getProcess());
        }

    }

    public static CommandTasker createTasker(String id, String command, OutHandlerMethod ohm) throws IOException {
        Process process = exec(command);
        OutHandler outHandler = OutHandler.create(process.getErrorStream(), id, ohm);
        CommandTasker tasker = new CommandTasker(id, command, process, outHandler);
        return tasker;
    }

    public static CommandTasker restart(CommandTasker tasker) throws IOException {
        if (tasker != null) {
            String id = tasker.getId();
            String command = tasker.getCommand();
            OutHandlerMethod ohm = null;
            if (tasker.getThread() != null) {
                ohm = tasker.getThread().getOhm();
            }

            stop(tasker);
            Process process = exec(command);
            tasker.setProcess(process);
            OutHandler outHandler = OutHandler.create(process.getErrorStream(), id, ohm);
            tasker.setThread(outHandler);
        }

        return tasker;
    }
}
