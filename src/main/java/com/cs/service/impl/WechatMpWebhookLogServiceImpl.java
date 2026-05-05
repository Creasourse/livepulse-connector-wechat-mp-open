package com.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpWebhookLog;
import com.cs.mapper.WechatMpWebhookLogMapper;
import com.cs.service.WechatMpWebhookLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号 Webhook 日志服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpWebhookLogServiceImpl extends ServiceImpl<WechatMpWebhookLogMapper, WechatMpWebhookLog> implements WechatMpWebhookLogService {

    private final WechatMpWebhookLogMapper webhookLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createLog(WechatMpWebhookLog webhookLog) {
        webhookLog.setReceivedTime(LocalDateTime.now());
        return save(webhookLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateProcessedStatus(Long id, String processedStatus) {
        WechatMpWebhookLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setProcessedStatus(processedStatus);
        log.setProcessedTime(LocalDateTime.now());
        return updateById(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateErrorMessage(Long id, String errorMessage) {
        WechatMpWebhookLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setErrorMessage(errorMessage);
        return updateById(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean incrementRetryCount(Long id) {
        WechatMpWebhookLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setRetryCount(log.getRetryCount() == null ? 1 : log.getRetryCount() + 1);
        return updateById(log);
    }
}
