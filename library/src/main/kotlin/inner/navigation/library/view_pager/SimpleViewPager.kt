package inner.navigation.library.view_pager

import android.content.Context
import android.util.AttributeSet
import androidx.viewpager.widget.ViewPager
import android.view.MotionEvent



class SimpleViewPager : ViewPager {
    private var canSwipe : Boolean

    constructor(context: Context) : super(context) {
        this.canSwipe = true
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        this.canSwipe = true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return if (this.canSwipe) {
            super.onTouchEvent(event)
        } else false

    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return if (this.canSwipe) {
            super.onInterceptTouchEvent(event)
        } else false

    }

    fun setSwipeEnabled(enabled: Boolean) {
        this.canSwipe = enabled
    }
}