//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.data;

import java.util.Collection;

public interface TaskDao {
    CommandTasker get(String id);

    Collection<CommandTasker> getAll();

    int add(CommandTasker CommandTasker);

    int remove(String id);

    int removeAll();

    boolean isHave(String id);
}
