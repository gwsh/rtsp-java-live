//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.handler;

public class DefaultOutHandlerMethod implements OutHandlerMethod {
    public boolean isBroken = false;

    public DefaultOutHandlerMethod() {
    }

    public void parse(String id, String msg) {
        if (msg.indexOf("fail") != -1) {
            System.err.println(id + "任务可能发生故障：" + msg);
            System.err.println("失败，设置中断状态");
            this.isBroken = true;
        } else {
            this.isBroken = false;
            System.err.println(id + "消息：" + msg);
        }

    }

    public boolean isbroken() {
        return this.isBroken;
    }
}
