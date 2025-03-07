package com.sc2.sc2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.sc2.common.utils.SecurityUtils;
import com.sc2.sc2.domain.Sc2Trips;
import com.sc2.sc2.mapper.Sc2TripsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sc2.sc2.mapper.Sc2ActsMapper;
import com.sc2.sc2.domain.Sc2Acts;
import com.sc2.sc2.service.ISc2ActsService;

/**
 * 移动Service业务层处理
 *
 * @author dojncp
 * @date 2025-02-11
 */
@Service
public class Sc2ActsServiceImpl extends ServiceImpl<Sc2ActsMapper, Sc2Acts> implements ISc2ActsService {
    @Autowired
    private Sc2ActsMapper sc2ActsMapper;

    @Autowired
    private Sc2TripsMapper sc2TripsMapper;

    /**
     * 查询移动
     *
     * @param actId 移动主键
     * @return 移动
     */
    @Override
    public Sc2Acts selectSc2ActsByActId(Long actId) {
        return sc2ActsMapper.selectSc2ActsByActId(actId);
    }

    /**
     * 查询移动列表
     *
     * @param sc2Acts 移动
     * @return 移动
     */
    @Override
    public List<Sc2Acts> selectSc2ActsList(Sc2Acts sc2Acts) {
        Long userId = SecurityUtils.getUserId(); // 当前用户ID
        // 仅显示当前用户对应的移动（admin除外）
        return sc2ActsMapper.selectSc2ActsList(sc2Acts, userId);
    }

    /**
     * 新增移动
     *
     * @param sc2Acts 移动
     * @return 结果
     */
    @Override
    public int insertSc2Acts(Sc2Acts sc2Acts) {
        sc2Acts.setActSpeed(this.calculateSpeed(sc2Acts));
        sc2Acts.setMinuteLastInterval(this.calculateInterval(sc2Acts));
        this.calculateNextInterval(sc2Acts);
        return sc2ActsMapper.insertSc2Acts(sc2Acts);
    }

    /**
     * 修改移动
     *
     * @param sc2Acts 移动
     * @return 结果
     */
    @Override
    public int updateSc2Acts(Sc2Acts sc2Acts) {
        sc2Acts.setActSpeed(this.calculateSpeed(sc2Acts));
        sc2Acts.setMinuteLastInterval(this.calculateInterval(sc2Acts));
        this.calculateNextInterval(sc2Acts);
        // 更新本条的下一条数据的时候，由于更新方法内同时带有更新下一条的方法，所以会嵌套更新后续所有的移动记录
        return sc2ActsMapper.updateSc2Acts(sc2Acts);
    }

    /**
     * 批量删除移动
     *
     * @param actIds 需要删除的移动主键
     * @return 结果
     */
    @Override
    public int deleteSc2ActsByActIds(Long[] actIds) {
        return sc2ActsMapper.deleteSc2ActsByActIds(actIds);
    }

    /**
     * 删除移动信息
     *
     * @param actId 移动主键
     * @return 结果
     */
    @Override
    public int deleteSc2ActsByActId(Long actId) {
        // 先更新下一条记录的等候时间
        Sc2Acts sc2Acts = sc2ActsMapper.selectSc2ActsByActId(actId);
        this.calculateNextInterval(sc2Acts);
        return sc2ActsMapper.deleteSc2ActsByActId(actId);
    }

    /**
     * 传入一个Sc2Acts实例，计算一次移动与上次移动间的等候时间
     * @param sc2Acts
     * @return
     */
    public int calculateWaitMinutes(Sc2Acts sc2Acts) {
        return 0;
    }

    /**
     * 传入一个Sc2Acts实例，计算一次移动的速度
     * @param sc2Acts
     * @return
     */
    public Double calculateSpeed(Sc2Acts sc2Acts) {
        Date arr = sc2Acts.getActArrTime();
        Date dep = sc2Acts.getActDepTime();
        Double dis = sc2Acts.getActDisKm();
        // 仅在可计算时计算，否则返回null。
        // 只要有一个是null，就不计算。
        if (arr == null | dep == null | dis == null) {
            return null;
        } else {
            Long minutes = Duration.between(dep.toInstant(), arr.toInstant()).toMinutes();
            if (minutes > 0L) { // 仅在有时间差时计算，保留两位小数
                Double diff =  Math.round(dis/(minutes/60.0) * 100.0)/100.0;
                return diff;
            } else {
                return null;
            }
        }
    }

