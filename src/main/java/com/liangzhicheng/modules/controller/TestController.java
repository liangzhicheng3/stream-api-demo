package com.liangzhicheng.modules.controller;

import com.liangzhicheng.modules.entity.vo.SysMenuVO;
import com.liangzhicheng.modules.service.ISysMenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private ISysMenuService menuService;

    //Stream API测试
    @GetMapping(value = "/stream")
    public String testStream() {
        menuService.testStream();
        return "操作成功";
    }

    //以层级结构返回所有菜单
    @GetMapping(value = "/listTree")
    public Object listTree() {
        return menuService.listTree();
    }

}
