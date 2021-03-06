package de.ktbl.android.sharedlibrary.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import de.ktbl.android.sharedlibrary.BuildConfig
import de.ktbl.android.sharedlibrary.navigation.NavigationCommand
import de.ktbl.android.sharedlibrary.view.activity.IBarTitleSetable
import timber.log.Timber

typealias NavigationRequestHandler = (navigationCommand: NavigationCommand) -> Unit

abstract class AbstractBaseFragment : Fragment(), INavigationRequestHandler {
    /**
     * Needs to be implemented by subclasses to provide the id of the defaulNavHost View
     *
     * @return the R.id.x of the view holding the defaultNavHost
     */
    protected abstract val navHostId: Int
    protected val navController: NavController
        get() {
            if (BuildConfig.DEBUG && this.activity == null) {
                error("Assertion failed")
            }
            return Navigation.findNavController(this.requireActivity(), navHostId)
        }

    /**
     * Listener for navigationEvents of a [de.ktbl.android.sharedlibrary.livedata.LiveEventHandler]
     *
     * @param navigationCommand which holds the navigation request
     */
    override fun onNavigationRequest(navigationCommand: NavigationCommand) {
        if (navigationCommand.hasNotBeenHandled()) {
            navigationCommand.setHandled()
            val navController = navController
            if (navigationCommand.isNavigateBackCommand) {
                navController.popBackStack()
            } else {
                if (BuildConfig.DEBUG && navigationCommand.navDirection == null) {
                    error("Assertion failed")
                }
                navController.navigate(navigationCommand.navDirection)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val barSetable = this.activity as? IBarTitleSetable
        barSetable?.resetBarTitle()
    }


    fun setBarTitle(resId: Int) {
        val barTitleSetable = this.activity as? IBarTitleSetable?
        barTitleSetable?.setBarTitle(resId)
    }

    fun setBarTitle(title: CharSequence?) {
        val barTitleSetable = this.activity as? IBarTitleSetable?
        barTitleSetable?.setBarTitle(title)
    }

    /**
     * Create an Argument object of an *Args class which is generated by Android Studio.
     * This Method is just a simplification, thus the code below does not need to be in every
     * subclass
     *
     * @param fromBundle    reference of the `fromBundle(Bundle bundle)` static method from the
     * according *Args class
     * @param savedInstance savedInstance-bundle which is typically provided to the
     * [Fragment.onViewCreated] method.
     * @param <T>           *Args-class Type
     * @return an *Args objects from either the arguments, or if these are `null` from the given
     * savedInstance. If the savedInstance is null as well `null` is returned
    </T> */
    protected fun <Any> getArgs(fromBundle: (Bundle) -> Any, savedInstance: Bundle?): Any? {
        val arg = this.arguments
        try {
            return when {
                arg != null           -> {
                    Timber.tag(TAG)
                            .d("Arguments are given, therefore using the Fragments getArguments() for the *Args")
                    fromBundle(arg)
                }
                savedInstance != null -> {
                    Timber.tag(TAG)
                            .d("getArguments() has been null, savedInstance is not null, therefore using savedInstance for *Args")
                    fromBundle(savedInstance)
                }
                else                  -> {
                    Timber.tag(TAG)
                            .d("getArguments() as well as savedInstance is null, therefore returning null")
                    null
                }
            }
        } catch (e: IllegalArgumentException) {
            // we're landing here since arg or savedInstance does not provide fields
            // which are marked as non-null in the generated Args class
            return null
        }
    }

    companion object {
        private val TAG = AbstractBaseFragment::class.java.simpleName
    }
}