    /**
     * 计算本条移动与上条移动（以序号为准）间的时间差（分钟），如果有则计算
     * @param sc2Acts
     * @return
     */
    public Long calculateInterval(Sc2Acts sc2Acts) { // 用封装类，否则无法返回null
        Long actOrder = sc2Acts.getActOrder(); // 本条移动的自定义序号
        if (actOrder < 1L) { // actOrder必须大于等于1，如果小于1则非法，不予计算
            return null;
        }
        Long tripId = sc2Acts.getTripId(); // 本条移动所属的行程编号
        Long lastActOrder = actOrder - 1L;
        LambdaQueryWrapper<Sc2Acts> sa = new LambdaQueryWrapper<>();
        sa.eq(Sc2Acts::getTripId, tripId)
                .eq(Sc2Acts::getActOrder, lastActOrder);
        // 如果sa的查询结果为空，则表明不存在“上一条记录”，直接返回null
        if (this.count(sa) == 0) {
            return null;
        }
        // 如果sa的查询结果不为空，表明存在“上一条记录”
        sa.select(Sc2Acts::getActArrTime);
        Sc2Acts lastAct = sc2ActsMapper.selectOne(sa);
        Date lastActArrTime = (lastAct != null) ? lastAct.getActArrTime(): null;
        if (lastActArrTime == null) {
            return null;
        }
        // 本条记录的出发时间
        Date depTime = sc2Acts.getActDepTime();
        if (depTime == null) {
            return null;
        }
        // 计算差值并返回
        Long minutesDiff = Duration.between(lastActArrTime.toInstant(), depTime.toInstant()).toMinutes();
        return minutesDiff;
    }

    /**
     * 计算本条移动与下条移动（以序号为准）间的时间差（分钟），如果有则计算并更新下条记录的等候时间
     * @param sc2Acts
     * @return
     */
    public void calculateNextInterval(Sc2Acts sc2Acts) { // 用封装类，否则无法返回null
        Long actOrder = sc2Acts.getActOrder(); // 本条移动的自定义序号
        if (actOrder >= 1L) { // actOrder必须大于等于1，如果小于1则非法，不予计算
            Long tripId = sc2Acts.getTripId(); // 本条移动所属的行程编号
            Long nextActOrder = actOrder + 1L;
            LambdaQueryWrapper<Sc2Acts> sa = new LambdaQueryWrapper<>();
            sa.eq(Sc2Acts::getTripId, tripId)
                    .eq(Sc2Acts::getActOrder, nextActOrder);
            // 如果sa的查询结果为空，则表明不存在“下一条记录”，直接返回null
            if (this.count(sa) > 0) {
                // 如果sa的查询结果不为空，表明存在“下一条记录”
                // sa.select(Sc2Acts::getActArrTime); // 不能只查询一列，否则接下来找不到数据
                Sc2Acts nextAct = sc2ActsMapper.selectOne(sa);
                Date nextActDepTime = (nextAct != null) ? nextAct.getActDepTime(): null;
                if (nextActDepTime != null) {
                    // 本条记录的到达时间
                    Date arrTime = sc2Acts.getActArrTime();
                    if (arrTime != null) {
                        // 计算差值并设置
                        Long minutesDiff = Duration.between(arrTime.toInstant(), nextActDepTime.toInstant()).toMinutes();
                        nextAct.setMinuteLastInterval(minutesDiff);
                        sc2ActsMapper.updateSc2Acts(nextAct); // 把更新写入数据库
                    }
                }
            }
        }
    }

    /**
     * 同一行程下的序号 重号校验
     * @param tripId
     * @param actOrder
     * @return
     */
    @Override
    public boolean isUniqueActOrder(Long tripId, Long actOrder) {
        LambdaQueryWrapper<Sc2Acts> sa = new LambdaQueryWrapper<>();
        sa.eq(Sc2Acts::getTripId, tripId)
                .eq(Sc2Acts::getActOrder, actOrder);
        if (this.count(sa) > 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取某个tripId对应的act
     * @param tripName
     * @return
     */
    @Override
    public List<String> getActsOfTheTrip(String tripName) {
        LambdaQueryWrapper<Sc2Trips> st = new LambdaQueryWrapper<>();
        st.eq(Sc2Trips::getTripName, tripName)
                .select(Sc2Trips::getTripId);
        // 获取对应tripid
        List<Long> tripId = sc2TripsMapper.selectList(st).stream().map(Sc2Trips::getTripId).collect(Collectors.toList());
        // 获取actName
        LambdaQueryWrapper<Sc2Acts> sa = new LambdaQueryWrapper<>();
        sa.in(Sc2Acts::getTripId, tripId)
                .select(Sc2Acts::getActName);
        List<String> actsOfTheTrip = sc2ActsMapper.selectList(sa).stream().map(Sc2Acts::getActName).collect(Collectors.toList());
        return actsOfTheTrip;
    }
}
