package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpNews;

/**
 * 微信服务号图文消息服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpNewsService extends IService<WechatMpNews> {

    /**
     * 根据 News ID 查询图文消息
     *
     * @param accountId 账户 ID
     * @param newsId    图文消息 ID
     * @return 图文消息信息
     */
    WechatMpNews getByNewsId(Long accountId, String newsId);

    /**
     * 标记图文消息为已处理
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean markAsProcessed(Long id);

    /**
     * 批量标记图文消息为已处理
     *
     * @param accountId 账户 ID
     * @return 处理的记录数
     */
    int batchMarkAsProcessed(Long accountId);
}
