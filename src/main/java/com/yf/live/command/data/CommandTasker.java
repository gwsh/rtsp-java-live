//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.data;

import com.yf.live.command.handler.OutHandler;

public class CommandTasker {
    private final String id;
    private final String command;
    private Process process;
    private OutHandler thread;

    public CommandTasker(String id, String command) {
        this.id = id;
        this.command = command;
    }

    public CommandTasker(String id, String command, Process process, OutHandler thread) {
        this.id = id;
        this.command = command;
        this.process = process;
        this.thread = thread;
    }

    public String getId() {
        return this.id;
    }

    public Process getProcess() {
        return this.process;
    }

    public OutHandler getThread() {
        return this.thread;
    }

    public String getCommand() {
        return this.command;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public void setThread(OutHandler thread) {
        this.thread = thread;
    }

    public String toString() {
        return "CommandTasker [id=" + this.id + ", command=" + this.command + ", process=" + this.process + ", thread=" + this.thread + "]";
    }
}
