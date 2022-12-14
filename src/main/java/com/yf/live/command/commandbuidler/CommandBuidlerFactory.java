//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.commandbuidler;

public class CommandBuidlerFactory {
    public CommandBuidlerFactory() {
    }

    public static CommandBuidler createBuidler() {
        return new DefaultCommandBuidler();
    }

    public static CommandBuidler createBuidler(String rootpath) {
        return new DefaultCommandBuidler(rootpath);
    }
}
