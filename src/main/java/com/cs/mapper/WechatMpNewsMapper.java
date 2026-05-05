package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpNews;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号图文消息 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpNewsMapper extends BaseMapper<WechatMpNews> {
}
