package ren.nearby.share

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ren.nearby.share_module.StartUiAct

class ShareActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(ShareActivity@ this, StartUiAct::class.java))
    }
}