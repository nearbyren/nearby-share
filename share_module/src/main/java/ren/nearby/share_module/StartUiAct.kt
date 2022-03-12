package ren.nearby.share_module

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.share_start_activity.*
import ren.nearby.share_export.ShareServiceUtil


/**
 * @author:
 * @created on: 2022/2/24 14:19
 * @description:
 */
class StartUiAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_start_activity)
        share_button1.setOnClickListener {
            Log.d("share", "Share flag = ${ShareServiceUtil.getService()!!.shareBoolean()}")

        }
        share_button2.setOnClickListener {
            ShareServiceUtil.startRecommendPage()
        }
    }
}