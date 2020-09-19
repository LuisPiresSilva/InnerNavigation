package inner.navigation.view_pager

import androidx.fragment.app.Fragment

data class NavigationalViewPagerAdapterFragmentTuple(
        val nav: Fragment?,
        val fragment: Fragment,
        val supportInnerNavigation: Boolean,
        val title: String?
)