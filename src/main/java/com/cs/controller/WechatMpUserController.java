package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpUser;
import com.cs.service.WechatMpUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号用户控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/user")
@RequiredArgsConstructor
@Tag(name = "微信服务号用户管理", description = "微信服务号用户数据查询接口")
public class WechatMpUserController {

    private final WechatMpUserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情", description = "根据 ID 获取用户详情")
    public ResponseEntity<WechatMpUser> getUser(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpUser user = userService.getById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping
    @Operation(summary = "分页查询用户", description = "分页查询微信服务号用户列表")
    public ResponseEntity<Page<WechatMpUser>> listUsers(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "昵称") @RequestParam(required = false) String nickname,
            @Parameter(description = "关注状态") @RequestParam(required = false) Integer subscribeStatus) {
        Page<WechatMpUser> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpUser> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpUser::getAccountId, accountId);
        }
        if (nickname != null && !nickname.isEmpty()) {
            wrapper.like(WechatMpUser::getNickname, nickname);
        }
        if (subscribeStatus != null) {
            wrapper.eq(WechatMpUser::getSubscribeStatus, subscribeStatus);
        }

        wrapper.orderByDesc(WechatMpUser::getSubscribeTime);
        Page<WechatMpUser> result = userService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将用户标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        userService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
