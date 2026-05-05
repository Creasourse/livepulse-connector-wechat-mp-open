package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpTag;
import com.cs.mapper.WechatMpTagMapper;
import com.cs.service.WechatMpTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 微信服务号标签服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpTagServiceImpl extends ServiceImpl<WechatMpTagMapper, WechatMpTag> implements WechatMpTagService {

    private final WechatMpTagMapper tagMapper;

    @Override
    public WechatMpTag getByTagId(Long accountId, Long tagId) {
        LambdaQueryWrapper<WechatMpTag> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpTag::getAccountId, accountId)
                .eq(WechatMpTag::getTagId, tagId);
        return getOne(wrapper);
    }
}
