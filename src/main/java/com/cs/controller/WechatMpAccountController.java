package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpAccount;
import com.cs.service.WechatMpAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号账户配置控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/account")
@RequiredArgsConstructor
@Tag(name = "微信服务号账户管理", description = "微信服务号账户配置管理接口")
public class WechatMpAccountController {

    private final WechatMpAccountService accountService;

    @PostMapping
    @Operation(summary = "添加账户配置", description = "添加新的微信服务号账户配置")
    public ResponseEntity<WechatMpAccount> addAccount(@RequestBody WechatMpAccount account) {
        account.setCreateTime(java.time.LocalDateTime.now());
        account.setUpdateTime(java.time.LocalDateTime.now());
        account.setEnabled(true);
        account.setSyncStatus("pending");
        accountService.save(account);
        return ResponseEntity.ok(account);
    }

    @PutMapping("/{id}")
    @Operation(summary = "更新账户配置", description = "更新微信服务号账户配置")
    public ResponseEntity<WechatMpAccount> updateAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @RequestBody WechatMpAccount account) {
        account.setId(id);
        account.setUpdateTime(java.time.LocalDateTime.now());
        accountService.updateById(account);
        return ResponseEntity.ok(account);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除账户配置", description = "删除微信服务号账户配置")
    public ResponseEntity<Void> deleteAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取账户配置", description = "根据 ID 获取账户配置")
    public ResponseEntity<WechatMpAccount> getAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpAccount account = accountService.getById(id);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(account);
    }

    @GetMapping
    @Operation(summary = "分页查询账户配置", description = "分页查询微信服务号账户配置列表")
    public ResponseEntity<Page<WechatMpAccount>> listAccounts(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "公众号名称") @RequestParam(required = false) String accountName) {
        Page<WechatMpAccount> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpAccount> wrapper = new LambdaQueryWrapper<>();
        if (accountName != null && !accountName.isEmpty()) {
            wrapper.like(WechatMpAccount::getAccountName, accountName);
        }
        wrapper.orderByDesc(WechatMpAccount::getCreateTime);
        Page<WechatMpAccount> result = accountService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/enable")
    @Operation(summary = "启用账户", description = "启用指定的账户")
    public ResponseEntity<Void> enableAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.enableAccount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disable")
    @Operation(summary = "禁用账户", description = "禁用指定的账户")
    public ResponseEntity<Void> disableAccount(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.disableAccount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/enable-webhook")
    @Operation(summary = "启用 Webhook", description = "启用账户的 Webhook 接收")
    public ResponseEntity<Void> enableWebhook(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.enableWebhook(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disable-webhook")
    @Operation(summary = "禁用 Webhook", description = "禁用账户的 Webhook 接收")
    public ResponseEntity<Void> disableWebhook(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        accountService.disableWebhook(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/sync/users")
    @Operation(summary = "手动同步用户", description = "手动触发用户数据同步")
    public ResponseEntity<String> syncUsers(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现用户同步逻辑
        return ResponseEntity.ok("用户同步任务已提交");
    }

    @PostMapping("/{id}/sync/materials")
    @Operation(summary = "手动同步素材", description = "手动触发素材数据同步")
    public ResponseEntity<String> syncMaterials(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现素材同步逻辑
        return ResponseEntity.ok("素材同步任务已提交");
    }

    @PostMapping("/{id}/sync/messages")
    @Operation(summary = "手动同步消息", description = "手动触发消息数据同步")
    public ResponseEntity<String> syncMessages(
            @Parameter(description = "主键 ID") @PathVariable Long id,
            @Parameter(description = "同步最近多少天") @RequestParam(defaultValue = "30") Integer daysAgo) {
        // TODO: 实现消息同步逻辑
        return ResponseEntity.ok("消息同步任务已提交");
    }
}
