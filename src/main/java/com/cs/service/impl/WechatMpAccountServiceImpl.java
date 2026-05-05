package com.cs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpAccount;
import com.cs.mapper.WechatMpAccountMapper;
import com.cs.service.WechatMpUserService;
import com.cs.service.WechatMpMaterialService;
import com.cs.service.WechatMpMessageService;
import com.cs.service.WechatMpAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号账户配置服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpAccountServiceImpl extends ServiceImpl<WechatMpAccountMapper, WechatMpAccount> implements WechatMpAccountService {

    private final WechatMpAccountMapper accountMapper;

    @Lazy
    @Autowired
    private WechatMpUserService userService;

    @Lazy
    @Autowired
    private WechatMpMaterialService materialService;

    @Lazy
    @Autowired
    private WechatMpMessageService messageService;

    @Override
    public WechatMpAccount getByAppId(String appId) {
        LambdaQueryWrapper<WechatMpAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpAccount::getAppId, appId);
        return getOne(wrapper);
    }

    @Override
    public WechatMpAccount getByAccountId(String accountId) {
        LambdaQueryWrapper<WechatMpAccount> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WechatMpAccount::getAccountId, accountId);
        return getOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableAccount(Long id) {
        WechatMpAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setEnabled(true);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableAccount(Long id) {
        WechatMpAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setEnabled(false);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncStatus(Long id, String syncStatus) {
        WechatMpAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setSyncStatus(syncStatus);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateLastSyncTime(Long id, String syncType) {
        WechatMpAccount account = getById(id);
        if (account == null) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        switch (syncType.toLowerCase()) {
            case "user":
                account.setLastUserSyncTime(now);
                break;
            case "material":
                account.setLastMaterialSyncTime(now);
                break;
            case "message":
                account.setLastMessageSyncTime(now);
                break;
            default:
                return false;
        }

        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean enableWebhook(Long id) {
        WechatMpAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setWebhookEnabled(true);
        return updateById(account);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean disableWebhook(Long id) {
        WechatMpAccount account = getById(id);
        if (account == null) {
            return false;
        }
        account.setWebhookEnabled(false);
        return updateById(account);
    }
}
