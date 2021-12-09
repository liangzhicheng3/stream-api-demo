package com.liangzhicheng.modules.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liangzhicheng.modules.entity.SysMenuEntity;
import com.liangzhicheng.modules.entity.vo.SysMenuVO;

import java.util.List;

public interface ISysMenuService extends IService<SysMenuEntity> {

    void testStream();

    List<SysMenuVO> listTree();

}
