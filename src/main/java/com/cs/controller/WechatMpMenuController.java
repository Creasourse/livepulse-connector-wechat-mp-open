package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpMenu;
import com.cs.service.WechatMpMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号菜单控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/menu")
@RequiredArgsConstructor
@Tag(name = "微信服务号菜单管理", description = "微信服务号菜单数据查询接口")
public class WechatMpMenuController {

    private final WechatMpMenuService menuService;

    @GetMapping("/{id}")
    @Operation(summary = "获取菜单详情", description = "根据 ID 获取菜单详情")
    public ResponseEntity<WechatMpMenu> getMenu(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpMenu menu = menuService.getById(id);
        if (menu == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(menu);
    }

    @GetMapping
    @Operation(summary = "分页查询菜单", description = "分页查询微信服务号菜单列表")
    public ResponseEntity<Page<WechatMpMenu>> listMenus(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "父菜单 ID") @RequestParam(required = false) Long parentId) {
        Page<WechatMpMenu> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpMenu> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpMenu::getAccountId, accountId);
        }
        if (parentId != null) {
            wrapper.eq(WechatMpMenu::getParentId, parentId);
        }

        wrapper.orderByAsc(WechatMpMenu::getSortOrder);
        Page<WechatMpMenu> result = menuService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }
}
