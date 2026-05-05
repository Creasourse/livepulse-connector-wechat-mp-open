package com.cs.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs.entity.WechatMpNews;
import com.cs.service.WechatMpNewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 微信服务号图文消息控制器
 *
 * @author Livepulse
 * @since 2.0
 */
@RestController
@RequestMapping("/wechat-mp/news")
@RequiredArgsConstructor
@Tag(name = "微信服务号图文消息管理", description = "微信服务号图文消息数据查询接口")
public class WechatMpNewsController {

    private final WechatMpNewsService newsService;

    @GetMapping("/{id}")
    @Operation(summary = "获取图文消息详情", description = "根据 ID 获取图文消息详情")
    public ResponseEntity<WechatMpNews> getNews(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        WechatMpNews news = newsService.getById(id);
        if (news == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(news);
    }

    @GetMapping
    @Operation(summary = "分页查询图文消息", description = "分页查询微信服务号图文消息列表")
    public ResponseEntity<Page<WechatMpNews>> listNews(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") Integer pageNum,
            @Parameter(description = "页大小") @RequestParam(defaultValue = "10") Integer pageSize,
            @Parameter(description = "账户 ID") @RequestParam(required = false) Long accountId,
            @Parameter(description = "标题") @RequestParam(required = false) String title,
            @Parameter(description = "作者") @RequestParam(required = false) String author) {
        Page<WechatMpNews> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<WechatMpNews> wrapper = new LambdaQueryWrapper<>();

        if (accountId != null) {
            wrapper.eq(WechatMpNews::getAccountId, accountId);
        }
        if (title != null && !title.isEmpty()) {
            wrapper.like(WechatMpNews::getTitle, title);
        }
        if (author != null && !author.isEmpty()) {
            wrapper.like(WechatMpNews::getAuthor, author);
        }

        wrapper.orderByDesc(WechatMpNews::getCreatedTimeWechat);
        Page<WechatMpNews> result = newsService.page(page, wrapper);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/{id}/process")
    @Operation(summary = "标记为已处理", description = "将图文消息标记为已处理")
    public ResponseEntity<Void> markAsProcessed(
            @Parameter(description = "主键 ID") @PathVariable Long id) {
        newsService.markAsProcessed(id);
        return ResponseEntity.ok().build();
    }
}
