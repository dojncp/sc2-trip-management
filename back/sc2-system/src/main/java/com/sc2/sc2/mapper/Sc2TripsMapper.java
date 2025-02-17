package com.sc2.sc2.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc2.sc2.domain.Sc2Trips;
import org.apache.ibatis.annotations.Param;

/**
 * 行程Mapper接口
 *
 * @author dojncp
 * @date 2025-02-10
 */
public interface Sc2TripsMapper extends BaseMapper<Sc2Trips> {
    /**
     * 查询行程
     *
     * @param tripId 行程主键
     * @return 行程
     */
    public Sc2Trips selectSc2TripsByTripId(Long tripId);

    /**
     * 查询行程列表
     *
     * @param sc2Trips 行程
     * @return 行程集合
     */
    public List<Sc2Trips> selectSc2TripsList(@Param("sc2Trips")Sc2Trips sc2Trips,
                                             @Param("userId") Long userId);

    /**
     * 新增行程
     *
     * @param sc2Trips 行程
     * @return 结果
     */
    public int insertSc2Trips(Sc2Trips sc2Trips);

    /**
     * 修改行程
     *
     * @param sc2Trips 行程
     * @return 结果
     */
    public int updateSc2Trips(Sc2Trips sc2Trips);

    /**
     * 删除行程
     *
     * @param tripId 行程主键
     * @return 结果
     */
    public int deleteSc2TripsByTripId(Long tripId);

    /**
     * 批量删除行程
     *
     * @param tripIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSc2TripsByTripIds(Long[] tripIds);
}
