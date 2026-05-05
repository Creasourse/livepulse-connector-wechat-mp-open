package com.cs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs.entity.WechatMpSyncLog;
import com.cs.mapper.WechatMpSyncLogMapper;
import com.cs.service.WechatMpSyncLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 微信服务号同步日志服务实现
 *
 * @author Livepulse
 * @since 2.0
 */
@Service
@RequiredArgsConstructor
public class WechatMpSyncLogServiceImpl extends ServiceImpl<WechatMpSyncLogMapper, WechatMpSyncLog> implements WechatMpSyncLogService {

    private final WechatMpSyncLogMapper syncLogMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createLog(WechatMpSyncLog syncLog) {
        syncLog.setStartTime(LocalDateTime.now());
        return save(syncLog);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncStatus(Long id, String syncStatus) {
        WechatMpSyncLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setSyncStatus(syncStatus);
        return updateById(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateSyncResult(Long id, Integer totalCount, Integer successCount, Integer failureCount) {
        WechatMpSyncLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setTotalCount(totalCount);
        log.setSuccessCount(successCount);
        log.setFailureCount(failureCount);
        return updateById(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean completeSync(Long id) {
        WechatMpSyncLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setSyncStatus("COMPLETED");
        log.setEndTime(LocalDateTime.now());
        log.setDuration(java.time.Duration.between(log.getStartTime(), log.getEndTime()).toMillis());
        return updateById(log);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateErrorMessage(Long id, String errorMessage) {
        WechatMpSyncLog log = getById(id);
        if (log == null) {
            return false;
        }
        log.setErrorMessage(errorMessage);
        log.setSyncStatus("FAILED");
        log.setEndTime(LocalDateTime.now());
        log.setDuration(java.time.Duration.between(log.getStartTime(), log.getEndTime()).toMillis());
        return updateById(log);
    }
}
