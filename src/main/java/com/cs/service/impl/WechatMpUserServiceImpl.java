package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpUser;
import com.cs.mapper.WechatMpUserMapper;
import com.cs.service.WechatMpUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号用户服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpUserServiceImpl extends ServiceImpl<WechatMpUserMapper, WechatMpUser> implements WechatMpUserService {

    private final WechatMpUserMapper userMapper;

    @Override
    public WechatMpUser getByOpenid(Long accountId, String openid) {
        LambdaQueryWrapper<WechatMpUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpUser::getAccountId, accountId)
                .eq(WechatMpUser::getOpenid, openid);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSubscribeStatus(Long id, Integer subscribeStatus) {
        WechatMpUser user = getById(id);
        if (user == null) {
            return false;
        }
        user.setSubscribeStatus(subscribeStatus);
        if (subscribeStatus == 0) {
            user.setUnsubscribeTime(LocalDateTime.now());
        } else {
            user.setSubscribeTime(LocalDateTime.now());
        }
        return updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsProcessed(Long id) {
        WechatMpUser user = getById(id);
        if (user == null) {
            return false;
        }
        user.setProcessed(true);
        user.setProcessedTime(LocalDateTime.now());
        return updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchMarkAsProcessed(Long accountId) {
        LambdaUpdateWrapper<WechatMpUser> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(WechatMpUser::getAccountId, accountId)
                .set(WechatMpUser::getProcessed, true)
                .set(WechatMpUser::getProcessedTime, LocalDateTime.now());
        return userMapper.update(null, wrapper);
    }
}
