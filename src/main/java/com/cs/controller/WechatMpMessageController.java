package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpMessage;
import com.cs.service.WechatMpMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号消息控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/message")
@RequiredArgsConstructor
@Tag(name = "微信服务号消息管理", description = "微信服务号消息数据查询接口")
public class WechatMpMessageController {

    private final WechatMpMessageService messageService;

    @GetMapping("/{id}")
    @Operation(summary = "获取消息详情", description = "根据 ID 获取消息详情")
    public ResponseEntity<WechatMpMessage> getMessage(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpMessage message = messageService.getById(id);
        if (message == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(message);
    }

    @GetMapping
    @Operation(summary = "分页查询消息", description = "分页查询微信服务号消息列表")
    public ResponseEntity<Page<WechatMpMessage>> listMessages(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "消息类型") @RequestParam(required = false) String msgType,
            @Parameter(description = "用户 OpenID") @RequestParam(required = false) String openid) {
        Page<WechatMpMessage> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpMessage> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpMessage::getAccountId, accountId);
        }
        if (msgType != null && !msgType.isEmpty()) {
            wrapper.eq(WechatMpMessage::getMsgType, msgType);
        }
        if (openid != null && !openid.isEmpty()) {
            wrapper.eq(WechatMpMessage::getOpenid, openid);
        }

        wrapper.orderByDesc(WechatMpMessage::getCreateTimeWechat);
        Page<WechatMpMessage> result = messageService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将消息标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        messageService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
