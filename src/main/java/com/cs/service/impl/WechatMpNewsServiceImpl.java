package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpNews;
import com.cs.mapper.WechatMpNewsMapper;
import com.cs.service.WechatMpNewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号图文消息服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpNewsServiceImpl extends ServiceImpl<WechatMpNewsMapper, WechatMpNews> implements WechatMpNewsService {

    private final WechatMpNewsMapper newsMapper;

    @Override
    public WechatMpNews getByNewsId(Long accountId, String newsId) {
        LambdaQueryWrapper<WechatMpNews> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpNews::getAccountId, accountId)
                .eq(WechatMpNews::getNewsId, newsId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        WechatMpNews news = getById(id);
        if (news == null) {
            return false;
        }
        news.setProcessed(true);
        news.setProcessedTime(LocalDateTime.now());
        return updateById(news);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchMarkAsProcessed(Long accountId) {
        LambdaUpdateWrapper<WechatMpNews> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(WechatMpNews::getAccountId, accountId)
                .set(WechatMpNews::getProcessed, true)
                .set(WechatMpNews::getProcessedTime, LocalDateTime.now());
        return newsMapper.update(null, wrapper);
    }
}
