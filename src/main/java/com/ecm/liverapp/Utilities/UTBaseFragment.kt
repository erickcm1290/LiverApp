package com.ecm.liverapp.Utilities

import android.R
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class UTBaseFragment : Fragment() {

    protected fun goToActivity(intent: Intent?, flags: Int) {
        val activity = activity as UTBaseActivity?
        activity!!.goToActivity(intent!!, flags)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDetach() {
        super.onDetach()
    }

    @Throws(RuntimeException::class)
    fun <T : Fragment?> showFragment(
        fragmentClass: Class<T>,
        containerViewId: Int,
        bundle: Bundle?,
        addToBackStack: Boolean,
        animation: Boolean
    ) {
        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment = fragmentManager.findFragmentByTag(fragmentClass.simpleName)
        if (fragment == null) {
            fragment = try {
                fragmentClass.newInstance()
            } catch (e: Exception) {
                throw RuntimeException(getString(com.ecm.liverapp.R.string.nuevo_fragment), e)
            }
        }
        fragment!!.arguments = bundle
        if (animation) {
            fragmentTransaction.setCustomAnimations(
                R.animator.fade_in,
                R.animator.fade_out,
                R.anim.slide_in_left,
                R.anim.slide_out_right
            )
        }
        fragmentTransaction.replace(containerViewId, fragment, fragmentClass.simpleName)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commit()
    }

    open fun onBackPressed(): Boolean {
        return false
    }
}