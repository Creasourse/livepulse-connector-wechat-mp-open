package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号用户 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpUserMapper extends BaseMapper<WechatMpUser> {
}
