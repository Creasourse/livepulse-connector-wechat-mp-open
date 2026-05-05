package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpMaterial;

/**
 * 微信服务号素材服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpMaterialService extends IService<WechatMpMaterial> {

    /**
     * 根据 Media ID 查询素材
     *
     * @param accountId 账户 ID
     * @param mediaId   媒体 ID
     * @return 素材信息
     */
    WechatMpMaterial getByMediaId(Long accountId, String mediaId);

    /**
     * 标记素材为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);

    /**
     * 批量标记素材为已处理
     *
     * @param accountId 账户 ID
     * @return 处理的记录数
     */
    int batchMarkAsProcessed(Long accountId);
}
