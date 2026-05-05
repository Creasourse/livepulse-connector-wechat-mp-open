package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpAccount;

/**
 * 微信服务号账户配置服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpAccountService extends IService<WechatMpAccount> {

    /**
     * 根据 AppID 查询账户
     *
     * @param appId 应用 ID
     * @return 账户配置
     */
    WechatMpAccount getByAppId(String appId);

    /**
     * 根据 Account ID 查询账户
     *
     * @param accountId 账户 ID
     * @return 账户配置
     */
    WechatMpAccount getByAccountId(String accountId);

    /**
     * 启用账户
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean enableAccount(Long id);

    /**
     * 禁用账户
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean disableAccount(Long id);

    /**
     * 更新同步状态
     *
     * @param id         主键 ID
     * @param syncStatus 同步状态
     * @return 是否成功
     */
    boolean updateSyncStatus(Long id, String syncStatus);

    /**
     * 更新最后同步时间
     *
     * @param id       主键 ID
     * @param syncType 同步类型 (user/material/message)
     * @return 是否成功
     */
    boolean updateLastSyncTime(Long id, String syncType);

    /**
     * 启用 Webhook
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean enableWebhook(Long id);

    /**
     * 禁用 Webhook
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean disableWebhook(Long id);
}
