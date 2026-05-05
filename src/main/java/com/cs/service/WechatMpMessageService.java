package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpMessage;

/**
 * 微信服务号消息服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpMessageService extends IService<WechatMpMessage> {

    /**
     * 根据 Msg ID 查询消息
     *
     * @param accountId 账户 ID
     * @param msgId     消息 ID
     * @return 消息信息
     */
    WechatMpMessage getByMsgId(Long accountId, String msgId);

    /**
     * 标记消息为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);

    /**
     * 批量标记消息为已处理
     *
     * @param accountId 账户 ID
     * @return 处理的记录数
     */
    int batchMarkAsProcessed(Long accountId);
}
