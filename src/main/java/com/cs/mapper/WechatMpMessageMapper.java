package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号消息 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpMessageMapper extends BaseMapper<WechatMpMessage> {
}
