package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpUser;

/**
 * 微信服务号用户服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpUserService extends IService<WechatMpUser> {

    /**
     * 根据 OpenID 查询用户
     *
     * @param accountId 账户 ID
     * @param openid    用户 OpenID
     * @return 用户信息
     */
    WechatMpUser getByOpenid(Long accountId, String openid);

    /**
     * 更新关注状态
     *
     * @param id             主键 ID
     * @param subscribeStatus 关注状态
     * @return 是否成功
     */
    boolean updateSubscribeStatus(Long id, Integer subscribeStatus);

    /**
     * 标记用户为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);

    /**
     * 批量标记用户为已处理
     *
     * @param accountId 账户 ID
     * @return 处理的记录数
     */
    int batchMarkAsProcessed(Long accountId);
}
