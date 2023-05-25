package com.ecm.liverapp.UI.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecm.liverapp.API.MainActivityInterface
import com.ecm.liverapp.Utilities.UTBaseFragment
import com.ecm.liverapp.databinding.FragmentMainBinding

class FragmentMain : UTBaseFragment() {
    private var interfaceMain: MainActivityInterface? = null
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.botonInicial.setOnClickListener {
            interfaceMain!!.showFragmentProducts()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityInterface) {
            interfaceMain = context
        }
    }

    override fun onBackPressed(): Boolean {
        activity?.moveTaskToBack(true)
        return super.onBackPressed()
    }


}