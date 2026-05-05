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
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 微信服务号同步日志实体
 *
 * @author Livepulse
 * @since 2.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("wechat_mp_sync_log")
@Schema(description = "微信服务号同步日志")
public class WechatMpSyncLog extends Model<WechatMpSyncLog> {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "主键 ID")
    private Long id;

    @TableField("account_id")
    @Schema(description = "关联的账户 ID")
    private Long accountId;

    @TableField("sync_type")
    @Schema(description = "同步类型")
    private String syncType;

    @TableField("sync_method")
    @Schema(description = "同步方式")
    private String syncMethod;

    @TableField("start_date")
    @Schema(description = "开始日期")
    private LocalDate startDate;

    @TableField("end_date")
    @Schema(description = "结束日期")
    private LocalDate endDate;

    @TableField("sync_status")
    @Schema(description = "同步状态")
    private String syncStatus;

    @TableField("total_count")
    @Schema(description = "总记录数")
    private Integer totalCount;

    @TableField("success_count")
    @Schema(description = "成功数量")
    private Integer successCount;

    @TableField("failure_count")
    @Schema(description = "失败数量")
    private Integer failureCount;

    @TableField("error_message")
    @Schema(description = "错误信息")
    private String errorMessage;

    @TableField("start_time")
    @Schema(description = "开始时间")
    private LocalDateTime startTime;

    @TableField("end_time")
    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @TableField("duration")
    @Schema(description = "耗时（毫秒）")
    private Long duration;

    @TableField("create_time")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
