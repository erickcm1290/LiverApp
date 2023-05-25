package com.ecm.liverapp.Utilities

import android.R
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment

abstract class UTBaseActivity : AppCompatActivity() {
    private val TAG = UTBaseActivity::class.java.simpleName

    var permissionsResult: ActivityCompat.OnRequestPermissionsResultCallback? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onPause() {
        super.onPause()
    }

    fun goToActivity(intent: Intent, flags: Int) {
        if (flags == NO_FLAGS) {
            startActivity(intent)
        } else {
            startActivity(intent.addFlags(flags))
        }
    }

    fun <T : Fragment?> showFragment(
        fragmentClass: Class<T>,
        containerViewId: Int,
        bundle: Bundle?,
        addToBackStack: Boolean,
        clearStack: Boolean
    ) {
        val fragmentManager = supportFragmentManager
        if (clearStack) {
            for (i in 0 until fragmentManager.backStackEntryCount) {
                fragmentManager.popBackStack()
            }
        }
        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragment: Fragment?

        try {
            fragment = fragmentClass.newInstance()
            fragment!!.arguments = bundle
        } catch (e: Exception) {
            throw RuntimeException(getString(com.ecm.liverapp.R.string.nuevo_fragment), e)
        }

        fragmentTransaction.setCustomAnimations(
            R.animator.fade_in,
            R.animator.fade_out,
            R.anim.slide_in_left,
            R.anim.slide_out_right
        )
        fragmentTransaction.replace(containerViewId, fragment, fragmentClass.simpleName)
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(fragmentClass.name)
        }
        fragmentTransaction.commit()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager)
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var accion = false
        Log.d(TAG, "empezando un key down")
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            for (i in supportFragmentManager.fragments.indices) {
                if (supportFragmentManager.fragments[i] is UTBaseFragment) {
                    val fragment: UTBaseFragment =
                        supportFragmentManager.fragments[i] as UTBaseFragment
                    accion = accion || fragment.onBackPressed()
                }
            }
        }
        Log.d(TAG, " accion = $accion")
        return accion || super.onKeyDown(keyCode, event)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (permissionsResult != null) {
            permissionsResult!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    companion object {
        const val NO_FLAGS = -1
    }
}