package com.example.soundbar

import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mSoundPool: SoundPool
    private var mLoaded: Boolean = false
    var mSoundMap: HashMap<Int, Int> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setUpUi()
        this.loadSounds()
    }

    private fun loadSounds() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.mSoundPool = SoundPool.Builder().setMaxStreams(10).build()
        } else {
            this.mSoundPool = SoundPool(10, AudioManager.STREAM_MUSIC, 0)
        }
        this.mSoundPool.setOnLoadCompleteListener { _, _, _ -> mLoaded = true }
        this.mSoundMap[R.raw.bird_song] = this.mSoundPool.load(this, R.raw.bird_song, 1)
        this.mSoundMap[R.raw.child_laugh] = this.mSoundPool.load(this, R.raw.child_laugh, 1)
        this.mSoundMap[R.raw.evil_laugh] = this.mSoundPool.load(this, R.raw.evil_laugh, 1)

    }

    private fun setUpUi() {
        child.setOnClickListener(this)
        evil.setOnClickListener(this)
        bird.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (!this.mLoaded) return
        when (v!!.id) {
            R.id.bird -> playSound(R.raw.bird_song)
            R.id.child -> playSound(R.raw.child_laugh)
            R.id.evil -> playSound(R.raw.evil_laugh)
        }

    }

    private fun playSound(soundToPlay: Int) {
        val soundID = this.mSoundMap[soundToPlay] ?: 0
        this.mSoundPool.play(soundID, 1f, 1f, 1, 0, 1f)

    }
}
