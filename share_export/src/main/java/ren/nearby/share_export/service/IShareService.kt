package ren.nearby.share_export.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.orhanobut.logger.Logger

/**
 * @author:
 * @created on: 2022/2/24 14:12
 * @description:
 */
open class IShareService : IProvider {

    open fun shareBoolean(): Boolean {
        Logger.d("share share_export shareBoolean")
        return true
    }

    override fun init(context: Context?) {
        Logger.d("share share_export init")
    }
}