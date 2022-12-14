//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.handler;

import com.yf.live.command.CommandManagerImpl;
import com.yf.live.command.data.CommandTasker;
import com.yf.live.command.util.ExecUtil;
import java.io.IOException;

public class TaskHandlerImpl implements TaskHandler {
    private OutHandlerMethod ohm = null;

    public TaskHandlerImpl(OutHandlerMethod ohm) {
        this.ohm = ohm;
    }

    public void setOhm(OutHandlerMethod ohm) {
        this.ohm = ohm;
    }

    public CommandTasker process(String id, String command) {
        CommandTasker tasker = null;

        try {
            tasker = ExecUtil.createTasker(id, command, this.ohm);
            tasker.getProcess();
            if (CommandManagerImpl.config.isDebug()) {
                System.out.println(id + " 执行命令行：" + command);
            }

            return tasker;
        } catch (IOException var5) {
            ExecUtil.stop(tasker);
            if (CommandManagerImpl.config.isDebug()) {
                System.err.println(id + " 执行命令失败！进程和输出线程已停止");
            }

            return null;
        }
    }

    public boolean stop(Process process) {
        return ExecUtil.stop(process);
    }

    public boolean stop(Thread outHandler) {
        return ExecUtil.stop(outHandler);
    }

    public boolean stop(Process process, Thread thread) {
        boolean ret = false;
        this.stop(thread);
        ret = this.stop(process);
        return ret;
    }
}
