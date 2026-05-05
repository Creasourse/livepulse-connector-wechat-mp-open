package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpTag;
import com.cs.service.WechatMpTagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号标签控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/tag")
@RequiredArgsConstructor
@Tag(name = "微信服务号标签管理", description = "微信服务号标签数据查询接口")
public class WechatMpTagController {

    private final WechatMpTagService tagService;

    @GetMapping("/{id}")
    @Operation(summary = "获取标签详情", description = "根据 ID 获取标签详情")
    public ResponseEntity<WechatMpTag> getTag(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpTag tag = tagService.getById(id);
        if (tag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tag);
    }

    @GetMapping
    @Operation(summary = "分页查询标签", description = "分页查询微信服务号标签列表")
    public ResponseEntity<Page<WechatMpTag>> listTags(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "标签名称") @RequestParam(required = false) String tagName) {
        Page<WechatMpTag> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpTag> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpTag::getAccountId, accountId);
        }
        if (tagName != null && !tagName.isEmpty()) {
            wrapper.like(WechatMpTag::getTagName, tagName);
        }

        wrapper.orderByDesc(WechatMpTag::getCreatedTimeWechat);
        Page<WechatMpTag> result = tagService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }
}
