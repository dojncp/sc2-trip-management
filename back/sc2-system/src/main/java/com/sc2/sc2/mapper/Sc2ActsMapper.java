package com.sc2.sc2.mapper;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sc2.sc2.domain.Sc2Acts;
import org.apache.ibatis.annotations.Param;

/**
 * 移动Mapper接口
 *
 * @author dojncp
 * @date 2025-02-11
 */
public interface Sc2ActsMapper extends BaseMapper<Sc2Acts> {
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
    public List<Sc2Acts> selectSc2ActsList(@Param("sc2Acts") Sc2Acts sc2Acts,
                                           @Param("userId") Long userId);

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
     * 删除移动
     *
     * @param actId 移动主键
     * @return 结果
     */
    public int deleteSc2ActsByActId(Long actId);

    /**
     * 批量删除移动
     *
     * @param actIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSc2ActsByActIds(Long[] actIds);
}
