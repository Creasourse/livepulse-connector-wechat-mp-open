package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpMaterial;
import com.cs.service.WechatMpMaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号素材控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/material")
@RequiredArgsConstructor
@Tag(name = "微信服务号素材管理", description = "微信服务号素材数据查询接口")
public class WechatMpMaterialController {

    private final WechatMpMaterialService materialService;

    @GetMapping("/{id}")
    @Operation(summary = "获取素材详情", description = "根据 ID 获取素材详情")
    public ResponseEntity<WechatMpMaterial> getMaterial(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpMaterial material = materialService.getById(id);
        if (material == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(material);
    }

    @GetMapping
    @Operation(summary = "分页查询素材", description = "分页查询微信服务号素材列表")
    public ResponseEntity<Page<WechatMpMaterial>> listMaterials(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "素材类型") @RequestParam(required = false) String materialType,
            @Parameter(description = "标题") @RequestParam(required = false) String title) {
        Page<WechatMpMaterial> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpMaterial> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpMaterial::getAccountId, accountId);
        }
        if (materialType != null && !materialType.isEmpty()) {
            wrapper.eq(WechatMpMaterial::getMaterialType, materialType);
        }
        if (title != null && !title.isEmpty()) {
            wrapper.like(WechatMpMaterial::getTitle, title);
        }

        wrapper.orderByDesc(WechatMpMaterial::getCreatedTimeWechat);
        Page<WechatMpMaterial> result = materialService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将素材标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        materialService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
