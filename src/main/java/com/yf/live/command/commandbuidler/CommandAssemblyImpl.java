//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.command.commandbuidler;

import java.util.Map;

public class CommandAssemblyImpl implements CommandAssembly {
    public CommandAssemblyImpl() {
    }

    public String assembly(Map<String, String> paramMap) {
        try {
            if (paramMap.containsKey("ffmpegPath")) {
                String ffmpegPath = (String)paramMap.get("ffmpegPath");
                StringBuilder comm = new StringBuilder(ffmpegPath + " -i ");
                if (paramMap.containsKey("input") && paramMap.containsKey("output") && paramMap.containsKey("appName") && paramMap.containsKey("twoPart")) {
                    String input = (String)paramMap.get("input");
                    String output = (String)paramMap.get("output");
                    String appName = (String)paramMap.get("appName");
                    String twoPart = (String)paramMap.get("twoPart");
                    String codec = (String)paramMap.get("codec");
                    codec = codec == null ? "h264" : (String)paramMap.get("codec");
                    comm.append(input);
                    if ("0".equals(twoPart)) {
                        comm.append(" -vcodec " + codec + " -f flv -an " + output + appName);
                    } else {
                        String rs;
                        if (paramMap.containsKey("fmt")) {
                            rs = (String)paramMap.get("fmt");
                            comm.append(" -f " + rs);
                        }

                        if (paramMap.containsKey("fps")) {
                            rs = (String)paramMap.get("fps");
                            comm.append(" -r " + rs);
                            comm.append(" -g " + rs);
                        }

                        if (paramMap.containsKey("rs")) {
                            rs = (String)paramMap.get("rs");
                            comm.append(" -s " + rs);
                        }

                        comm.append(" -an " + output + appName);
                        if ("2".equals(twoPart)) {
                            comm.append(" -vcodec copy  -f flv -an ").append(output + appName + "HD");
                        }
                    }

                    return comm.toString();
                }
            }

            return null;
        } catch (Exception var10) {
            return null;
        }
    }

    public String assembly() {
        return null;
    }
}
