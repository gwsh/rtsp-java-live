//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.test;

import com.yf.live.command.CommandManager;
import com.yf.live.command.CommandManagerImpl;
import com.yf.live.command.commandbuidler.CommandBuidlerFactory;
import com.yf.live.command.data.CommandTasker;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public Test() {
    }

    public static void test1() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        Map<String, String> map = new HashMap();
        map.put("appName", "test123");
        map.put("input", "rtsp://admin:admin@192.168.2.236:37779/cam/realmonitor?channel=1&subtype=0");
        map.put("output", "rtmp://192.168.30.21/live/");
        map.put("codec", "h264");
        map.put("fmt", "flv");
        map.put("fps", "25");
        map.put("rs", "640x360");
        map.put("twoPart", "2");
        String id = manager.start(map);
        System.out.println(id);
        CommandTasker info = manager.query(id);
        System.out.println(info);
        Collection<CommandTasker> infoList = manager.queryAll();
        System.out.println(infoList);
        Thread.sleep(30000L);
        manager.stop(id);
    }

    public static void test2() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        manager.start("tomcat", "ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat");
        Thread.sleep(30000L);
        manager.stopAll();
    }

    public static void test4() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        manager.start("tomcat", "D:/TestWorkspaces/FFmpegCommandHandler/src/cc/eguid/FFmpegCommandManager/ffmpeg/ffmpeg -i rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov -vcodec copy -acodec copy -f flv -y rtmp://106.14.182.20:1935/rtmp/tomcat", true);
        Thread.sleep(30000L);
        manager.stopAll();
    }

    public static void test3() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        manager.start("tomcat", "ffmpeg -i rtsp://admin:yf_12345678@192.168.0.109:554/h264/ch1/main/av_stream -vcodec copy -acodec aac -ar 44100 -strict -2 -ac 1 -f flv -s 1280x720 -q 10 -f flv rtmp://localhost:1935/hls/test", false);
        System.out.println(1);
    }

    public static void testStreamCommandAssmbly() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        manager.start("test1", CommandBuidlerFactory.createBuidler().add("ffmpeg").add("-i", "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov").add("-rtsp_transport", "tcp").add("-vcodec", "copy").add("-acodec", "copy").add("-f", "flv").add("-y").add("rtmp://106.14.182.20:1935/rtmp/test1"));
        Thread.sleep(30000L);
        manager.stopAll();
    }

    public static void testBroken() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        manager.start("test1", CommandBuidlerFactory.createBuidler().add("ffmpeg").add("-i", "rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov").add("-rtsp_transport", "tcp").add("-vcodec", "copy").add("-acodec", "copy").add("-f", "flv").add("-y").add("rtmp://106.14.182.20:1935/rtmp/test1"));
        Thread.sleep(30000L);
        manager.stopAll();
        manager.destory();
    }

    public static void testBrokenMuti() throws InterruptedException {
        CommandManager manager = new CommandManagerImpl();
        manager.start("test1", CommandBuidlerFactory.createBuidler().add("ffmpeg").add("-i", "rtsp://admin:yf_12345678@192.168.0.109:554/h264/ch1/main/av_stream").add("-vcodec", "copy").add("-acodec", "aac").add("-ar", "44100").add("-strict", "2").add("-ac", "1").add("-f", "flv").add("-s", "1280x720").add("-q", "10").add("rtmp://localhost:1935/hls/test"));
        Thread.sleep(30000L);
        manager.stopAll();
        manager.destory();
    }

    public static void main(String[] args) throws InterruptedException {
        pushVideoAsRTSP("rtsp://admin:a12345678@60.29.128.234:3001/h264/ch37/sub/av_stream", "rtmp://222.242.188.236:1935/live/test");
    }

    public static Integer pushVideoAsRTSP(String rtspUrl, String nginxRtmpUrl) {
        int flag = -1;

        try {
            String command = "ffmpeg ";
            command = command + " -re -rtsp_transport tcp -i " + rtspUrl;
            command = command + " -vcodec libx264 -f flv -acodec aac -ar 44100 -strict -2 -ac 1  -s 1280x720 -q 10 " + nginxRtmpUrl;
            System.out.println("ffmpeg推流命令：" + command);
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line = "";

            while((line = br.readLine()) != null) {
                System.out.println("视频推流信息[" + line + "]");
            }

            flag = process.waitFor();
        } catch (Exception var7) {
            var7.printStackTrace();
        }

        return flag;
    }
}
