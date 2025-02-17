package com.sc2.sc2.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户和行程关联
 */
@Getter
@Setter
@ToString
@TableName("sc2_user_trip")
public class Sc2UserTrip {

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long tripId;
}
