package com.sc2.sc2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import java.util.stream.Collectors;

import com.sc2.common.exception.ServiceException;
import com.sc2.common.utils.DateUtils;
import com.sc2.sc2.domain.Sc2Acts;
import com.sc2.sc2.domain.Sc2UserTrip;
import com.sc2.sc2.mapper.Sc2ActsMapper;
import com.sc2.sc2.mapper.Sc2UserTripMapper;
import com.sc2.sc2.service.ISc2ActsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import com.sc2.sc2.mapper.Sc2TripsMapper;
import com.sc2.sc2.domain.Sc2Trips;
import com.sc2.sc2.service.ISc2TripsService;

import com.sc2.common.utils.SecurityUtils;

import static com.sc2.common.core.domain.AjaxResult.error;

/**
 * 行程Service业务层处理
 *
 * @author dojncp
 * @date 2025-02-10
 */
@Service
public class Sc2TripsServiceImpl extends ServiceImpl<Sc2TripsMapper, Sc2Trips> implements ISc2TripsService {
    @Autowired
    private Sc2TripsMapper sc2TripsMapper;

    @Autowired
    private Sc2UserTripMapper sc2UserTripMapper;

    @Autowired
    private ISc2ActsService sc2ActsService;

    /**
     * 查询行程
     *
     * @param tripId 行程主键
     * @return 行程
     */
    @Override
    public Sc2Trips selectSc2TripsByTripId(Long tripId) {
        return sc2TripsMapper.selectSc2TripsByTripId(tripId);
    }

    /**
     * 查询行程列表
     *
     * @param sc2Trips 行程
     * @return 行程
     */
    @Override
    public List<Sc2Trips> selectSc2TripsList(Sc2Trips sc2Trips) {
        Long userId = SecurityUtils.getUserId(); // 当前用户ID
        return sc2TripsMapper.selectSc2TripsList(sc2Trips, userId);
    }

    /**
     * 新增行程
     *
     * @param sc2Trips 行程
     * @return 结果
     */
    @Override
    public int insertSc2Trips(Sc2Trips sc2Trips) {
        Long userId = SecurityUtils.getUserId(); // 当前用户ID
        sc2Trips.setCreateBy(userId.toString());
        sc2Trips.setCreateTime(DateUtils.getNowDate());
        int affectedRows = sc2TripsMapper.insertSc2Trips(sc2Trips);
        Long tripId = sc2Trips.getTripId();
        // 实例化一个sc2_user_trip
        Sc2UserTrip sc2UserTrip = new Sc2UserTrip();
        sc2UserTrip.setUserId(userId);
        sc2UserTrip.setTripId(tripId);
        // 将关系记录插入表中
        sc2UserTripMapper.insert(sc2UserTrip);
        // 返回发生改变的记录条数
        return affectedRows;
    }

    /**
     * 修改行程
     *
     * @param sc2Trips 行程
     * @return 结果
     */
    @Override
    public int updateSc2Trips(Sc2Trips sc2Trips) {
        Long userId = SecurityUtils.getUserId(); // 当前用户ID
        sc2Trips.setUpdateBy(userId.toString());
        sc2Trips.setUpdateTime(DateUtils.getNowDate());
        return sc2TripsMapper.updateSc2Trips(sc2Trips);
    }

    /**
     * 批量删除行程
     *
     * @param tripIds 需要删除的行程主键
     * @return 结果
     */
    @Override
    public int deleteSc2TripsByTripIds(Long[] tripIds) {
        for (Long tripId : tripIds) {
            Sc2Trips trip = selectSc2TripsByTripId(tripId);
            if (countNumberOfActsOfTrip(tripId) > 0) {
                throw new ServiceException("名为 " + trip.getTripName() + " 的行程名下已有移动记录，不能删除！");
            }
        }
        int affectedRows = sc2TripsMapper.deleteSc2TripsByTripIds(tripIds);
        // 删除sc2_user_trip表中相关记录
        LambdaQueryWrapper<Sc2UserTrip> lqw = new LambdaQueryWrapper<>();
        lqw.in(Sc2UserTrip::getTripId, tripIds);
        sc2UserTripMapper.delete(lqw);
        // 返回受影响行数
        return affectedRows;
    }

