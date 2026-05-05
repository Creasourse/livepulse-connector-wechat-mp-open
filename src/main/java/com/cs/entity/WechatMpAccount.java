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
 * 微信服务号账户配置实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_account")
@Schema(description = "微信服务号账户配置")
public class WechatMpAccount extends Model<WechatMpAccount> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "账户唯一标识")
    private String accountId;

    @TableField("account_name")
    @Schema(description = "公众号名称")
    private String accountName;

    @TableField("app_id")
    @Schema(description = "应用 ID")
    private String appId;

    @TableField("app_secret")
    @Schema(description = "应用密钥")
    private String appSecret;

    @TableField("token")
    @Schema(description = "令牌")
    private String token;

    @TableField("encoding_aes_key")
    @Schema(description = "消息加密密钥")
    private String encodingAesKey;

    @TableField("account_type")
    @Schema(description = "账号类型")
    private String accountType;

    @TableField("service_type_info")
    @Schema(description = "服务类型信息")
    private String serviceTypeInfo;

    @TableField("verify_type_info")
    @Schema(description = "认证类型信息")
    private String verifyTypeInfo;

    @TableField("business_info")
    @Schema(description = "功能介绍")
    private String businessInfo;

    @TableField("principal_name")
    @Schema(description = "主体名称")
    private String principalName;

    @TableField("signature")
    @Schema(description = "签名")
    private String signature;

    @TableField("enabled")
    @Schema(description = "是否启用")
    private Boolean enabled;

    @TableField("sync_status")
    @Schema(description = "同步状态")
    private String syncStatus;

    @TableField("last_user_sync_time")
    @Schema(description = "最后用户同步时间")
    private LocalDateTime lastUserSyncTime;

    @TableField("last_material_sync_time")
    @Schema(description = "最后素材同步时间")
    private LocalDateTime lastMaterialSyncTime;

    @TableField("last_message_sync_time")
    @Schema(description = "最后消息同步时间")
    private LocalDateTime lastMessageSyncTime;

    @TableField("webhook_enabled")
    @Schema(description = "是否启用 Webhook")
    private Boolean webhookEnabled;

    @TableField("webhook_url")
    @Schema(description = "Webhook 回调 URL")
    private String webhookUrl;

    @TableField("last_error_message")
    @Schema(description = "最后错误信息")
    private String lastErrorMessage;

    @TableField("retry_count")
    @Schema(description = "重试次数")
    private Integer retryCount;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @TableField("create_by")
    @Schema(description = "创建人")
    private String createBy;

    @TableField("update_by")
    @Schema(description = "更新人")
    private String updateBy;
}
