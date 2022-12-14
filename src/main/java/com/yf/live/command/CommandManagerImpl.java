//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command;

import com.yf.live.command.commandbuidler.CommandAssembly;
import com.yf.live.command.commandbuidler.CommandAssemblyImpl;
import com.yf.live.command.commandbuidler.CommandBuidler;
import com.yf.live.command.config.ProgramConfig;
import com.yf.live.command.data.CommandTasker;
import com.yf.live.command.data.TaskDao;
import com.yf.live.command.data.TaskDaoImpl;
import com.yf.live.command.handler.DefaultOutHandlerMethod;
import com.yf.live.command.handler.KeepAliveHandler;
import com.yf.live.command.handler.OutHandlerMethod;
import com.yf.live.command.handler.TaskHandler;
import com.yf.live.command.handler.TaskHandlerImpl;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CommandManagerImpl implements CommandManager {
    @Autowired
    private Environment env;
    public static ProgramConfig config;
    private TaskDao taskDao;
    private TaskHandler taskHandler;
    private CommandAssembly commandAssembly;
    private OutHandlerMethod ohm;
    private KeepAliveHandler keepAliveHandler;

    @PostConstruct
    private void initConfig() {
        config = new ProgramConfig();

        try {
            config.setDebug(Boolean.valueOf(this.env.getProperty("ffmpeg.debug")));
            config.setCallback(this.env.getProperty("ffmpeg.callback"));
            config.setSize(Integer.valueOf(this.env.getProperty("ffmpeg.size")));
            config.setPath(this.env.getProperty("ffmpeg.path"));
            config.setKeepalive(Boolean.valueOf(this.env.getProperty("ffmpeg.keepalive")));
        } catch (Exception var2) {
            System.err.println("获取配置文件异常");
        }

    }

    public CommandManagerImpl() {
        this((Integer)null);
    }

    public CommandManagerImpl(Integer size) {
        this.taskDao = null;
        this.taskHandler = null;
        this.commandAssembly = null;
        this.ohm = null;
        this.keepAliveHandler = null;
        this.init(size);
    }

    public CommandManagerImpl(TaskDao taskDao, TaskHandler taskHandler, CommandAssembly commandAssembly, OutHandlerMethod ohm, Integer size) {
        this.taskDao = null;
        this.taskHandler = null;
        this.commandAssembly = null;
        this.ohm = null;
        this.keepAliveHandler = null;
        this.taskDao = taskDao;
        this.taskHandler = taskHandler;
        this.commandAssembly = commandAssembly;
        this.ohm = ohm;
        this.init(size);
    }

    public void init(Integer size) {
        config = new ProgramConfig();
        boolean iskeepalive = false;
        if (size == null) {
            size = config.getSize() == null ? 10 : config.getSize();
        }

        if (this.ohm == null) {
            this.ohm = new DefaultOutHandlerMethod();
        }

        if (this.taskDao == null) {
            this.taskDao = new TaskDaoImpl(size);
            if (iskeepalive) {
                this.keepAliveHandler = new KeepAliveHandler(this.taskDao);
                this.keepAliveHandler.start();
            }
        }

        if (this.taskHandler == null) {
            this.taskHandler = new TaskHandlerImpl(this.ohm);
        }

        if (this.commandAssembly == null) {
            this.commandAssembly = new CommandAssemblyImpl();
        }

    }

    public void setTaskDao(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public void setTaskHandler(TaskHandler taskHandler) {
        this.taskHandler = taskHandler;
    }

    public void setCommandAssembly(CommandAssembly commandAssembly) {
        this.commandAssembly = commandAssembly;
    }

    public void setOhm(OutHandlerMethod ohm) {
        this.ohm = ohm;
    }

    public boolean isInit(boolean b) {
        boolean ret = this.ohm == null || this.taskDao == null || this.taskHandler == null || this.commandAssembly == null;
        if (ret && b) {
            this.init((Integer)null);
        }

        return ret;
    }

    public String start(String id, String command) {
        return this.start(id, command, false);
    }

    public String start(String id, String command, boolean hasPath) {
        if (this.isInit(true)) {
            System.err.println("执行失败，未进行初始化或初始化失败！");
            return null;
        } else {
            if (id != null && command != null) {
                CommandTasker tasker = this.taskHandler.process(id, hasPath ? command : config.getPath() + command);
                if (tasker != null) {
                    int ret = this.taskDao.add(tasker);
                    if (ret > 0) {
                        return tasker.getId();
                    }

                    this.taskHandler.stop(tasker.getProcess(), tasker.getThread());
                    if (config.isDebug()) {
                        System.err.println("持久化失败，停止任务！");
                    }
                }
            }

            return null;
        }
    }

    public String start(Map<String, String> assembly) {
        if (this.checkConfig()) {
            System.err.println("配置未正确加载，无法执行");
            return null;
        } else if (assembly != null && !assembly.isEmpty() && assembly.containsKey("appName")) {
            String appName = (String)assembly.get("appName");
            if (appName != null && "".equals(appName.trim())) {
                System.err.println("appName不能为空");
                return null;
            } else {
                assembly.put("ffmpegPath", config.getPath() + "ffmpeg");
                String command = this.commandAssembly.assembly(assembly);
                return command != null ? this.start(appName, command, true) : null;
            }
        } else {
            System.err.println("参数不正确，无法执行");
            return null;
        }
    }

    public String start(String id, CommandBuidler commandBuidler) {
        if (this.checkConfig()) {
            System.err.println("配置未正确加载，无法执行");
            return null;
        } else {
            String command = commandBuidler.get();
            return command != null ? this.start(id, command, true) : null;
        }
    }

    private boolean checkConfig() {
        return config == null;
    }

    public boolean stop(String id) {
        if (id != null && this.taskDao.isHave(id)) {
            if (config.isDebug()) {
                System.out.println("正在停止任务：" + id);
            }

            CommandTasker tasker = this.taskDao.get(id);
            if (this.taskHandler.stop(tasker.getProcess(), tasker.getThread())) {
                this.taskDao.remove(id);
                return true;
            }
        }

        System.err.println("停止任务失败！id=" + id);
        return false;
    }

    public boolean isHave(String id) {
        return this.taskDao.isHave(id);
    }

    public int stopAll() {
        Collection<CommandTasker> list = this.taskDao.getAll();
        Iterator<CommandTasker> iter = list.iterator();
        CommandTasker tasker = null;
        int index = 0;

        while(iter.hasNext()) {
            tasker = (CommandTasker)iter.next();
            if (this.taskHandler.stop(tasker.getProcess(), tasker.getThread())) {
                this.taskDao.remove(tasker.getId());
                ++index;
            }
        }

        if (config.isDebug()) {
            System.out.println("停止了" + index + "个任务！");
        }

        return index;
    }

    public CommandTasker query(String id) {
        return this.taskDao.get(id);
    }

    public Collection<CommandTasker> queryAll() {
        return this.taskDao.getAll();
    }

    public void destory() {
        if (this.keepAliveHandler != null) {
            this.keepAliveHandler.interrupt();
        }

    }
}
