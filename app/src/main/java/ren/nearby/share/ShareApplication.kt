package ren.nearby.share

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * @author:
 * @created on: 2022/3/10 14:26
 * @description:
 */
class ShareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化koin
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }
}