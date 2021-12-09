package com.liangzhicheng.modules.entity.vo;

import com.liangzhicheng.modules.entity.SysMenuEntity;
import lombok.Data;

import java.util.List;

@Data
public class SysMenuVO extends SysMenuEntity {

    private List<SysMenuVO> childrenList;

}
