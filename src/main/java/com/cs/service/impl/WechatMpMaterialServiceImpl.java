package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpMaterial;
import com.cs.mapper.WechatMpMaterialMapper;
import com.cs.service.WechatMpMaterialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号素材服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpMaterialServiceImpl extends ServiceImpl<WechatMpMaterialMapper, WechatMpMaterial> implements WechatMpMaterialService {

    private final WechatMpMaterialMapper materialMapper;

    @Override
    public WechatMpMaterial getByMediaId(Long accountId, String mediaId) {
        LambdaQueryWrapper<WechatMpMaterial> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpMaterial::getAccountId, accountId)
                .eq(WechatMpMaterial::getMediaId, mediaId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        WechatMpMaterial material = getById(id);
        if (material == null) {
            return false;
        }
        material.setProcessed(true);
        material.setProcessedTime(LocalDateTime.now());
        return updateById(material);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchMarkAsProcessed(Long accountId) {
        LambdaUpdateWrapper<WechatMpMaterial> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(WechatMpMaterial::getAccountId, accountId)
                .set(WechatMpMaterial::getProcessed, true)
                .set(WechatMpMaterial::getProcessedTime, LocalDateTime.now());
        return materialMapper.update(null, wrapper);
    }
}
