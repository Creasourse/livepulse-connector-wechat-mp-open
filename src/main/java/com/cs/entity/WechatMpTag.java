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
 * 微信服务号标签实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_tag")
@Schema(description = "微信服务号标签")
public class WechatMpTag extends Model<WechatMpTag> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("tag_id")
    @Schema(description = "标签 ID")
    private Long tagId;

    @TableField("tag_name")
    @Schema(description = "标签名称")
    private String tagName;

    @TableField("user_count")
    @Schema(description = "用户数量")
    private Integer userCount;

    @TableField("created_time_wechat")
    @Schema(description = "微信创建时间")
    private LocalDateTime createdTimeWechat;

    @TableField("updated_time_wechat")
    @Schema(description = "微信更新时间")
    private LocalDateTime updatedTimeWechat;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
