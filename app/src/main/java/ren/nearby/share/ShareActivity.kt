package ren.nearby.share

import android.os.Bundle
import android.system.Os
import androidx.appcompat.app.AppCompatActivity
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.share_activity_main.*
import java.util.*

class ShareActivity : AppCompatActivity() {
    var flg: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_activity_main)
//        startActivity(Intent(ShareActivity@ this, StartUiAct::class.java))
        start.setOnClickListener {
            myThread()
            TRunnable()
            TTimer()

        }
        stop.setOnClickListener {
            flg = false
            task?.cancel()
        }

    }

    fun myThread() {
        Thread {
            while (flg) {
                Logger.d("哈哈哈 Thread  Process id:  ${Os.getpid()} Parent_ID: ${Os.getppid()} Thread ID: ${Os.gettid()}")
                Thread.sleep(10)
            }
        }.start()
    }

    fun TRunnable() {
        Runnable {
            Logger.d("哈哈哈 Runnable  Process id:  ${Os.getpid()} Parent_ID: ${Os.getppid()} Thread ID: ${Os.gettid()}")
        }.run()
    }

    var task: TimerTask? = null
    fun TTimer() {
        val timer = Timer()
        task = object : TimerTask() {
            override fun run() {
                Logger.d("哈哈哈 TTimer myTimer: id:  ${Os.getpid()} Parent_ID: ${Os.getppid()} Thread ID: ${Os.gettid()}")
            }
        }
        timer.schedule(task, 10, 10)
    }
}