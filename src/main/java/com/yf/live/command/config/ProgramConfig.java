//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProgramConfig {
    @Value("${ffmpeg.path}")
    private String path;
    @Value("${ffmpeg.debug}")
    private boolean debug;
    @Value("${ffmpeg.size}")
    private Integer size;
    @Value("${ffmpeg.callback}")
    private String callback;
    @Value("${ffmpeg.keepalive}")
    private boolean keepalive;

    public ProgramConfig() {
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isDebug() {
        return this.debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public Integer getSize() {
        return this.size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getCallback() {
        return this.callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }

    public boolean isKeepalive() {
        return this.keepalive;
    }

    public void setKeepalive(boolean keepalive) {
        this.keepalive = keepalive;
    }

    public String toString() {
        return "ProgramConfig [path=" + this.path + ", debug=" + this.debug + ", size=" + this.size + ", callback=" + this.callback + ", keepalive=" + this.keepalive + "]";
    }
}
