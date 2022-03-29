package ren.nearby.share

import android.app.Application
import android.content.res.Configuration
import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.FormatStrategy
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.sankuai.erp.component.appinit.api.AppInitManager
import com.sankuai.erp.component.appinit.common.AppInitCallback
import com.sankuai.erp.component.appinit.common.AppInitItem
import com.sankuai.erp.component.appinit.common.ChildInitTable
import com.sankuai.erp.component.appinit.api.AppInitApiUtils
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * @author:
 * @created on: 2022/3/10 14:26
 * @description:
 */
class ShareApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化koin
        if (BuildConfig.DEBUG) {// 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog()// 打印日志
            ARouter.openDebug()// 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this)// 尽可能早，推荐在Application中初始化
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
        val formatStrategy: FormatStrategy = PrettyFormatStrategy
                .newBuilder()
                .showThreadInfo(true) //（可选）是否显示线程信息。 默认值为true
                //                .methodCount(5)// （可选）要显示的方法行数。 默认2
                //                .methodOffset(7) // （可选）设置调用堆栈的函数偏移值，0的话则从打印该Log的函数开始输出堆栈信息，默认是0
                //                .logStrategy() //（可选）更改要打印的日志策略。 默认LogCat
                .tag("Share") //（可选）每个日志的全局标记。
                .build()
        Logger.addLogAdapter(object : AndroidLogAdapter(formatStrategy) {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
        AppInitManager.get().init(this, object : AppInitCallback {
            override fun onInitStart(isMainProcess: Boolean, processName: String?) {
                Logger.d("AppInitManager onInitStart , isMainProcess =  ${isMainProcess} , processName = ${processName}")
            }

            override fun isDebug(): Boolean {
                Logger.d("AppInitManager isDebug , ${BuildConfig.DEBUG}")
                return BuildConfig.DEBUG
            }

            override fun getCoordinateAheadOfMap(): MutableMap<String, String>? {
                Logger.d("AppInitManager getCoordinateAheadOfMap ,  $ ")
                return null;
            }

            override fun onInitFinished(isMainProcess: Boolean, processName: String?, childInitTableList: MutableList<ChildInitTable>?, appInitItemList: MutableList<AppInitItem>?) {
                val initLogInfo =
                    AppInitApiUtils.getInitOrderAndTimeLog(childInitTableList, appInitItemList)
                Logger.d("AppInitManager initLogInfo ,  ${initLogInfo} ")
            }

        })
    }
    override fun onTerminate() {
        super.onTerminate()
        Logger.d("AppInitManager onTerminate ,  ")
        AppInitManager.get().onTerminate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Logger.d("AppInitManager onConfigurationChanged ,  ${newConfig} ")
        AppInitManager.get().onConfigurationChanged(newConfig);
    }

    override fun onLowMemory() {
        super.onLowMemory()
        Logger.d("AppInitManager onLowMemory , ")
        AppInitManager.get().onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        Logger.d("AppInitManager onTrimMemory ,  ${level} ")
        AppInitManager.get().onTrimMemory(level)
    }
}