package com.nativecomponentexamples.modules.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.bridge.WritableMap
import com.facebook.react.modules.core.DeviceEventManagerModule
import com.facebook.react.uimanager.events.RCTEventEmitter
import com.google.gson.Gson
import com.nativecomponentexamples.R
import java.util.Arrays


class ListView(context: Context) : LinearLayout(context) {
    private val recyclerView: WrapperView
    private val swipeRefreshLayout: SwipeRefreshLayout
    private val adapter: RecyclerViewAdapter
    private var products: Array<Product>? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.list, this, true)
        recyclerView = findViewById(R.id.listView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        swipeRefreshLayout = findViewById(R.id.refresh_layout)
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: ReadableArray?) {
        post {
            val gson = Gson()
            if (data != null) {
                products = gson.fromJson(data.toString(), Array<Product>::class.java)
                adapter.clearProducts()
                adapter.addProducts(products!!.toList())
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun setRefreshing(refreshFeed: Boolean) {
        post {
            swipeRefreshLayout.setOnRefreshListener { refreshFeed() }
            swipeRefreshLayout.isRefreshing = refreshFeed
        }
    }

    private fun refreshFeed() {
        val map: WritableMap = Arguments.createMap()
        try {
            val reactContext = context as ReactContext
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit("refreshFeed", map)
        } catch (e: Exception) {
            Log.e("ReactNative", "Caught Exception: ${e.message}")
        }
    }

    fun onToggleFav() {
        // Implementation here
    }

    private fun onReceiveNativeEvent(vararg objects: Any) {
        Log.v("React", "dipanggil")
        val event: WritableMap = Arguments.createMap()
        event.putString("data", adapter.pvalue.toString())
        val reactContext = context as ReactContext
        reactContext
            .getJSModule(RCTEventEmitter::class.java)
            .receiveEvent(id, "callback", event)
    }

    // Uncomment and implement if needed
    /*
    fun toggleFav(): Int {
        val value = -1
        return adapter.pvalue
        try {
            val event: WritableMap = Arguments.createMap()
            val reactContext = context as ReactContext
            reactContext
                .getJSModule(RCTEventEmitter::class.java)
                .receiveEvent(adapter.toggleButtonFunc(), "favFunc", event)
        } catch (e: Exception) {
            Log.e("ReactNative", "Caught Exception: ${e.message}")
        }
    }
    */


}
