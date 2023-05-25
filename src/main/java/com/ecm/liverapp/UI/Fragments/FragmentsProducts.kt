package com.ecm.liverapp.UI.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecm.liverapp.API.MainActivityInterface
import com.ecm.liverapp.Core.Constants.BASE_URL
import com.ecm.liverapp.Data.Models.Models
import com.ecm.liverapp.Data.Network.APIServices
import com.ecm.liverapp.R
import com.ecm.liverapp.UI.Adapters.AdaperProductos
import com.ecm.liverapp.UI.Dialogs.DialogSimple
import com.ecm.liverapp.Utilities.UTBaseFragment
import com.ecm.liverapp.databinding.FragmentProductsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class FragmentsProducts : UTBaseFragment() {
    private var interfaz: MainActivityInterface? = null
    private var binding: FragmentProductsBinding? = null
    private var listaProductos: List<Models.Record?>? = null
    private lateinit var adapter: AdaperProductos
    private var pagenumber : Int? = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding!!.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.stProductos.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                var handled = false
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    consultaProductos(
                            Objects.requireNonNull(binding!!.stProductos.text).toString()
                                .trim { it <= ' ' }, pagenumber!!.inc()
                    )
                    handled = true
                    val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm!!.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
                }
                return handled
            }
        })
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun consultaProductos(searchWord : String, pageNumber: Int) {
        interfaz!!.mostrarProgress(true)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val respuesta = getRetrofit().create(APIServices::class.java).getProductos("plp?search-string={$searchWord}&page-number=$pageNumber")
                requireActivity().runOnUiThread {
                    if (respuesta.isSuccessful) {
                        listaProductos = respuesta.body()!!.plpResults.records
                        if (listaProductos!!.size > 0){
                            listadoProductos()
                        } else {
                            val dialog = DialogSimple(getString(R.string.no_resultados))
                            dialog.show(childFragmentManager, "Error")
                            interfaz!!.mostrarProgress(false)
                        }
                    } else {
                        val dialog = DialogSimple(getString(R.string.error_generarlista))
                        dialog.show(childFragmentManager, "Error")
                        interfaz!!.mostrarProgress(false)
                    }
                }
            } catch (e: Exception) {
                val dialog = DialogSimple(e.message.toString())
                dialog.show(childFragmentManager, "Error")
            }
        }
    }

    private fun listadoProductos() {
        adapter = AdaperProductos(listaProductos)
        binding!!.rvProductos.layoutManager = LinearLayoutManager(context)
        binding!!.rvProductos.adapter = adapter
        interfaz!!.mostrarProgress(false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityInterface) {
            interfaz = context
        }
    }

    override fun onBackPressed(): Boolean {
        return super.onBackPressed()
    }
}