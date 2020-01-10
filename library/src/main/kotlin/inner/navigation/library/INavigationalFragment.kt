package inner.navigation.library

import androidx.fragment.app.Fragment

interface INavigationalFragment {
    fun add(frag: Fragment)

    interface Listener {
        fun setNavigationCallback(navigationalFragment: NavigationalFragment)
    }

}