package com.y3tu.yao.common.constant;

/**
 * 普通常量
 *
 * @author y3tu
 */
public interface CommonConstant {
    /**
     * 超级管理员用户名
     */
    String ADMIN_USER_NAME = "admin";

    /**
     * 删除
     */
    String STATUS_DEL = "1";

    /**
     * 正常
     */
    String STATUS_NORMAL = "0";

    /**
     * 锁定
     */
    String STATUS_LOCK = "9";

    /**
     * 删除标记
     */
    String DEL_FLAG = "del_flag";

    /**
     * 全部数据权限
     */
    Integer DATA_TYPE_ALL = 0;

    /**
     * 菜单
     */
    String MENU = "0";

    /**
     * 1级菜单
     */
    String PARENT_ID = "0";
    /**
     * 1级菜单
     */
    Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    Integer LEVEL_THREE = 3;

    /**
     * 页面类型权限
     */
    Integer PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    Integer PERMISSION_OPERATION = 1;

    /**
     * 路由信息Redis保存的key
     */
    String ROUTE_KEY = AuthConstant.CLOUD_PREFIX + "ROUTE_LIST";

}
