package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpSyncLog;
import com.cs.service.WechatMpSyncLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 微信服务号同步日志控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/sync-log")
@RequiredArgsConstructor
@Tag(name = "微信服务号同步日志管理", description = "微信服务号同步日志查询接口")
public class WechatMpSyncLogController {

    private final WechatMpSyncLogService syncLogService;

    @GetMapping("/{id}")
    @Operation(summary = "获取同步日志详情", description = "根据 ID 获取同步日志详情")
    public ResponseEntity<WechatMpSyncLog> getSyncLog(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpSyncLog log = syncLogService.getById(id);
        if (log == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(log);
    }

    @GetMapping
    @Operation(summary = "分页查询同步日志", description = "分页查询微信服务号同步日志列表")
    public ResponseEntity<Page<WechatMpSyncLog>> listSyncLogs(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "同步类型") @RequestParam(required = false) String syncType,
            @Parameter(description = "同步方式") @RequestParam(required = false) String syncMethod,
            @Parameter(description = "同步状态") @RequestParam(required = false) String syncStatus,
            @Parameter(description = "开始日期") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @Parameter(description = "结束日期") @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Page<WechatMpSyncLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpSyncLog> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpSyncLog::getAccountId, accountId);
        }
        if (syncType != null && !syncType.isEmpty()) {
            wrapper.eq(WechatMpSyncLog::getSyncType, syncType);
        }
        if (syncMethod != null && !syncMethod.isEmpty()) {
            wrapper.eq(WechatMpSyncLog::getSyncMethod, syncMethod);
        }
        if (syncStatus != null && !syncStatus.isEmpty()) {
            wrapper.eq(WechatMpSyncLog::getSyncStatus, syncStatus);
        }
        if (startDate != null) {
            wrapper.ge(WechatMpSyncLog::getStartTime, startDate.atStartOfDay());
        }
        if (endDate != null) {
            wrapper.le(WechatMpSyncLog::getStartTime, endDate.atTime(23, 59, 59));
        }

        wrapper.orderByDesc(WechatMpSyncLog::getStartTime);
        Page<WechatMpSyncLog> result = syncLogService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }
}