    /**
     * 删除行程信息
     *
     * @param tripId 行程主键
     * @return 结果
     */
    @Override
    public int deleteSc2TripsByTripId(Long tripId) {
        int affectedRows = sc2TripsMapper.deleteSc2TripsByTripId(tripId);
        // 删除sc2_user_trip表中相关记录
        LambdaQueryWrapper<Sc2UserTrip> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Sc2UserTrip::getTripId, tripId);
        sc2UserTripMapper.delete(lqw);
        // 返回受影响行数
        return affectedRows;
    }

    /**
     * 无参 查询所有行程id
     * @return
     */
    @Override
    public List<Long> getAllTripIds() {
        List<Sc2Trips> trips = sc2TripsMapper.selectList(null); // 查询所有记录
        return trips.stream().map(Sc2Trips::getTripId).collect(Collectors.toList());
    }

    /**
     * 行程 重名校验
     * @param tripName
     * @return
     */
    @Override
    public boolean isUniqueTripName(String tripName) {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Sc2UserTrip> sut = new LambdaQueryWrapper<>();
        sut.eq(Sc2UserTrip::getUserId, userId)
                .select(Sc2UserTrip::getTripId); // 只查询trip_id
        List<Long> myTripIdList = sc2UserTripMapper.selectObjs(sut)
                .stream().map(id -> (Long) id)
                .collect(Collectors.toList());
        // 本人未有任何行程，直接通过
        if (myTripIdList.isEmpty()) {
            return true;
        }
        // 本人有行程
        LambdaQueryWrapper<Sc2Trips> st = new LambdaQueryWrapper<>();
        // 本人所有trip_id对应的trip_name
        st.in(Sc2Trips::getTripId, myTripIdList)
                .eq(Sc2Trips::getTripName, tripName)
                .select(Sc2Trips::getTripName); // 只查询trip_name
        List<String> myTripNameList = sc2TripsMapper.selectObjs(st)
                .stream().map(name -> (String) name)
                .collect(Collectors.toList());
        boolean existTripName = myTripNameList.contains(tripName);
        if (!existTripName) { // 如果不包含，则说明无重名行程，通过
            return true;
        } else {
            return false;
        }
    }

    /**
     * 给定trip_name获取指定用户的trip_id
     * @param tripName
     * @return
     */
    @Override
    public Long getTripIdByTripName(String tripName) {
        Long userId = SecurityUtils.getUserId();
        LambdaQueryWrapper<Sc2UserTrip> sut = new LambdaQueryWrapper<>();
        sut.eq(Sc2UserTrip::getUserId, userId)
                .select(Sc2UserTrip::getTripId);
        // 只查询当前用户的所有trip_id
        List<Long> myTripIdList = sc2UserTripMapper.selectObjs(sut)
                .stream().map(id -> (Long) id)
                .collect(Collectors.toList());
        // 本人未有任何行程，报错
        if (myTripIdList.isEmpty()) {
            throw new ServiceException("您没有任何行程，请先新增一条行程，再于该行程下添加移动！");
        }
        // 本人有行程，检查trip_id中是否有与传入tripName相应的记录
        LambdaQueryWrapper<Sc2Trips> st = new LambdaQueryWrapper<>();
        st.in(Sc2Trips::getTripId, myTripIdList)
                .eq(Sc2Trips::getTripName, tripName)
                .select(Sc2Trips::getTripId);
        if (this.count(st) > 0) { // 如果查到了相应记录，则该行程名合法
            // 获取相应的trip_id
            List<Long> myTripId = sc2TripsMapper.selectObjs(st)
                    .stream().map(id -> (Long)id)
                    .collect(Collectors.toList());
            Long tripId = myTripId.get(0);
            return tripId;
        } else {
            throw new ServiceException("您没有名为 "+tripName+" 的行程，请检查后重试！");
        }
    }

    /**
     * 计数当前行程下有多少条移动
     * @param tripId
     * @return
     */
    public long countNumberOfActsOfTrip(Long tripId) {
        LambdaQueryWrapper<Sc2Acts> sa = new LambdaQueryWrapper<>();
        sa.eq(Sc2Acts::getTripId, tripId);
        long thisCount = sc2ActsService.count(sa);
        return thisCount;
    }

}
