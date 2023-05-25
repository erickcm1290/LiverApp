package com.ecm.liverapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ecm.liverapp.API.MainActivityInterface
import com.ecm.liverapp.UI.Fragments.FragmentMain
import com.ecm.liverapp.UI.Fragments.FragmentsProducts
import com.ecm.liverapp.Utilities.UTBaseActivity
import com.ecm.liverapp.databinding.ActivityMainBinding

class MainActivity : UTBaseActivity(), MainActivityInterface {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showFragmentMain()
    }

    override fun showFragmentMain() {
        showFragment(FragmentMain::class.java, binding.clMain.id, null, false, true)
    }

    override fun showFragmentProducts() {
        showFragment(FragmentsProducts::class.java, binding.clMain.id, null, true, false)
    }

    override fun mostrarProgress(valor: Boolean) {
        if (valor == true) {
            binding.fdCarga.visibility = View.VISIBLE
            binding.pbCarga.visibility = View.VISIBLE
        } else {
            binding.fdCarga.visibility = View.GONE
            binding.pbCarga.visibility = View.GONE
        }
    }

}