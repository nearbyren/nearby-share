package ren.nearby.share_module

import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import kotlinx.android.synthetic.main.share_recommend_activity.*
import ren.nearby.common_module.BaseActivityKot
import ren.nearby.share_export.router.ShareRouterTable

/**
 * @author:
 * @created on: 2022/3/12 10:55
 * @description:
 */
@Route(path = ShareRouterTable.PATH_RECOMMEND)
class RecommendAct : BaseActivityKot() {

    @Autowired(name = "key1")
    lateinit var key1: String

    @Autowired(name = "key2")
    lateinit var key2: String
    override fun getLayoutResKot(): Int {
        return R.layout.share_recommend_activity
    }

    override fun initView() {
        super.initView()
        share_tv.text = "我是分享module key = ${key1} - ${key2}"
        share_btn.setOnClickListener {

        }

    }
}