package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpAccount;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号账户配置 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpAccountMapper extends BaseMapper<WechatMpAccount> {
}
