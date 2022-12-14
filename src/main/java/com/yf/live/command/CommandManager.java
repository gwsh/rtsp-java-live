//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command;

import com.yf.live.command.commandbuidler.CommandAssembly;
import com.yf.live.command.commandbuidler.CommandBuidler;
import com.yf.live.command.data.CommandTasker;
import com.yf.live.command.data.TaskDao;
import com.yf.live.command.handler.TaskHandler;
import java.util.Collection;
import java.util.Map;

public interface CommandManager {
    void setTaskDao(TaskDao taskDao);

    void setTaskHandler(TaskHandler taskHandler);

    void setCommandAssembly(CommandAssembly commandAssembly);

    String start(String id, String command);

    String start(String id, String commond, boolean hasPath);

    String start(String id, CommandBuidler commandBuidler);

    String start(Map<String, String> assembly);

    boolean stop(String id);

    boolean isHave(String id);

    int stopAll();

    CommandTasker query(String id);

    Collection<CommandTasker> queryAll();

    void destory();
}
