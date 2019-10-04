package com.godwin.happyratingview

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntRange


/**
 * Created by WiSilica on 10/3/2019 12:25 PM.
 *
 * @author : Godwin Joseph Kurinjikattu
 * @since : 2017
 */

class HappyView : View {
    private var progress: Float = 0F
    private var max: Float = 100F
    private var strokeColor: Int = Color.DKGRAY
    private var eyeColor: Int = Color.DKGRAY
    private var irisColor: Int = Color.DKGRAY
    private var strokeWidth: Int = 10

    private lateinit var pathPaint: Paint
    private lateinit var eyePaint: Paint


    private val path = Path()

    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        initialize(context, attrs, defStyle)
    }

    private fun initialize(context: Context, attr: AttributeSet? = null, defStyle: Int = 0) {
        val typedArray: TypedArray = context.theme.obtainStyledAttributes(
            attr,
            R.styleable.HappyView,
            0,
            defStyle
        )
        try {
            strokeColor =
                typedArray.getColor(R.styleable.HappyView_pathColor, Color.DKGRAY)
            eyeColor =
                typedArray.getColor(R.styleable.HappyView_eyeColor, Color.DKGRAY)
            irisColor =
                typedArray.getColor(R.styleable.HappyView_irisColor, Color.DKGRAY)

            max = typedArray.getInt(R.styleable.HappyView_max, 0).toFloat()
            progress = typedArray.getInt(R.styleable.HappyView_progress, 0).toFloat()
            strokeWidth = typedArray.getDimensionPixelSize(R.styleable.HappyView_strokeWidth, 10)

            pathPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            pathPaint.isAntiAlias = true
            pathPaint.color = strokeColor
            pathPaint.style = Paint.Style.STROKE
            pathPaint.strokeWidth = strokeWidth.toFloat()
            pathPaint.strokeCap = Paint.Cap.ROUND

            eyePaint = Paint()
            eyePaint.isAntiAlias = true
        } catch (e: Exception) {
            L.e(javaClass.simpleName, e.localizedMessage)
        } finally {
            typedArray.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val dimen = if (measuredWidth > measuredHeight) measuredWidth else measuredHeight
        L.v(javaClass.simpleName, "View Dimension set To : $dimen")
        setMeasuredDimension(dimen, dimen)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        L.v(javaClass.simpleName, "Value Cahnging : $progress")

        canvas?.drawPath(drawPath(), pathPaint)

        drawIris(canvas)
    }


    private fun drawIris(canvas: Canvas?) {

        val widthFraction = (width / 5).toFloat()
        val heightFraction = (height / 5).toFloat()

        val leftIrisX =
            widthFraction * 2 - ((widthFraction / 2) * progress / max) - widthFraction / 4
        val rightIrisX =
            widthFraction * 4 - ((widthFraction / 2) * progress / max) - widthFraction / 4
        val leftIrisY =
            heightFraction + heightFraction / 4 - ((heightFraction / 3) * progress / max)

        L.v(javaClass.simpleName, "height fraction:  $heightFraction Value iris : $leftIrisY")
        eyePaint.color = strokeColor
        eyePaint.style = Paint.Style.STROKE
        eyePaint.strokeWidth = strokeWidth.toFloat()
        eyePaint.strokeCap = Paint.Cap.ROUND
        canvas?.drawCircle(leftIrisX, leftIrisY, 10F, eyePaint)
        canvas?.drawCircle(rightIrisX, leftIrisY, 10F, eyePaint)

        eyePaint.color = irisColor
        eyePaint.style = Paint.Style.FILL
        canvas?.drawCircle(leftIrisX, leftIrisY, 10F, eyePaint)
        canvas?.drawCircle(rightIrisX, leftIrisY, 10F, eyePaint)

    }

    private fun drawPath(): Path {
        path.reset()
        /** DRAWING FOR LEFT EYE STARTS*/

        val widthFraction = (width / 5).toFloat()
        val heightFraction = (height / 5).toFloat()

        var leftX = widthFraction
        var leftY = heightFraction

        var midTopX = widthFraction * 2 - (widthFraction / 2)
        var midTopY = heightFraction - ((progress / max) * heightFraction) - heightFraction / 5

        var midBotX = widthFraction * 2 - ((progress / max) * (widthFraction / 2))
        var midBotY = heightFraction * 3 - ((progress / max) * (heightFraction))

        var rightX = widthFraction * 2
        var rightY = heightFraction

        path.moveTo(leftX, leftY)

        path.cubicTo(
            leftX,
            leftY,
            midTopX,
            midTopY,
            rightX,
            rightY
        )
        path.moveTo(leftX, leftY)
        path.cubicTo(
            leftX,
            leftY,
            midBotX,
            midBotY,
            rightX,
            rightY
        )
        /** DRAWING FOR RIGHT EYE STARTS*/
        leftX = widthFraction * 3
        leftY = heightFraction

        midTopX = widthFraction * 4 - widthFraction / 2
        midTopY =
            heightFraction - ((progress / max) * heightFraction) - heightFraction / 5

        midBotX =
            widthFraction * 4 - ((progress / max) * (widthFraction / 2))
        midBotY = heightFraction * 3 - ((progress / max) * (heightFraction))

        rightX = widthFraction * 4
        rightY = heightFraction

        path.moveTo(leftX, leftY)

        path.cubicTo(
            leftX,
            leftY,
            midTopX,
            midTopY,
            rightX,
            rightY
        )
        path.moveTo(leftX, leftY)
        path.cubicTo(
            leftX,
            leftY,
            midBotX,
            midBotY,
            rightX,
            rightY
        )
        /** DRAWING FOR MOUTH STARTS*/
        leftX = widthFraction
        leftY = heightFraction * 4

        midBotX = widthFraction * 4 - ((progress / max) * (widthFraction * 2))
        midBotY = heightFraction * 3 + ((progress / max) * (heightFraction * 3))

        rightX = widthFraction * 4
        rightY = heightFraction * 4

        path.moveTo(leftX, leftY)
        path.cubicTo(
            leftX,
            leftY,
            midBotX,
            midBotY,
            rightX,
            rightY
        )
        return path
    }

    fun getProgressInPercentile(): Float {
        return (progress * 100) / max
    }

    fun getProgress(): Int {
        return progress.toInt()
    }

    fun setProgress(
        progress: Int, isAnimate: Boolean = false, @IntRange(
            from = 200,
            to = 2000
        ) duration: Int = 1000
    ) {
        if (isAnimate && duration > 0 && duration < 2000) {
            val animator = ValueAnimator.ofInt(this.progress.toInt(), progress)
            animator.addUpdateListener { animation ->
                this.progress = (animation.animatedValue as Int).toFloat()
                invalidate()
            }
            animator.duration = duration.toLong()
            animator.start()
        }
        this.progress = progress.toFloat()
        invalidate()
    }

    fun setMax(max: Float) {
        this.max = max
        invalidate()
    }

    fun setPathColor(
        color: Int, isAnimate: Boolean = false, @IntRange(
            from = 500,
            to = 3000
        ) duration: Int = 1000
    ) {
        if (isAnimate && duration > 500 && duration < 3000) {
            val animator = ValueAnimator.ofArgb(strokeColor, color)
            animator.addUpdateListener { animation ->
                this.strokeColor = (animation.animatedValue as Int)
                invalidate()
            }
            animator.duration = duration.toLong()
            animator.start()
        }
        this.strokeColor = color
        invalidate()
    }

    fun setPathWidth(width: Int) {
        this.strokeWidth = width
        invalidate()
    }
}