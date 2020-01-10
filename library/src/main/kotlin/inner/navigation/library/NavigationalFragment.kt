package inner.navigation.library

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
import inner.navigation.library.databinding.FragmentNavigationalBinding
import javax.inject.Inject


class NavigationalFragment : Fragment(), HasAndroidInjector, INavigationalFragment {

    companion object {
        const val KEY_TAG = "KEY_TAG"

        fun newInstance(tag : String): NavigationalFragment {
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

//        ViewModelProvider(activity as FragmentActivity, viewModelFactory).get(HomeViewModel::class.java)

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {
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
        activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container_navigational, initialFragment)
                ?.commit()
    }


    private lateinit var initialFragment : Fragment
    fun replaceInitial(frag: Fragment) {
        (frag as INavigationalFragment.Listener).setNavigationCallback(this)
        initialFragment = frag
    }


    override fun add(frag: Fragment) {
        if(isAdded) {
            childFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container_navigational, frag)
                    .addToBackStack(null)
                    .commit()
        } else {
            throw IllegalStateException("Fragment NavigationalFragment has not been attached yet")
        }
    }

}