package com.cs.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.time.LocalDateTime;

/**
 * 微信服务号图文消息实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_news")
@Schema(description = "微信服务号图文消息")
public class WechatMpNews extends Model<WechatMpNews> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("news_id")
    @Schema(description = "图文消息 ID")
    private String newsId;

    @TableField("title")
    @Schema(description = "标题")
    private String title;

    @TableField("author")
    @Schema(description = "作者")
    private String author;

    @TableField("digest")
    @Schema(description = "摘要")
    private String digest;

    @TableField("content")
    @Schema(description = "正文内容")
    private String content;

    @TableField("content_source_url")
    @Schema(description = "原文链接")
    private String contentSourceUrl;

    @TableField("thumb_media_id")
    @Schema(description = "封面图媒体 ID")
    private String thumbMediaId;

    @TableField("thumb_url")
    @Schema(description = "封面图 URL")
    private String thumbUrl;

    @TableField("show_cover_pic")
    @Schema(description = "是否显示封面")
    private Integer showCoverPic;

    @TableField("need_open_comment")
    @Schema(description = "是否打开评论")
    private Integer needOpenComment;

    @TableField("only_fans_can_comment")
    @Schema(description = "是否只有粉丝可以评论")
    private Integer onlyFansCanComment;

    @TableField("created_time_wechat")
    @Schema(description = "微信创建时间")
    private LocalDateTime createdTimeWechat;

    @TableField("updated_time_wechat")
    @Schema(description = "微信更新时间")
    private LocalDateTime updatedTimeWechat;

    @TableField("processed")
    @Schema(description = "是否已处理")
    private Boolean processed;

    @TableField("processed_time")
    @Schema(description = "处理时间")
    private LocalDateTime processedTime;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
