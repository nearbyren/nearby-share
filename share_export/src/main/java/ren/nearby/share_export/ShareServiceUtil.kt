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
class ShareServiceUtil {

    companion object {
        fun navigateShareWx() {
            Logger.d("share share_export navigateShareWx")
        }

        fun getService(): Any? {
            Logger.d("share share_export getService")
            val service = ARouter.getInstance().build(ShareRouterTable.path_service).navigation()
            return service
        }

        fun gotoShare() {
            Logger.d("share share_export gotoShare")
            var service = getService()
            if (service is IShareService) {
                service.shareBoolean()
            }
        }
    }
}