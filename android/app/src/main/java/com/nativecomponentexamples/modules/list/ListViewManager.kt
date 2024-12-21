package com.nativecomponentexamples.modules.list

import android.util.Log
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.facebook.react.uimanager.annotations.ReactProp
import com.facebook.react.common.MapBuilder

class ListViewManager : SimpleViewManager<ListView>() {

    private val REACT_CLASS = "nativeList"

    override fun getName(): String {
        return REACT_CLASS
    }

    override fun createViewInstance(reactContext: ThemedReactContext): ListView {
        return ListView(reactContext)
    }

    @ReactProp(name = "data")
    fun setData(view: ListView, data: ReadableArray) {
        view.setData(data)
    }

    @ReactProp(name = "refreshing")
    fun setRefreshing(view: ListView, refreshing: Boolean) {
        view.setRefreshing(refreshing)
    }

    @ReactProp(name = "callback")
    fun callbackFunc(view: ListView, refreshing: Boolean) {
        view.onToggleFav()
    }

    override fun getExportedCustomDirectEventTypeConstants(): Map<String, Any>? {
        Log.d("React", "called directEventType")
        return MapBuilder.builder<String, Any>()
            .put(
                "callback",
                MapBuilder.of("registrationName", "callback")
            )
            .build()
    }
}