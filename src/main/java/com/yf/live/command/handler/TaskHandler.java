//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.handler;

import com.yf.live.command.data.CommandTasker;

public interface TaskHandler {
    CommandTasker process(String id, String command);

    boolean stop(Process process);

    boolean stop(Thread thread);

    boolean stop(Process process, Thread thread);
}
