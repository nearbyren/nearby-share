package ren.nearby.share_module

import com.alibaba.android.arouter.facade.annotation.Route
import ren.nearby.common_module.BaseActivityKot
import ren.nearby.share_export.router.ShareRouterTable
import kotlinx.android.synthetic.main.share_recommend_activity.*

/**
 * @author:
 * @created on: 2022/3/12 10:55
 * @description:
 */
@Route(path = ShareRouterTable.PATH_RECOMMEND)
class RecommendAct : BaseActivityKot() {

    override fun getLayoutResKot(): Int {
        return R.layout.share_recommend_activity
    }

    override fun initView() {
        super.initView()
        share_tv.text = "我是分享module"
        share_btn.setOnClickListener {
        }

    }
}