package com.eylulcan.moviefragment

import android.content.Context
import android.graphics.Canvas
import android.text.TextUtils
import android.util.AttributeSet
import androidx.core.content.res.ResourcesCompat

private const val DEFAULT_TEXT_SIZE = 20F
class CustomTitleTextView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context) : super(context) {
        val typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        this.textSize = DEFAULT_TEXT_SIZE
        //val face = Typeface.createFromAsset(context.assets, R.font.lato_bold.toString())
        this.typeface = typeface
        this.setTextColor(resources.getColor(R.color.white_light_grey))
        this.setLines(2)
        this.ellipsize = TextUtils.TruncateAt.END
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val  typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        this.typeface = typeface
        this.textSize = DEFAULT_TEXT_SIZE
        this.setTextColor(resources.getColor(R.color.white_light_grey))
        this.setLines(2)
        this.ellipsize = TextUtils.TruncateAt.END
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        val  typeface = ResourcesCompat.getFont(context, R.font.lato_bold)
        this.typeface = typeface
        this.textSize = DEFAULT_TEXT_SIZE
        this.setTextColor(resources.getColor(R.color.white_light_grey))
        this.setLines(2)
        this.ellipsize = TextUtils.TruncateAt.END
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
    }
}