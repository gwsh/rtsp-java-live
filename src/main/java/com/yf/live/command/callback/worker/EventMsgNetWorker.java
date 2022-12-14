//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.callback.worker;

import com.yf.live.command.CommandManagerImpl;
import com.yf.live.command.callback.EventCallBack;
import com.yf.live.command.callback.EventCallBackType;
import com.yf.live.command.data.CommandTasker;
import com.yf.live.command.data.TaskerEventMsg;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class EventMsgNetWorker extends Thread implements EventCallBack {
    protected static Queue<TaskerEventMsg> queue = null;
    private int timeout = 300;

    public EventMsgNetWorker(int timeout) {
        this.timeout = timeout;
        queue = new ConcurrentLinkedQueue();
    }

    public void run() {
        while(true) {
            try {
                while(true) {
                    if (queue.peek() != null) {
                        TaskerEventMsg t = (TaskerEventMsg)queue.poll();
                        String url = CommandManagerImpl.config.getCallback();
                        if (this.reqGET(url)) {
                            System.err.println("发送成功");
                        } else {
                            System.err.println("发送失败");
                            queue.offer(t);
                        }
                    }
                }
            } catch (Exception var3) {
            }
        }
    }

    private boolean reqGET(String url) {
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setUseCaches(false);
            connection.setConnectTimeout(this.timeout);
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setDoOutput(false);
            connection.setDoInput(false);
            connection.connect();
            return true;
        } catch (IOException var4) {
            return false;
        }
    }

    public boolean callback(EventCallBackType ecbt, CommandTasker tasker) {
        return queue.add(new TaskerEventMsg(ecbt, tasker));
    }
}
