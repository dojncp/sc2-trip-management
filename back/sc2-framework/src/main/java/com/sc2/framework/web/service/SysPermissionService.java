package com.sc2.framework.web.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sc2.sc2.domain.Sc2Trips;
import com.sc2.sc2.service.ISc2TripsService;
import com.sc2.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import com.sc2.common.constant.UserConstants;
import com.sc2.common.core.domain.entity.SysRole;
import com.sc2.common.core.domain.entity.SysUser;
import com.sc2.common.utils.StringUtils;
import com.sc2.system.service.ISysMenuService;
import com.sc2.system.service.ISysRoleService;

/**
 * 用户权限处理
 * 
 * @author sc2
 */
@Component
public class SysPermissionService
{
    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private ISc2TripsService sc2TripsService;

    @Autowired
    private ISysUserService sysUserService;

    /**
     * 获取角色数据权限
     * 
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<String> getRolePermission(SysUser user)
    {
        Set<String> roles = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            roles.add("admin");
        }
        else
        {
            roles.addAll(roleService.selectRolePermissionByUserId(user.getUserId()));
        }
        return roles;
    }

    /**
     * 获取菜单数据权限
     * 
     * @param user 用户信息
     * @return 菜单权限信息
     */
    public Set<String> getMenuPermission(SysUser user)
    {
        Set<String> perms = new HashSet<String>();
        // 管理员拥有所有权限
        if (user.isAdmin())
        {
            perms.add("*:*:*");
        }
        else
        {
            List<SysRole> roles = user.getRoles();
            if (!CollectionUtils.isEmpty(roles))
            {
                // 多角色设置permissions属性，以便数据权限匹配权限
                for (SysRole role : roles)
                {
                    if (StringUtils.equals(role.getStatus(), UserConstants.ROLE_NORMAL))
                    {
                        Set<String> rolePerms = menuService.selectMenuPermsByRoleId(role.getRoleId());
                        role.setPermissions(rolePerms);
                        perms.addAll(rolePerms);
                    }
                }
            }
            else
            {
                perms.addAll(menuService.selectMenuPermsByUserId(user.getUserId()));
            }
        }
        return perms;
    }

    /**
     * 获取用户拥有的行程
     *
     * @param user 用户信息
     * @return 角色权限信息
     */
    public Set<Long> getTripsIdByUser(SysUser user)
    {
        Set<Long> userTripsId;
        // 管理员可查看所有的行程
        if (user.isAdmin())
        {
            // 查询所有行程信息，返回所有行程id
            List<Long> allTripsIdList = sc2TripsService.getAllTripIds();
            userTripsId = allTripsIdList.stream().collect(Collectors.toSet());
        }
        else
        {
            // 在sc2_user_trip表中查询user_id对应的trip_id
            List<Long> userTripsIdList = sysUserService.selectTripsIdByUserId(user.getUserId());
            userTripsId = userTripsIdList.stream().collect(Collectors.toSet());
        }
        return userTripsId;
    }

}
