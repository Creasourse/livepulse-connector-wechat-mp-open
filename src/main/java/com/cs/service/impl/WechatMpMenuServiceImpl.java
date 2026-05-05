package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpMenu;
import com.cs.mapper.WechatMpMenuMapper;
import com.cs.service.WechatMpMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 微信服务号菜单服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpMenuServiceImpl extends ServiceImpl<WechatMpMenuMapper, WechatMpMenu> implements WechatMpMenuService {

    private final WechatMpMenuMapper menuMapper;

    @Override
    public WechatMpMenu getByMenuId(Long accountId, Long menuId) {
        LambdaQueryWrapper<WechatMpMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpMenu::getAccountId, accountId)
                .eq(WechatMpMenu::getMenuId, menuId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByAccountId(Long accountId) {
        LambdaQueryWrapper<WechatMpMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpMenu::getAccountId, accountId);
        return remove(wrapper);
    }
}
