import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.countmein.R
import com.scappworks.countmein.handsrecyclerview.HandsRecyclerViewModel
import com.scappworks.countmein.variables.GameVariables

class HandsAdapter(private val context: Context, handsList: List<GameVariables.PlayerHand>) :
    RecyclerView.Adapter<HandsAdapter.ViewHolder>() {
    private val handsModelList: List<GameVariables.PlayerHand>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HandsAdapter.ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.hand_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HandsAdapter.ViewHolder, position: Int) {
        // to set data to textview and imageview of each card layout
        val model: GameVariables.PlayerHand = handsModelList[position]
        holder.firstCardNumber.setText(model.firstCard)
        holder.secondCardNumber.setText(model.secondCard)
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return handsModelList.count()
    }

    // View holder class for initializing of your views such as TextView and Imageview.
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstCardNumber: TextView
        val secondCardNumber: TextView
        val runningCount: TextView
        init {
            firstCardNumber = itemView.findViewById(R.id.first_card_number_view)
            secondCardNumber = itemView.findViewById(R.id.second_card_number_view)
            runningCount = itemView.findViewById(R.id.running_count)
        }
    }

    // Constructor
    init {
        this.handsModelList = handsList
    }
}
