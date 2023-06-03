package com.scappworks.countmein.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.countmein.R


class HandsRecyclerView (private val dataSet: Array<String>) :
    RecyclerView.Adapter<HandsRecyclerView.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: CardView) : RecyclerView.ViewHolder(view) {
        val cardView: CardView

        init {
            // Define click listener for the ViewHolder's View
            cardView = view.findViewById(R.id.player_hand)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view: CardView = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.hand_player, viewGroup, false) as CardView

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.cardView.background = R.color.cardview_dark_background.toDrawable()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}
