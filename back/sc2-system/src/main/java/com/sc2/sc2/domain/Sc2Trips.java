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
 * 行程对象 sc2_trips
 *
 * @author dojncp
 * @date 2025-02-10
 */
@Getter
@Setter
@ToString
@TableName(resultMap = "com.sc2.sc2.mapper.Sc2TripsMapper.Sc2TripsResult")
public class Sc2Trips extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 行程主键
     */
    @TableId(value = "trip_id", type = IdType.AUTO)
    private Long tripId;

    /**
     * 行程名称
     */
    @Excel(name = "行程名称")
    private String tripName;

    /**
     * 行程起始日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "行程起始日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tripStartDate;

    /**
     * 行程终止日
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "行程终止日", width = 30, dateFormat = "yyyy-MM-dd")
    private Date tripEndDate;

    /**
     * 行程起始地
     */
    @Excel(name = "行程起始地")
    private String tripDepaturePlace;

    /**
     * 行程目的地
     */
    @Excel(name = "行程目的地")
    private String tripDestination;

    /**
     * 行程预算
     */
    @Excel(name = "行程预算")
    private Long tripBudget;

    /**
     * 行程预算币种
     */
    @Excel(name = "行程预算币种")
    private String tripBudgetCurrency;
}