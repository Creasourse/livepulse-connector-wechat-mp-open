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
 * 微信服务号素材实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_material")
@Schema(description = "微信服务号素材")
public class WechatMpMaterial extends Model<WechatMpMaterial> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("media_id")
    @Schema(description = "素材媒体 ID")
    private String mediaId;

    @TableField("material_type")
    @Schema(description = "素材类型")
    private String materialType;

    @TableField("title")
    @Schema(description = "素材标题")
    private String title;

    @TableField("description")
    @Schema(description = "素材描述")
    private String description;

    @TableField("name")
    @Schema(description = "素材名称")
    private String name;

    @TableField("url")
    @Schema(description = "素材 URL")
    private String url;

    @TableField("thumb_media_id")
    @Schema(description = "缩略图媒体 ID")
    private String thumbMediaId;

    @TableField("thumb_url")
    @Schema(description = "缩略图 URL")
    private String thumbUrl;

    @TableField("media_type")
    @Schema(description = "媒体类型")
    private String mediaType;

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
