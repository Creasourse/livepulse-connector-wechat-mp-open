package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpMenu;

/**
 * 微信服务号菜单服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpMenuService extends IService<WechatMpMenu> {

    /**
     * 根据 Menu ID 查询菜单
     *
     * @param accountId 账户 ID
     * @param menuId    菜单 ID
     * @return 菜单信息
     */
    WechatMpMenu getByMenuId(Long accountId, Long menuId);

    /**
     * 根据账户 ID 删除所有菜单
     *
     * @param accountId 账户 ID
     * @return 是否成功
     */
    boolean deleteByAccountId(Long accountId);
}
