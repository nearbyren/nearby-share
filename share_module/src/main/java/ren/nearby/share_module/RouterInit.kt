package ren.nearby.share_module

import com.orhanobut.logger.Logger
import com.sankuai.erp.component.appinit.api.SimpleAppInit
import com.sankuai.erp.component.appinit.common.AppInit

/**
 * @author:
 * @created on: 2022/3/29 10:41
 * @description:
 */
@AppInit(priority = 40, description = "初始化")
class RouterInit : SimpleAppInit() {
    override fun onCreate() {
        // SimpleAppInit 中包含了 mApplication 和 mIsDebug 属性，可以直接在子类中使用
        Logger.d("AppInitManager = ")
    }
}