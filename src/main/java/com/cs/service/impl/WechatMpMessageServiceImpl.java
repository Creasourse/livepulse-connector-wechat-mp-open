package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpMessage;
import com.cs.mapper.WechatMpMessageMapper;
import com.cs.service.WechatMpMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号消息服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpMessageServiceImpl extends ServiceImpl<WechatMpMessageMapper, WechatMpMessage> implements WechatMpMessageService {

    private final WechatMpMessageMapper messageMapper;

    @Override
    public WechatMpMessage getByMsgId(Long accountId, String msgId) {
        LambdaQueryWrapper<WechatMpMessage> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpMessage::getAccountId, accountId)
                .eq(WechatMpMessage::getMsgId, msgId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        WechatMpMessage message = getById(id);
        if (message == null) {
            return false;
        }
        message.setProcessed(true);
        message.setProcessedTime(LocalDateTime.now());
        return updateById(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchMarkAsProcessed(Long accountId) {
        LambdaUpdateWrapper<WechatMpMessage> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(WechatMpMessage::getAccountId, accountId)
                .set(WechatMpMessage::getProcessed, true)
                .set(WechatMpMessage::getProcessedTime, LocalDateTime.now());
        return messageMapper.update(null, wrapper);
    }
}
