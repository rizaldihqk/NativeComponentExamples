package com.nativecomponentexamples.modules.list

import com.nativecomponentexamples.R
import android.view.View
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class ProductHolder(itemView: View) : ViewHolder(itemView) {
    var favorite: ToggleButton = itemView.findViewById<ToggleButton>(R.id.favButton)
    var ask: TextView = itemView.findViewById<TextView>(R.id.ask)
    var bid: TextView = itemView.findViewById<TextView>(R.id.bid)
    var price: TextView = itemView.findViewById<TextView>(R.id.hargaid)
}
