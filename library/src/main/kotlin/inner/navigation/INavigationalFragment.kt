package inner.navigation

import androidx.fragment.app.Fragment

interface INavigationalFragment {
    fun addInnerFragment(oldFrag: Fragment, frag: Fragment, sharedElement: SharedElementData? = null)


    interface Listener {
        fun setNavigationCallback(navigationalFragment: NavigationalFragment)
    }
}