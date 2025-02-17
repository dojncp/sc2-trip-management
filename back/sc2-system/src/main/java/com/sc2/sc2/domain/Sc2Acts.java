package com.sc2.sc2.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.sc2.common.annotation.Excel;
import com.sc2.common.core.domain.BaseEntity;

/**
 * 移动对象 sc2_acts
 *
 * @author dojncp
 * @date 2025-02-11
 */
@Getter
@Setter
@ToString
@TableName(resultMap = "com.sc2.sc2.mapper.Sc2ActsMapper.Sc2ActsResult")
public class Sc2Acts extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 移动主键
     */
    @TableId(value = "act_id", type = IdType.AUTO)
    private Long actId;

    /**
     * 行程id
     */
    @Excel(name = "行程id")
    private Long tripId;

    /**
     * 移动名称
     */
    @Excel(name = "移动名称")
    private String actName;

    /**
     * 移动序号
     */
    @Excel(name = "移动序号")
    private Long actOrder;

    /**
     * 移动起点
     */
    @Excel(name = "移动起点")
    private String startPoint;

    /**
     * 移动终点
     */
    @Excel(name = "移动终点")
    private String endPoint;

    /**
     * 移动方式
     */
    @Excel(name = "移动方式")
    private String actType;

    /**
     * 移动媒介（具体车次等）
     */
    @Excel(name = "移动媒介", readConverterExp = "具=体车次等")
    private String actTran;

    /**
     * 媒介启动时间（发车等）
     */
    @Excel(name = "媒介启动时间", readConverterExp = "发=车等")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm") // 避免反序列化时出现错误
    private Date actDepTime;

    /**
     * 媒介停止时间（到站等）
     */
    @Excel(name = "媒介停止时间", readConverterExp = "到=站等")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8") // 避免反序列化时出现错误
    private Date actArrTime;

    /**
     * 等候时间（min）
     */
    @Excel(name = "等候时间", readConverterExp = "m=in")
    private Long minuteLastInterval;

    /**
     * 移动开销
     */
    @Excel(name = "移动开销")
    private Long actCost;

    /**
     * 开销币种
     */
    @Excel(name = "开销币种")
    private String actCostType;

    /**
     * 移动距离（km）
     */
    @Excel(name = "移动距离", readConverterExp = "k=m")
    private Double actDisKm;

    /**
     * 移动速度（km/h）
     */
    @Excel(name = "移动速度", readConverterExp = "k=m/h")
    private Double actSpeed;
}
