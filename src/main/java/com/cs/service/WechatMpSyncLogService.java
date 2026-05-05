package com.cs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs.entity.WechatMpSyncLog;

/**
 * 微信服务号同步日志服务
 *
 * @author Livepulse
 * @since 2.0
 */
public interface WechatMpSyncLogService extends IService<WechatMpSyncLog> {

    /**
     * 创建同步日志
     *
     * @param syncLog 同步日志实体
     * @return 是否成功
     */
    boolean createLog(WechatMpSyncLog syncLog);

    /**
     * 更新同步状态
     *
     * @param id         主键 ID
     * @param syncStatus 同步状态
     * @return 是否成功
     */
    boolean updateSyncStatus(Long id, String syncStatus);

    /**
     * 更新同步结果
     *
     * @param id           主键 ID
     * @param totalCount   总记录数
     * @param successCount 成功数量
     * @param failureCount 失败数量
     * @return 是否成功
     */
    boolean updateSyncResult(Long id, Integer totalCount, Integer successCount, Integer failureCount);

    /**
     * 完成同步
     *
     * @param id 主键 ID
     * @return 是否成功
     */
    boolean completeSync(Long id);

    /**
     * 更新错误信息
     *
     * @param id           主键 ID
     * @param errorMessage 错误信息
     * @return 是否成功
     */
    boolean updateErrorMessage(Long id, String errorMessage);
}
