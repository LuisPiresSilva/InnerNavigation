package inner.navigation.library.view_pager

import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/*
used when there are a fixed and small number of tabs
 */
class SimpleViewPagerAdapter(fm: FragmentManager, behaviour: Int) : FragmentPagerAdapter(fm, behaviour) {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(BEHAVIOR_SET_USER_VISIBLE_HINT, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    annotation class Behavior

    private val fragments = mutableListOf<Fragment>()

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
    }
}