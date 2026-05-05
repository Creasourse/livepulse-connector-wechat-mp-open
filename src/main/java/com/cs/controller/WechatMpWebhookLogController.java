package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpWebhookLog;
import com.cs.service.WechatMpWebhookLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号 Webhook 日志控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/webhook-log")
@RequiredArgsConstructor
@Tag(name = "微信服务号 Webhook 日志管理", description = "微信服务号 Webhook 日志查询接口")
public class WechatMpWebhookLogController {

    private final WechatMpWebhookLogService webhookLogService;

    @GetMapping("/{id}")
    @Operation(summary = "获取 Webhook 日志详情", description = "根据 ID 获取 Webhook 日志详情")
    public ResponseEntity<WechatMpWebhookLog> getWebhookLog(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpWebhookLog log = webhookLogService.getById(id);
        if (log == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(log);
    }

    @GetMapping
    @Operation(summary = "分页查询 Webhook 日志", description = "分页查询微信服务号 Webhook 日志列表")
    public ResponseEntity<Page<WechatMpWebhookLog>> listWebhookLogs(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "事件类型") @RequestParam(required = false) String eventType,
            @Parameter(description = "处理状态") @RequestParam(required = false) String processedStatus) {
        Page<WechatMpWebhookLog> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpWebhookLog> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpWebhookLog::getAccountId, accountId);
        }
        if (eventType != null && !eventType.isEmpty()) {
            wrapper.eq(WechatMpWebhookLog::getEventType, eventType);
        }
        if (processedStatus != null && !processedStatus.isEmpty()) {
            wrapper.eq(WechatMpWebhookLog::getProcessedStatus, processedStatus);
        }

        wrapper.orderByDesc(WechatMpWebhookLog::getReceivedTime);
        Page<WechatMpWebhookLog> result = webhookLogService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }
}
