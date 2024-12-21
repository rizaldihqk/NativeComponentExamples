package com.nativecomponentexamples.modules.list

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView


class WrapperView : RecyclerView {
    private var isRequestLayoutCalled = false

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    )

    @SuppressLint("WrongCall")
    override fun requestLayout() {
        super.requestLayout()
        if (!isRequestLayoutCalled) {
            isRequestLayoutCalled = true
            this.post {
                isRequestLayoutCalled = false
                layout(left, top, right, bottom)
                onLayout(false, left, top, right, bottom)
            }
        }
    }
}