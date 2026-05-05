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
 * 微信服务号消息实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_message")
@Schema(description = "微信服务号消息")
public class WechatMpMessage extends Model<WechatMpMessage> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("msg_id")
    @Schema(description = "消息 ID")
    private String msgId;

    @TableField("msg_type")
    @Schema(description = "消息类型")
    private String msgType;

    @TableField("openid")
    @Schema(description = "用户 OpenID")
    private String openid;

    @TableField("content")
    @Schema(description = "消息内容")
    private String content;

    @TableField("media_id")
    @Schema(description = "媒体 ID")
    private String mediaId;

    @TableField("msg_data")
    @Schema(description = "消息数据")
    private String msgData;

    @TableField("create_time_wechat")
    @Schema(description = "微信创建时间")
    private LocalDateTime createTimeWechat;

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
