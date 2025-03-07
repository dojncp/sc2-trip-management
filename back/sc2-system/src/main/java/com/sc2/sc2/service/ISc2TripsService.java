package com.sc2.sc2.service;

import java.util.List;
import com.sc2.sc2.domain.Sc2Trips;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 行程Service接口
 *
 * @author dojncp
 * @date 2025-02-10
 */
public interface ISc2TripsService extends IService<Sc2Trips> {
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
    public List<Sc2Trips> selectSc2TripsList(Sc2Trips sc2Trips);

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
     * 批量删除行程
     *
     * @param tripIds 需要删除的行程主键集合
     * @return 结果
     */
    public int deleteSc2TripsByTripIds(Long[] tripIds);

    /**
     * 删除行程信息
     *
     * @param tripId 行程主键
     * @return 结果
     */
    public int deleteSc2TripsByTripId(Long tripId);

    /**
     * 无参 查询所有行程id
     * @return
     */
    public List<Long> getAllTripIds();

    /**
     * 行程 重名校验
     * @param tripName
     * @return
     */
    public boolean isUniqueTripName(String tripName);

    /**
     * 给定trip_name获取指定用户的trip_id
     * @param tripName
     * @return
     */
    public Long getTripIdByTripName(String tripName);

    /**
     * 获取当前登录用户的行程信息
     * @return
     */
    List<String> getMyTrips();
}
