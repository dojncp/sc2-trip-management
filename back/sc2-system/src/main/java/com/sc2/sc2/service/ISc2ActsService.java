package com.sc2.sc2.service;

import java.util.List;
import com.sc2.sc2.domain.Sc2Acts;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 移动Service接口
 *
 * @author dojncp
 * @date 2025-02-11
 */
public interface ISc2ActsService extends IService<Sc2Acts> {
    /**
     * 查询移动
     *
     * @param actId 移动主键
     * @return 移动
     */
    public Sc2Acts selectSc2ActsByActId(Long actId);

    /**
     * 查询移动列表
     *
     * @param sc2Acts 移动
     * @return 移动集合
     */
    public List<Sc2Acts> selectSc2ActsList(Sc2Acts sc2Acts);

    /**
     * 新增移动
     *
     * @param sc2Acts 移动
     * @return 结果
     */
    public int insertSc2Acts(Sc2Acts sc2Acts);

    /**
     * 修改移动
     *
     * @param sc2Acts 移动
     * @return 结果
     */
    public int updateSc2Acts(Sc2Acts sc2Acts);

    /**
     * 批量删除移动
     *
     * @param actIds 需要删除的移动主键集合
     * @return 结果
     */
    public int deleteSc2ActsByActIds(Long[] actIds);

    /**
     * 删除移动信息
     *
     * @param actId 移动主键
     * @return 结果
     */
    public int deleteSc2ActsByActId(Long actId);

    /**
     * 同一行程下的序号 重号校验
     * @param tripId
     * @param actOrder
     * @return
     */
    boolean isUniqueActOrder(Long tripId, Long actOrder);

    /**
     * 获取某个tripId对应的act
     * @param tripName
     * @return
     */
    List<String> getActsOfTheTrip(String tripName);
}
