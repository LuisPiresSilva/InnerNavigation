package inner.navigation.view_pager

import android.view.ViewGroup
import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import inner.navigation.NavigationalFragment
import timber.log.Timber
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/*
used when there are a fixed and small number of tabs
 */
class NavigationalViewPagerAdapter(private val fm: FragmentManager, behaviour: Int) : FragmentStatePagerAdapter(fm, behaviour) {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef(BEHAVIOR_SET_USER_VISIBLE_HINT, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)
    annotation class Behavior

    private val fragments = mutableListOf<NavigationalViewPagerAdapterFragmentTuple>()

    override fun getPageTitle(position: Int) = if (position >= fragments.size || position < 0) "" else fragments[position].title
        ?: ""

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment {
        return if (fragments[position].supportInnerNavigation) {
            fragments[position].nav!!
        } else {
            fragments[position].fragment
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val ret = super.instantiateItem(container, position) as Fragment
        if (fragments[position].supportInnerNavigation) {
            fragments[position] = NavigationalViewPagerAdapterFragmentTuple(ret, fragments[position].fragment, fragments[position].supportInnerNavigation, fragments[position].title)
            (fragments[position].nav as NavigationalFragment).replaceInitial((fragments[position].fragment))
        } else {
            fragments[position] = NavigationalViewPagerAdapterFragmentTuple(fragments[position].nav, ret, fragments[position].supportInnerNavigation, fragments[position].title)
        }
        return ret
    }

    fun addFragment(fragment: Fragment, supportInnerNavigation: Boolean = false, tag: String? = null, title: String? = null) {
        Timber.i(fragments.size.toString())
        if (supportInnerNavigation) {
            val nav =
                if (tag != null) {
                    NavigationalFragment.newInstance(tag)
                } else {
                    NavigationalFragment.newInstance(fragment::class.java.simpleName)
                }
            nav.replaceInitial(fragment)
            fragments.add(NavigationalViewPagerAdapterFragmentTuple(nav, fragment, supportInnerNavigation, title))
        } else {
            fragments.add(NavigationalViewPagerAdapterFragmentTuple(null, fragment, supportInnerNavigation, title))
        }


    }
}