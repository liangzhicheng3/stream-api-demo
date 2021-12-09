package com.liangzhicheng.modules.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liangzhicheng.modules.entity.SysMenuEntity;
import com.liangzhicheng.modules.entity.vo.SysMenuVO;
import com.liangzhicheng.modules.mapper.ISysMenuMapper;
import com.liangzhicheng.modules.service.ISysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<ISysMenuMapper, SysMenuEntity> implements ISysMenuService {

    @Override
    public void testStream() {
        List<SysMenuEntity> menuList = this.menuList();

        //为集合创建串行流对象
        Stream<SysMenuEntity> stream = menuList.stream();
        log.info("串行流对象：{}", stream);

        //为集合创建并行流对象
        Stream<SysMenuEntity> parallelStream = menuList.parallelStream();
        log.info("并行流对象：{}", parallelStream);

        //filter，获取菜单类型为目录的菜单
        List<SysMenuEntity> filter = menuList.stream()
                .filter(menu -> menu.getType().equals("1"))
                .collect(Collectors.toList());
        log.info("filter操作：{}", filter);

        //map，获取所有菜单的id
        List<String> idList = menuList.stream()
                .map(menu -> menu.getId())
                .collect(Collectors.toList());
        log.info("map操作：{}", idList);

        //limit，获取前5个菜单
        List<SysMenuEntity> fiveList = menuList.stream()
                .limit(5)
                .collect(Collectors.toList());
        log.info("limit操作：{}", fiveList);

        //count，获取所有菜单目录菜单的个数
        long menuCount = menuList.stream()
                .filter(menu -> menu.getType().equals("1"))
                .count();
        log.info("count操作：{}", menuCount);

        //sorted，将所有菜单按先目录后菜单再按钮的顺序排序
        List<SysMenuEntity> sortedList = menuList.stream()
                .sorted((menu1, menu2)->{return menu1.getType().compareTo(menu2.getType());})
                .collect(Collectors.toList());
        log.info("sorted操作：{}", sortedList);

        //skip，跳过前5个元素，返回后面的
        List<SysMenuEntity> skipList = menuList.stream()
                .skip(5)
                .collect(Collectors.toList());
        log.info("skip操作：{}", skipList);

        //collect转map，将菜单列表以id为key，以菜单对象为值转换成map
        Map<String, SysMenuEntity> menuMap = menuList.stream()
                .collect(Collectors.toMap(menu -> menu.getId(), menu -> menu));
        log.info("collect转map操作：{}", menuMap);

        //forEach，对集合中的元素进行迭代
        menuList.stream().forEach(menu-> log.info("forEach操作{}", menu));
    }

    @Override
    public List<SysMenuVO> listTree() {
        List<SysMenuEntity> menuList = this.menuList();
        List<SysMenuVO> resultList = menuList.stream()
                .filter(menu -> menu.getParentId().equals("0"))
                .map(menu -> this.convert(menu, menuList))
                .collect(Collectors.toList());
        return resultList;
    }

    private List<SysMenuEntity> menuList(){
        return baseMapper.selectList(Wrappers.<SysMenuEntity>lambdaQuery());
    }

    /**
     * 将菜单转换为有子级的菜单对象，当找不到子级菜单时候map操作不会再递归调用covert
     * @param menu 菜单对象
     * @param menuList 菜单集合
     * @return 返回目录菜单按钮结果集
     */
    private SysMenuVO convert(SysMenuEntity menu, List<SysMenuEntity> menuList) {
        SysMenuVO menuVO = new SysMenuVO();
        BeanUtils.copyProperties(menu, menuVO);
        List<SysMenuVO> childrenList = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> this.convert(subMenu, menuList))
                .collect(Collectors.toList());
        menuVO.setChildrenList(childrenList);
        return menuVO;
    }

}
