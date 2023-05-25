package com.ecm.liverapp.UI.Adapters

import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ecm.liverapp.Data.Models.Models
import com.ecm.liverapp.databinding.ItemProductoBinding
import com.squareup.picasso.Picasso


class AdaperProductos(private var pProductos: List<Models.Record?>?) :
    RecyclerView.Adapter<AdaperProductos.ProductoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ProductoViewHolder(layoutInflater.inflate(com.ecm.liverapp.R.layout.item_producto, parent, false))
    }

    override fun getItemCount(): Int = pProductos!!.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val item = pProductos!![position]
        holder.bind(item!!)
    }

    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemProductoBinding.bind(view)
        fun bind(cardProducto: Models.Record) {
            Picasso.get().load(cardProducto.smImage).into(binding.imagen)
            binding.titulo.text = cardProducto.productDisplayName
            binding.marca.text = cardProducto.brand

            if (cardProducto.promoPrice == 0.0) {
                binding.listprice.text = " "
                binding.promoprice.text = "$ " + cardProducto.listPrice.toString()
            } else {
                binding.listprice.text = "$ " + cardProducto.listPrice.toString()
                val textView = binding.listprice
                textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                binding.promoprice.text = "$ " + cardProducto.promoPrice.toString()
            }

            try {
                //Implementar ciclo for y linearLayout.addView(view) para crear views de 20dpx20dp
                binding.lytColor.visibility = View.VISIBLE
                binding.color0.setBackgroundColor(Color.parseColor(cardProducto.variantsColor[0].colorHex))
                binding.color1.setBackgroundColor(Color.parseColor(cardProducto.variantsColor[1].colorHex))
                binding.color1.setBackgroundColor(Color.parseColor(cardProducto.variantsColor[2].colorHex))
            } catch (e: Exception) {

            }

        }
    }

    /*
        fun addViewsToLinearLayout(context: Context, linearLayout: LinearLayout, n: Int) {
        val layoutParams = LinearLayout.LayoutParams(
            dpToPx(context, 20),
            dpToPx(context, 20)
        )

        for (i in 0 until n) {
            val view = View(context)
            view.layoutParams = layoutParams
            linearLayout.addView(view)
        }
    }

    fun dpToPx(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density).toInt()
    }
     */

    fun updateListaProductos(pProductos: List<Models.Record?>?) {
        this.pProductos = pProductos
        notifyDataSetChanged()
    }
}