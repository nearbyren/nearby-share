package ren.nearby.share_module

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.orhanobut.logger.Logger
import ren.nearby.share_export.router.ShareRouterTable
import ren.nearby.share_export.service.IShareService

/**
 * @author:
 * @created on: 2022/2/24 14:19
 * @description:
 */
@Route(path = ShareRouterTable.path_service)
class ShareServiceImpl : IShareService() {

    override fun shareBoolean(): Boolean {
        Logger.d("share share_module shareBoolean ")
        return super.shareBoolean()
    }
    override fun init(context: Context?) {
        //初始化工作，服务注入时会调用
        Logger.d("share share_module init ")
    }
}