//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.yf.live.controller;

import com.yf.live.command.CommandManager;
import com.yf.live.util.AjaxResult;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping({"/live"})
@Controller
public class LiveController {

    @Value("${yf.server.url}")
    private String url;

    @Value("${yf.server.addr}")
    private String addr;

    @Value("${yf.server.port}")
    private String port;

    private static final Logger log = LoggerFactory.getLogger(LiveController.class);
    @Autowired
    private CommandManager manager;

    public LiveController() {
    }

    @RequestMapping({"/url"})
    @ResponseBody
    public AjaxResult getLiveUrl(String inputUrl, String id) {
        log.info(url);
        log.info("获取视频流地址...");
        log.info("推流地址：" + inputUrl);
        log.info("业务id：" + id);
        if (null != inputUrl && !"".equals(inputUrl.trim())) {
            if (null != id && !"".equals(id.trim())) {
                Map map = new HashMap();
                if (!this.manager.isHave(id + "")) {
                    log.info("推流开始");
                    this.manager.start(id, "ffmpeg -re -rtsp_transport tcp -i " + inputUrl + " -vcodec libx264 -f flv -acodec aac -ar 44100 -strict -2 -ac 1  -s 1280x720 -q 10 rtmp://" + addr + ":" + port + "/live/" + id, true);
                    //this.manager.start(id, "ffmpeg -re -rtsp_transport tcp -i " + inputUrl + " -f flv -vcodec libx264 -vprofile baseline -acodec aac -strict experimental -ar 44100 -ac 2 -b:a 96k -r 25 -b:v 500k -s 640*480  -f flv  -q 10  rtmp://" + addr + ":" + port + "/live/" + id, true);
                } else {
                    log.info("推流已经存在");
                }
                map.put("url", url + id);
                return AjaxResult.success(map);
            } else {
                return AjaxResult.error("业务id不能为空");
            }
        } else {
            return AjaxResult.error("推流地址不能为空");
        }
    }

    @RequestMapping({"/stop/{id}"})
    @ResponseBody
    public AjaxResult stop(@PathVariable("id") String id) {
        log.info("关闭视频流");
        return this.manager.stop(id) ? AjaxResult.success("关闭成功") : AjaxResult.error("关闭失败");
    }
}
