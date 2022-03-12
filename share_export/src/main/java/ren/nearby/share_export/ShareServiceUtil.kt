package ren.nearby.share_export

import com.alibaba.android.arouter.launcher.ARouter
import com.orhanobut.logger.Logger
import ren.nearby.share_export.router.ShareRouterTable
import ren.nearby.share_export.service.IShareService

/**
 * @author:
 * @created on: 2022/2/24 14:13
 * @description:
 */
open class ShareServiceUtil {

    companion object {


        fun startRecommendPage() {
            ARouter.getInstance().build(ShareRouterTable.PATH_RECOMMEND)
                    .withString("key1", "哈哈1")
                    .withString("key2", "哈哈2").navigation()
        }


        fun navigateShareWx() {
            Logger.d("share share_export navigateShareWx")
        }

        fun getService(): IShareService? {
            Logger.d("share share_export IShareService")

            var service = ARouter.getInstance().build(ShareRouterTable.PATH_SERVICE).navigation()
            if (service is IShareService)
                return service
            return null
        }

        fun shareWxResult(): Boolean {

            Logger.d("share share_export shareWxResult")

            return getService()!!.shareBoolean()
        }
    }
}