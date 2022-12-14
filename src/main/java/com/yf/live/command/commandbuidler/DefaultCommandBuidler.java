//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.commandbuidler;

import com.yf.live.command.CommandManagerImpl;

public class DefaultCommandBuidler implements CommandBuidler {
    StringBuilder buidler = null;
    String command = null;

    public DefaultCommandBuidler() {
        this.create();
    }

    public DefaultCommandBuidler(String rootpath) {
        this.create(rootpath);
    }

    public CommandBuidler create(String rootpath) {
        this.buidler = new StringBuilder(rootpath);
        return this;
    }

    public CommandBuidler create() {
        return this.create(CommandManagerImpl.config.getPath());
    }

    public CommandBuidler add(String key, String val) {
        return this.add(key).add(val);
    }

    public CommandBuidler add(String val) {
        if (this.buidler != null) {
            this.buidler.append(val);
            this.addBlankspace();
        }

        return this;
    }

    public CommandBuidler build() {
        if (this.buidler != null) {
            this.command = this.buidler.toString();
        }

        return this;
    }

    private void addBlankspace() {
        this.buidler.append(" ");
    }

    public String get() {
        if (this.command == null) {
            this.build();
        }

        return this.command;
    }
}
