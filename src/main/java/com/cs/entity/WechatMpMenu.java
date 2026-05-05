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
 * 微信服务号菜单实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_menu")
@Schema(description = "微信服务号菜单")
public class WechatMpMenu extends Model<WechatMpMenu> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("menu_id")
    @Schema(description = "菜单 ID")
    private Long menuId;

    @TableField("parent_id")
    @Schema(description = "父菜单 ID")
    private Long parentId;

    @TableField("menu_type")
    @Schema(description = "菜单类型")
    private String menuType;

    @TableField("menu_key")
    @Schema(description = "菜单 KEY")
    private String menuKey;

    @TableField("name")
    @Schema(description = "菜单名称")
    private String name;

    @TableField("value")
    @Schema(description = "菜单值")
    private String value;

    @TableField("menu_content")
    @Schema(description = "菜单内容")
    private String menuContent;

    @TableField("sort_order")
    @Schema(description = "排序")
    private Integer sortOrder;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @TableField("update_time")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
