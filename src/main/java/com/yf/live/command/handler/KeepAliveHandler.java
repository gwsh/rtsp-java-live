//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.handler;

import com.yf.live.command.data.CommandTasker;
import com.yf.live.command.data.TaskDao;
import com.yf.live.command.util.ExecUtil;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeepAliveHandler extends Thread {
    private static Queue<String> queue = null;
    public int err_index = 0;
    public volatile int stop_index = 0;
    private TaskDao taskDao = null;

    public KeepAliveHandler(TaskDao taskDao) {
        this.taskDao = taskDao;
        queue = new ConcurrentLinkedQueue();
    }

    public static void add(String id) {
        if (queue != null) {
            queue.offer(id);
        }

    }

    public boolean stop(Process process) {
        if (process != null) {
            process.destroy();
            return true;
        } else {
            return false;
        }
    }

    public void run() {
        while(this.stop_index == 0) {
            if (queue != null) {
                String id = null;
                CommandTasker task = null;

                try {
                    while(queue.peek() != null) {
                        System.err.println("准备重启任务：" + queue);
                        id = (String)queue.poll();
                        task = this.taskDao.get(id);
                        ExecUtil.restart(task);
                    }
                } catch (IOException var4) {
                    System.err.println(id + " 任务重启失败，详情：" + task);
                    ++this.err_index;
                } catch (Exception var5) {
                }
            }
        }

    }

    public void interrupt() {
        this.stop_index = 1;
    }
}
