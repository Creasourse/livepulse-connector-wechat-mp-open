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
 * 微信服务号用户实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_user")
@Schema(description = "微信服务号用户")
public class WechatMpUser extends Model<WechatMpUser> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("openid")
    @Schema(description = "用户 OpenID")
    private String openid;

    @TableField("nickname")
    @Schema(description = "昵称")
    private String nickname;

    @TableField("sex")
    @Schema(description = "性别")
    private Integer sex;

    @TableField("language")
    @Schema(description = "语言")
    private String language;

    @TableField("city")
    @Schema(description = "城市")
    private String city;

    @TableField("province")
    @Schema(description = "省份")
    private String province;

    @TableField("country")
    @Schema(description = "国家")
    private String country;

    @TableField("head_img_url")
    @Schema(description = "头像 URL")
    private String headImgUrl;

    @TableField("subscribe_time")
    @Schema(description = "关注时间")
    private LocalDateTime subscribeTime;

    @TableField("unsubscribe_time")
    @Schema(description = "取消关注时间")
    private LocalDateTime unsubscribeTime;

    @TableField("subscribe_status")
    @Schema(description = "关注状态")
    private Integer subscribeStatus;

    @TableField("group_id")
    @Schema(description = "用户分组 ID")
    private Long groupId;

    @TableField("remark")
    @Schema(description = "备注名")
    private String remark;

    @TableField("user_tags")
    @Schema(description = "用户标签")
    private String userTags;

    @TableField("qr_scene_str")
    @Schema(description = "二维码场景值")
    private String qrSceneStr;

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
