package com.godwin.happyview

import android.graphics.Color
import android.os.Bundle
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvAns.text = if (progress <= 25) "Behhh!!"
                else if (progress <= 50) "Mmmmm!!"
                else if (progress <= 75) " Nice!!"
                else "Awesome"

//                val transition: Transition = Slide()
//
//                TransitionManager.beginDelayedTransition(clMain,)
                happyView.setProgress(progress)
                clMain.setBackgroundColor(
                    blendColors(
                        resources.getColor(R.color.dumbYellow),
                        resources.getColor(R.color.dumbGreen),
                        (progress.toFloat().div(100))
                    )
                )
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    private fun blendColors(from: Int, to: Int, ratio: Float): Int {
        val inverseRatio = 1f - ratio

        val r = Color.red(to) * ratio + Color.red(from) * inverseRatio
        val g = Color.green(to) * ratio + Color.green(from) * inverseRatio
        val b = Color.blue(to) * ratio + Color.blue(from) * inverseRatio

        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }
}
