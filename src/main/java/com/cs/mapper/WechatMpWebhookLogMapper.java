package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpWebhookLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号 Webhook 日志 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpWebhookLogMapper extends BaseMapper<WechatMpWebhookLog> {
}
