//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.data;

import com.yf.live.command.callback.EventCallBackType;

public class TaskerEventMsg {
    EventCallBackType ecbt;
    CommandTasker tasker;

    public TaskerEventMsg(EventCallBackType ecbt, CommandTasker tasker) {
        this.ecbt = ecbt;
        this.tasker = tasker;
    }

    public EventCallBackType getEcbt() {
        return this.ecbt;
    }

    public void setEcbt(EventCallBackType ecbt) {
        this.ecbt = ecbt;
    }

    public CommandTasker getTasker() {
        return this.tasker;
    }

    public void setTasker(CommandTasker tasker) {
        this.tasker = tasker;
    }

    public String toString() {
        return "CommandEventMsg [ecbt=" + this.ecbt + ", tasker=" + this.tasker + "]";
    }
}
