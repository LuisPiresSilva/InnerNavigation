package inner.navigation.view_pager

import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/*
used when there are going to potentially be a lot of tabs
 */
class SimpleViewPagerStateAdapterTitle(fm: FragmentManager, behaviour: Int) : FragmentStatePagerAdapter(fm, behaviour) {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(BEHAVIOR_SET_USER_VISIBLE_HINT, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    annotation class Behavior

    private val fragmentIDs = mutableListOf<Long>()
    private val fragments = mutableListOf<Fragment>()
    private val fragmentTitles = mutableListOf<String>()

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getPageTitle(position: Int): CharSequence = fragmentTitles[position]

    fun addFragment(fragment: Fragment, title: String, id : Long) {
        if(!containsFragment(id)) {
            fragmentIDs.add(id)
            fragments.add(fragment)
            fragmentTitles.add(title)
        }
    }

    fun removeFragment(title: String, id : Long) {
        if(containsFragment(id)) {
            val index = fragmentIDs.indexOf(id)
            fragmentIDs.removeAt(index)
            fragments.removeAt(index)
            fragmentTitles.removeAt(index)
        }
    }

    fun containsFragment(id: Long) : Boolean {
        return fragmentIDs.contains(id)
    }
}