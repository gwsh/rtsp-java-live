//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.handler;

import com.yf.live.command.CommandManagerImpl;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class OutHandler extends Thread {
    private volatile boolean desstatus = true;
    private BufferedReader br = null;
    private String id = null;
    private OutHandlerMethod ohm;

    public static OutHandler create(InputStream is, String id, OutHandlerMethod ohm) {
        return create(is, id, ohm, true);
    }

    public static OutHandler create(InputStream is, String id, OutHandlerMethod ohm, boolean start) {
        OutHandler out = new OutHandler(is, id, ohm);
        if (start) {
            out.start();
        }

        return out;
    }

    public void setOhm(OutHandlerMethod ohm) {
        this.ohm = ohm;
    }

    public void setDesStatus(boolean desStatus) {
        this.desstatus = desStatus;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OutHandlerMethod getOhm() {
        return this.ohm;
    }

    public OutHandler(InputStream is, String id, OutHandlerMethod ohm) {
        this.br = new BufferedReader(new InputStreamReader(is));
        this.id = id;
        this.ohm = ohm;
    }

    public void destroy() {
        this.setDesStatus(false);
    }

    public void run() {
        String msg = null;

        try {
            if (CommandManagerImpl.config.isDebug()) {
                System.out.println(this.id + "开始推流！");
            }

            while(this.desstatus && (msg = this.br.readLine()) != null) {
                this.ohm.parse(this.id, msg);
                if (this.ohm.isbroken()) {
                    System.err.println("检测到中断，提交重启任务给保活处理器");
                    KeepAliveHandler.add(this.id);
                }
            }
        } catch (IOException var6) {
            System.out.println("发生内部异常错误，自动关闭[" + this.getId() + "]线程");
            this.destroy();
        } finally {
            if (this.isAlive()) {
                this.destroy();
            }

        }

    }
}
