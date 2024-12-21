package com.nativecomponentexamples.modules.list

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.Callback
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.google.gson.Gson
import com.nativecomponentexamples.R

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ProductHolder>() {

    private val products: MutableList<Product> = mutableListOf()
    internal var pvalue: Int = -1
    private lateinit var favButton: ToggleButton
    var origin: Point? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_items, parent, false)
        favButton = view.findViewById(R.id.favButton)
        return ProductHolder(view)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val product = products[position]
        holder.apply {
            ask.text = product.ask
            bid.text = product.bid
            price.text = product.price
            favorite.isChecked = product.favorite == true

            favorite.setOnClickListener {
                val event = Arguments.createMap()
                pvalue = adapterPosition
                try {
                    val gson = Gson()
                    val data = gson.toJson(products)
                    val map = Arguments.createMap()
                    map.putString(pvalue.toString(), data)
                    event.putMap("data", map)

                    Log.v("React", "Button clicked at position: $pvalue")

                    val reactContext = it.context as ReactContext
                    reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                        .emit("callback", event)

                } catch (e: Exception) {
                    Log.e("ReactNative", "Caught Exception: ${e.message}")
                }
            }
        }
    }

    override fun getItemCount(): Int = products.size

    fun clearProducts() {
        products.clear()
    }

    fun testFunc(callback: Callback): Callback {
        callback.invoke(pvalue)
        return callback
    }

    fun toggleButtonFunc(position: Int) {
        // Your toggle button function logic can go here
    }

    fun addProducts(newProducts: List<Product>) {
        products.addAll(newProducts)
    }

    fun parser(url: String): Uri = Uri.parse(url)

    class Point(var index: Int, var data: String)

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ask = itemView.findViewById<TextView>(R.id.ask) // Replace with appropriate ID
        val bid = itemView.findViewById<TextView>(R.id.bid) // Replace with appropriate ID
        val price = itemView.findViewById<TextView>(R.id.hargaid) // Replace with appropriate ID
        val favorite: ToggleButton = itemView.findViewById<ToggleButton>(R.id.favButton)
    }
}
