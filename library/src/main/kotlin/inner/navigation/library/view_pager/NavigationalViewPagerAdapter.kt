package inner.navigation.library.view_pager

import androidx.annotation.IntDef
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import inner.navigation.library.NavigationalFragment
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



    private val fragments = mutableListOf<Pair<Fragment, Boolean>>()

    override fun getCount(): Int = fragments.size

    override fun getItem(position: Int): Fragment {
//        if (fragments[position].second is NavigationalFragment) {
//            fm.beginTransaction()
//                    .replace(R.id.fragmnent_container_navigational, fragments[position].first, fragments[position].second?.arguments?.getString(NavigationalFragment.KEY_TAG))
//                    .commit()
//            fragments[position] = Triple(fragments[position].first, null, fragments[position].third)
//        }
        return fragments[position].first
    }

    fun addFragment(fragment: Fragment, supportInnerNavigation: Boolean = false) {
        if (supportInnerNavigation) {
            val nav = NavigationalFragment.newInstance(fragment::class.java.simpleName)
            nav.replaceInitial(fragment)
            fragments.add(Pair(nav, supportInnerNavigation))
        } else {
            fragments.add(Pair(fragment, supportInnerNavigation))
        }


    }
}
