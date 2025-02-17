package com.sc2.sc2.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.sc2.common.utils.SecurityUtils;
import com.sc2.sc2.domain.Sc2ActsForDisplay;
import com.sc2.sc2.service.ISc2TripsService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.sc2.common.annotation.Log;
import com.sc2.common.core.controller.BaseController;
import com.sc2.common.core.domain.AjaxResult;
import com.sc2.common.enums.BusinessType;
import com.sc2.sc2.domain.Sc2Acts;
import com.sc2.sc2.service.ISc2ActsService;
import com.sc2.common.utils.poi.ExcelUtil;
import com.sc2.common.core.page.TableDataInfo;

/**
 * 移动Controller
 * 
 * @author dojncp
 * @date 2025-02-11
 */
@RestController
@RequestMapping("/sc2/acts")
public class Sc2ActsController extends BaseController
{
    @Autowired
    private ISc2ActsService sc2ActsService;

    @Autowired
    private ISc2TripsService sc2TripsService;

    /**
     * 查询移动列表
     */
    @PreAuthorize("@ss.hasPermi('sc2:acts:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sc2Acts sc2Acts)
    {
        startPage();
        List<Sc2Acts> list = sc2ActsService.selectSc2ActsList(sc2Acts);
        List<Sc2ActsForDisplay> ld = new ArrayList<>();
        for (Sc2Acts a : list) {
            Sc2ActsForDisplay sd = new Sc2ActsForDisplay();
            sd.setActId(a.getActId());
            sd.setTripId(a.getTripId());
            sd.setActName(a.getActName());
            sd.setActOrder(a.getActOrder());
            sd.setStartPoint(a.getStartPoint());
            sd.setEndPoint(a.getEndPoint());
            sd.setActType(a.getActType());
            sd.setActTran(a.getActTran());
            sd.setActDepTime(a.getActDepTime());
            sd.setActArrTime(a.getActArrTime());
            sd.setMinuteLastInterval(a.getMinuteLastInterval());
            sd.setActCost(a.getActCost());
            sd.setActCostType(a.getActCostType());
            sd.setActCostType(a.getActCostType());
            sd.setActDisKm(a.getActDisKm());
            sd.setActSpeed(a.getActSpeed());
            sd.setTripName(sc2TripsService.selectSc2TripsByTripId(a.getTripId()).getTripName());
            sd.setCreateBy(a.getCreateBy());
            sd.setCreateTime(a.getCreateTime());
            sd.setUpdateBy(a.getUpdateBy());
            sd.setUpdateTime(a.getUpdateTime());
            ld.add(sd);
        }
//        return getDataTable(list);
        return getDataTable(ld);
    }

    /**
     * 导出移动列表
     */
    @PreAuthorize("@ss.hasPermi('sc2:acts:export')")
    @Log(title = "移动", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sc2Acts sc2Acts)
    {
        List<Sc2Acts> list = sc2ActsService.selectSc2ActsList(sc2Acts);
        ExcelUtil<Sc2Acts> util = new ExcelUtil<Sc2Acts>(Sc2Acts.class);
        util.exportExcel(response, list, "移动数据");
    }

    /**
     * 获取移动详细信息
     */
    @PreAuthorize("@ss.hasPermi('sc2:acts:query')")
    @GetMapping(value = "/{actId}")
    public AjaxResult getInfo(@PathVariable("actId") Long actId)
    {
        Sc2Acts a = sc2ActsService.selectSc2ActsByActId(actId);
        // 转移
        Sc2ActsForDisplay sd = new Sc2ActsForDisplay();
        sd.setActId(a.getActId());
        sd.setTripId(a.getTripId());
        sd.setActName(a.getActName());
        sd.setActOrder(a.getActOrder());
        sd.setStartPoint(a.getStartPoint());
        sd.setEndPoint(a.getEndPoint());
        sd.setActType(a.getActType());
        sd.setActTran(a.getActTran());
        sd.setActDepTime(a.getActDepTime());
        sd.setActArrTime(a.getActArrTime());
        sd.setMinuteLastInterval(a.getMinuteLastInterval());
        sd.setActCost(a.getActCost());
        sd.setActCostType(a.getActCostType());
        sd.setActCostType(a.getActCostType());
        sd.setActDisKm(a.getActDisKm());
        sd.setActSpeed(a.getActSpeed());
        sd.setTripName(sc2TripsService.selectSc2TripsByTripId(a.getTripId()).getTripName());
        sd.setCreateBy(a.getCreateBy());
        sd.setCreateTime(a.getCreateTime());
        sd.setUpdateBy(a.getUpdateBy());
        sd.setUpdateTime(a.getUpdateTime());
        // 返回新的值
        // return success(sc2ActsService.selectSc2ActsByActId(actId));
        return success(sd);
    }

    /**
     * 新增移动
     */
    @PreAuthorize("@ss.hasPermi('sc2:acts:add')")
    @Log(title = "移动", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sc2ActsForDisplay s)
    {
        Long userId = SecurityUtils.getUserId();
        Sc2Acts sc2Acts = new Sc2Acts();
        Long tripId = sc2TripsService.getTripIdByTripName(s.getTripName());
        // 恢复sc2Acts的实例
        sc2Acts.setActId(s.getActId());
        sc2Acts.setTripId(tripId); // 刚刚获取到的trip_id
        sc2Acts.setActName(s.getActName());
        sc2Acts.setActOrder(s.getActOrder());
        sc2Acts.setStartPoint(s.getStartPoint());
        sc2Acts.setEndPoint(s.getEndPoint());
        sc2Acts.setActType(s.getActType());
        sc2Acts.setActTran(s.getActTran());
        sc2Acts.setActDepTime(s.getActDepTime());
        sc2Acts.setActArrTime(s.getActArrTime());
        sc2Acts.setMinuteLastInterval(s.getMinuteLastInterval());
        sc2Acts.setActCost(s.getActCost());
        sc2Acts.setActCostType(s.getActCostType());
        sc2Acts.setActDisKm(s.getActDisKm());
        sc2Acts.setActSpeed(s.getActSpeed());
        sc2Acts.setUpdateBy(String.valueOf(userId));
        sc2Acts.setUpdateTime(new Date());
        // 新增时，校验序号重号与否
        boolean isUniqueActOrder = sc2ActsService.isUniqueActOrder(sc2Acts.getTripId(), sc2Acts.getActOrder());
        if (isUniqueActOrder) {
            // 返回
            return toAjax(sc2ActsService.insertSc2Acts(sc2Acts));
        } else {
            return error("您输入的移动序号已存在，请重新输入！");
        }
    }

    /**
     * 修改移动
     */
    @PreAuthorize("@ss.hasPermi('sc2:acts:edit')")
    @Log(title = "移动", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sc2ActsForDisplay s)
    {
        Long userId = SecurityUtils.getUserId();
        Sc2Acts sc2Acts = new Sc2Acts();
        Long tripId = sc2TripsService.getTripIdByTripName(s.getTripName());
        // 恢复sc2Acts的实例
        sc2Acts.setActId(s.getActId());
        sc2Acts.setTripId(tripId); // 刚刚获取到的trip_id
        sc2Acts.setActName(s.getActName());
        sc2Acts.setActOrder(s.getActOrder());
        sc2Acts.setStartPoint(s.getStartPoint());
        sc2Acts.setEndPoint(s.getEndPoint());
        sc2Acts.setActType(s.getActType());
        sc2Acts.setActTran(s.getActTran());
        sc2Acts.setActDepTime(s.getActDepTime());
        sc2Acts.setActArrTime(s.getActArrTime());
        sc2Acts.setMinuteLastInterval(s.getMinuteLastInterval());
        sc2Acts.setActCost(s.getActCost());
        sc2Acts.setActCostType(s.getActCostType());
        sc2Acts.setActDisKm(s.getActDisKm());
        sc2Acts.setActSpeed(s.getActSpeed());
        sc2Acts.setUpdateBy(String.valueOf(userId));
        sc2Acts.setUpdateTime(new Date());
        // 恢复实例
        return toAjax(sc2ActsService.updateSc2Acts(sc2Acts));
    }

    /**
     * 删除移动
     */
    @PreAuthorize("@ss.hasPermi('sc2:acts:remove')")
    @Log(title = "移动", businessType = BusinessType.DELETE)
	@DeleteMapping("/{actIds}")
    public AjaxResult remove(@PathVariable Long[] actIds)
    {
        return toAjax(sc2ActsService.deleteSc2ActsByActIds(actIds));
    }


}
