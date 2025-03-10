package com.sc2.web.controller.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sc2.common.config.sc2Config;
import com.sc2.common.utils.StringUtils;

/**
 * 首页
 *
 * @author sc2
 */
@RestController
public class SysIndexController
{
    /** 系统基础配置 */
    @Autowired
    private sc2Config sc2Config;

    /**
     * 访问首页，提示语
     */
    @RequestMapping("/")
    public String index()
    {
        return StringUtils.format("您已成功访问 {} 的后端地址。当前版本：v{}。", sc2Config.getName(), sc2Config.getVersion());
    }
}
