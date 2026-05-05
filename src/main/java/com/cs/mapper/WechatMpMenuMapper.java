package com.cs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs.entity.WechatMpMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信服务号菜单 Mapper
 *
 * @author Livepulse
 * @since 2.0
 */
@Mapper
public interface WechatMpMenuMapper extends BaseMapper<WechatMpMenu> {
}
