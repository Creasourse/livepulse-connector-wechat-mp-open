package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpSyncLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号同步日志 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpSyncLogMapper extends BaseMapper<WechatMpSyncLog> {
}
