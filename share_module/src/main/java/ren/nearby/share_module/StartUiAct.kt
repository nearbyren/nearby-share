package ren.nearby.share_module

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.share_start_activity.*
import ren.nearby.share_export.ShareServiceUtil
import ren.nearby.share_module.course.VideoPlayerActivity


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
            val flag = ShareServiceUtil.getService()?.apply {
                shareBoolean()
            } ?: false
            Logger.d("share", "Share flag = ${flag}")
        }
        share_button2.setOnClickListener {
//            ShareServiceUtil.startRecommendPage()
            startActivity(Intent(ShareActivity@ this, VideoPlayerActivity::class.java))
        }
    }
}