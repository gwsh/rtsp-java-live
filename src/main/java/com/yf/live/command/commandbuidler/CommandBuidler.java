//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.commandbuidler;

public interface CommandBuidler {
    CommandBuidler create(String root);

    CommandBuidler create();

    CommandBuidler add(String key, String val);

    CommandBuidler add(String val);

    CommandBuidler build();

    String get();
}
