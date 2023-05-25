package com.ecm.liverapp.UI.Dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.ecm.liverapp.API.MainActivityInterface
import com.ecm.liverapp.R
import com.ecm.liverapp.databinding.DialogSimpleBinding

class DialogSimple (val Message : String): DialogFragment()  {
    private lateinit var binding: DialogSimpleBinding
    private var interfaz: MainActivityInterface? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogSimpleBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(context, R.style.RoundedCornersDialog)
        builder.setView(binding.root)
        val bundle = Bundle()

        binding.titulo.setText(Message)
        binding.btnConfirmar.setOnClickListener {
            dismiss()
        }
        return builder.create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityInterface) {
            interfaz = context
        }
    }
}