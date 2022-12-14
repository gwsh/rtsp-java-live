//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.data;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TaskDaoImpl implements TaskDao {
    private ConcurrentMap<String, CommandTasker> map = null;

    public TaskDaoImpl(int size) {
        this.map = new ConcurrentHashMap(size);
    }

    public CommandTasker get(String id) {
        return (CommandTasker)this.map.get(id);
    }

    public Collection<CommandTasker> getAll() {
        return this.map.values();
    }

    public int add(CommandTasker CommandTasker) {
        String id = CommandTasker.getId();
        if (id != null && !this.map.containsKey(id)) {
            this.map.put(CommandTasker.getId(), CommandTasker);
            if (this.map.get(id) != null) {
                return 1;
            }
        }

        return 0;
    }

    public int remove(String id) {
        return this.map.remove(id) != null ? 1 : 0;
    }

    public int removeAll() {
        int size = this.map.size();

        try {
            this.map.clear();
            return size;
        } catch (Exception var3) {
            return 0;
        }
    }

    public boolean isHave(String id) {
        return this.map.containsKey(id);
    }
}
