package ren.nearby.share_module.course

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.media.AudioManager
import android.net.Uri
import android.view.GestureDetector
import android.view.View
import kotlinx.android.synthetic.main.share_layout_video.*
import ren.nearby.common_module.BaseActivityKot
import ren.nearby.share_module.R
import ren.nearby.share_module.media.MediaController
import ren.nearby.share_module.media.VideoPlayerView
import ren.nearby.share_module.media.callback.VideoBackListener
import tv.danmaku.ijk.media.player.IMediaPlayer

/**
 * @author: ym  作者 E-mail: 15622113269@163.com
 * date: 2018/12/4
 * desc: 自定义视频播放activity
 *
 */
class VideoPlayerActivity : BaseActivityKot(), VideoBackListener {

    private var mStartText = "初始化播放器..."
    private var mLoadingAnim: AnimationDrawable? = null
    private var mLastPosition = 0L

    // 点击纵坐标L
    private var downX = 0f

    // 点击横坐标
    private var downY = 0f

    // 媒体音量管理
    private var audioManager: AudioManager? = null

    // 屏幕当前亮度百分比
    private var currentF = 0f
    private var mUrl: String? = ""
    private var mTitle: String? = ""
    private var mGestureDetector: GestureDetector? = null
    private var mMediaController: MediaController? = null
    private var mMaxVolume: Int = 0

    override fun getLayoutResKot(): Int = R.layout.share_layout_video


    override fun initView() {
        super.initView()
        // 初始化播放器
        initMediaPlayer()
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        mMaxVolume = audioManager!!.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
//        val bundle = intent.getBundleExtra("bundle")
//        bundle?.let {
//            mUrl = bundle.getString("Url")
//            mTitle = bundle.getString("title")
//        }
        loadData2()
    }

    private fun initMediaPlayer() {
        mMediaController = MediaController(this)
        mMediaController?.setTitle("1111")
        share_player_view.setMediaController(mMediaController)
        share_player_view.setMediaBufferingIndicator(share_rl_loading)
        share_player_view.requestFocus()
        share_player_view.setOnInfoListener(onInfoListener)
        share_player_view.setOnSeekCompleteListener(onSeekCompleteListener)
        share_player_view.setOnCompletionListener(onCompletionListener)
        share_player_view.setOnControllerEventsListener(onControllerEventsListener)
        // 设置返回键监听
        mMediaController?.setVideoBackEvent(this)
    }

    /**
     * 初始化加载动画
     */
    private fun initAnimation() {
        rl_start.visibility = View.VISIBLE
        mStartText = "$mStartText【完成】\n解析视频地址...【完成】"
        tv_start.text = mStartText
        mLoadingAnim = iv_video_loading?.background as AnimationDrawable
        mLoadingAnim?.start()
    }

    /**
     * 视频缓冲事件回调
     */
    private val onInfoListener = IMediaPlayer.OnInfoListener { _, what, _ ->
        if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
            share_rl_loading?.visibility = View.GONE
        }
        true
    }

    /**
     * 视频跳转事件回调
     */
    private val onSeekCompleteListener = IMediaPlayer.OnSeekCompleteListener {
    }

    /**
     * 视频播放完成事件回调
     */
    private val onCompletionListener = IMediaPlayer.OnCompletionListener {
        share_player_view.pause()
    }

    /**
     * 控制条控制状态事件回调
     */
    private val onControllerEventsListener = object : VideoPlayerView.OnControllerEventsListener {
        override fun onVideoPause() {
        }

        override fun onVideoResume() {
        }
    }

    override fun onResume() {
        super.onResume()
        if (share_player_view != null && !share_player_view.isPlaying) {
            share_player_view?.seekTo(mLastPosition)
        }
    }

    override fun onPause() {
        super.onPause()
        share_player_view?.let {
            mLastPosition = share_player_view.currentPosition.toLong()
            share_player_view.pause()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        mLoadingAnim?.let {
            mLoadingAnim?.stop()
            mLoadingAnim = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (share_player_view != null && share_player_view.isDrawingCacheEnabled) {
            share_player_view?.destroyDrawingCache()
        }
        mLoadingAnim?.let {
            mLoadingAnim?.stop()
            mLoadingAnim = null
        }
    }

    /**
     * 退出界面回调
     */
    override fun back() {
        onBackPressed()
    }

    fun loadData2() {
        initAnimation()
          var url2= "http://stream.ydstatic.com/course2/uploadVideo/837666292218470400.mp4"
        share_player_view.setVideoURI(Uri.parse(url2))
        share_player_view.setOnPreparedListener {
            mLoadingAnim?.stop()
            mStartText = "$mStartText【完成】\n视频缓冲中..."
            tv_start?.text = mStartText
            share_rl_loading?.visibility = View.GONE
        }
        gone(rl_start)

    }

    fun gone(vararg views: View) {
        if (views.isNotEmpty()) {
            for (view in views) {
                view.visibility = View.GONE
            }
        }
    }
}