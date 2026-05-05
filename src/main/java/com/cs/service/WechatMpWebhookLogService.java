package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpWebhookLog;

/**
 * 微信服务号 Webhook 日志服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpWebhookLogService extends IService<WechatMpWebhookLog> {

    /**
     * 创建 Webhook 日志
     *
     * @param webhookLog Webhook 日志实体
     * @return 是否成功
     */
    boolean createLog(WechatMpWebhookLog webhookLog);

    /**
     * 更新处理状态
     *
     * @param id             主键 ID
     * @param processedStatus 处理状态
     * @return 是否成功
     */
    boolean updateProcessedStatus(Long id, String processedStatus);

    /**
     * 更新错误信息
     *
     * @param id           主键 ID
     * @param errorMessage 错误信息
     * @return 是否成功
     */
    boolean updateErrorMessage(Long id, String errorMessage);

    /**
     * 增加重试次数
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean incrementRetryCount(Long id);
}
