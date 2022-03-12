package ren.nearby.share_export.service

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider
import com.orhanobut.logger.Logger

/**
 * @author:
 * @created on: 2022/2/24 14:12
 * @description:
 */
interface IShareService : IProvider {

    open fun shareBoolean(): Boolean

    override fun init(context: Context?)
}