package com.sc2.sc2.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sc2.common.annotation.Excel;
import com.sc2.common.core.domain.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 继承移动对象
 *
 * @author dojncp
 * @date 2025-02-11
 */
//@TableName(resultMap = "com.sc2.sc2.mapper.Sc2ActsMapper.Sc2ActsResult")
@Getter
@Setter
@ToString
public class Sc2ActsForDisplay extends Sc2Acts {
    // tripName
    private String tripName;
}