package inner.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import inner.navigation.library.R
import inner.navigation.library.databinding.FragmentNavigationalBinding
import java.lang.IllegalStateException
import javax.inject.Inject


class NavigationalFragment : Fragment(), HasAndroidInjector, INavigationalFragment {

    companion object {
        const val KEY_TAG = "KEY_TAG"

        fun newInstance(tag: String): NavigationalFragment {
            val args = Bundle()
            args.putString(KEY_TAG, tag)
            val fragment = NavigationalFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun androidInjector() = androidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(this, callback)

        childFragmentManager.addOnBackStackChangedListener {
            callback.isEnabled = childFragmentManager.backStackEntryCount > 0
        }
    }


    @LayoutRes
    fun layoutToInflate(): Int = R.layout.fragment_navigational

    private val dataBinding: FragmentNavigationalBinding by lazy {
        DataBindingUtil.inflate<FragmentNavigationalBinding>(LayoutInflater.from(context), layoutToInflate(), null, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding.lifecycleOwner = this
        return dataBinding.root
    }


    val callback: OnBackPressedCallback = object : OnBackPressedCallback(false /* enabled by default */) {
        override fun handleOnBackPressed() { // Handle the back button event
            childFragmentManager.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        callback.isEnabled = childFragmentManager.backStackEntryCount > 0
    }

    override fun onPause() {
        super.onPause()
        callback.isEnabled = false
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (::initialFragment.isInitialized) {
            (initialFragment as INavigationalFragment.Listener).setNavigationCallback(this)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (::initialFragment.isInitialized) {
            setInitialFrag()
        }
        if (arguments?.containsKey(KEY_TAG) == true) {
            val frag = childFragmentManager.findFragmentByTag(requireArguments().getString(KEY_TAG))
            if(frag != null) {
                initialFragment = frag
                (initialFragment as INavigationalFragment.Listener).setNavigationCallback(this)
            }
        }

    }

    lateinit var initialFragment: Fragment
    fun replaceInitial(frag: Fragment) {
        initialFragment = frag
        (initialFragment as INavigationalFragment.Listener).setNavigationCallback(this)
    }

    private fun setInitialFrag(){
        if (isAdded) {
            for(i in 0 until childFragmentManager.backStackEntryCount) {
                childFragmentManager.popBackStack()
            }
        }

        if (::initialFragment.isInitialized) {
            if (arguments?.containsKey(KEY_TAG) == true) {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_navigational, initialFragment, arguments?.getString(KEY_TAG))
                    .commit()
            } else {
                childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_navigational, initialFragment)
                    .commit()
            }
        }

    }


    fun getLastFragment(): Fragment? {
        return childFragmentManager.fragments.last()
    }


    override fun addInnerFragment(oldFrag: Fragment, frag: Fragment, sharedElement: SharedElementData?) {
        if (isAdded) {
            (frag as INavigationalFragment.Listener).setNavigationCallback(this)
            if (frag.arguments?.containsKey(KEY_TAG) == true) {
                childFragmentManager.beginTransaction()
                    .apply {
                        add(R.id.fragment_container_navigational, frag, frag.requireArguments().getString(KEY_TAG))
                        sharedElement?.let {
                            setReorderingAllowed(true)
                            addSharedElement(it.sharedElement, it.transitionName)
                        }
                        hide(oldFrag)
                        addToBackStack(frag.requireArguments().getString(KEY_TAG))
                    }
                    .commit()
            } else {
                childFragmentManager.beginTransaction()
                    .apply {
                        add(R.id.fragment_container_navigational, frag)
                        sharedElement?.let {
                            setReorderingAllowed(true)
                            addSharedElement(it.sharedElement, it.transitionName)
                        }
                        hide(oldFrag)
                        addToBackStack(null)
                    }
                    .commit()
            }

        } else {
            throw IllegalStateException("Fragment NavigationalFragment has not been attached yet")
        }

    }

}