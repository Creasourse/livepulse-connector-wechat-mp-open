package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpTag;

/**
 * 微信服务号标签服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpTagService extends IService<WechatMpTag> {

    /**
     * 根据 Tag ID 查询标签
     *
     * @param accountId 账户 ID
     * @param tagId     标签 ID
     * @return 标签信息
     */
    WechatMpTag getByTagId(Long accountId, Long tagId);
}
