package com.sc2.sc2.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.sc2.sc2.domain.Sc2Trips;
import com.sc2.sc2.service.ISc2TripsService;
import com.sc2.common.utils.poi.ExcelUtil;
import com.sc2.common.core.page.TableDataInfo;

/**
 * 行程Controller
 * 
 * @author dojncp
 * @date 2025-02-10
 */
@RestController
@RequestMapping("/sc2/trips")
public class Sc2TripsController extends BaseController
{
    @Autowired
    private ISc2TripsService sc2TripsService;

    /**
     * 查询行程列表
     */
    @PreAuthorize("@ss.hasPermi('sc2:trips:list')")
    @GetMapping("/list")
    public TableDataInfo list(Sc2Trips sc2Trips)
    {
        startPage();
        List<Sc2Trips> list = sc2TripsService.selectSc2TripsList(sc2Trips);
        return getDataTable(list);
    }

    /**
     * 导出行程列表
     */
    @PreAuthorize("@ss.hasPermi('sc2:trips:export')")
    @Log(title = "行程", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Sc2Trips sc2Trips)
    {
        List<Sc2Trips> list = sc2TripsService.selectSc2TripsList(sc2Trips);
        ExcelUtil<Sc2Trips> util = new ExcelUtil<Sc2Trips>(Sc2Trips.class);
        util.exportExcel(response, list, "行程数据");
    }

    /**
     * 获取行程详细信息
     */
    @PreAuthorize("@ss.hasPermi('sc2:trips:query')")
    @GetMapping(value = "/{tripId}")
    public AjaxResult getInfo(@PathVariable("tripId") Long tripId)
    {
        return success(sc2TripsService.selectSc2TripsByTripId(tripId));
    }

    /**
     * 新增行程
     */
    @PreAuthorize("@ss.hasPermi('sc2:trips:add')")
    @Log(title = "行程", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Sc2Trips sc2Trips)
    {
        // 校验本人名下是否有同名行程，有则不通过
        if (sc2TripsService.isUniqueTripName(sc2Trips.getTripName())) {
            return toAjax(sc2TripsService.insertSc2Trips(sc2Trips));
        } else {
            return error("您已创建过名为 " +sc2Trips.getTripName()+ " 的行程，请重新输入行程名称！");
        }
    }

    /**
     * 修改行程
     */
    @PreAuthorize("@ss.hasPermi('sc2:trips:edit')")
    @Log(title = "行程", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Sc2Trips sc2Trips)
    {
        // 修改行程，不校验名称独立性
        return toAjax(sc2TripsService.updateSc2Trips(sc2Trips));
    }

    /**
     * 删除行程
     */
    @PreAuthorize("@ss.hasPermi('sc2:trips:remove')")
    @Log(title = "行程", businessType = BusinessType.DELETE)
	@DeleteMapping("/{tripIds}")
    public AjaxResult remove(@PathVariable Long[] tripIds)
    {
        return toAjax(sc2TripsService.deleteSc2TripsByTripIds(tripIds));
    }
}
