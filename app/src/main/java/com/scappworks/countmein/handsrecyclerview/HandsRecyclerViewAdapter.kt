import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.countmein.R
import com.scappworks.countmein.variables.GameVariables

class HandsAdapter(private val context: Context, handsList: List<GameVariables.PlayerHand>) :
    RecyclerView.Adapter<HandsAdapter.ViewHolder>() {
    private val handsModelList: List<GameVariables.PlayerHand>
    var revealed = false
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // to inflate the layout for each item of recycler view.
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.hand_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model: GameVariables.PlayerHand = handsModelList[position]
        holder.firstCardNumber.text = model.firstCard
        holder.firstCardImage.setImageResource(model.firstCardImage)
        holder.secondCardNumber.text = model.secondCard
        holder.secondCardImage.setImageResource(model.secondCardImage)
        holder.runningCount.text = model.handCount.toString()

        // Hides and reveals the total hand count
        if (revealed) {
            holder.runningCount.visibility = View.VISIBLE
        }
        else {
            holder.runningCount.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int {
        // this method is used for showing number of card items in recycler view.
        return handsModelList.count()
    }

    // View holder class for initializing of your views
    @SuppressLint("CutPasteId")
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstCardNumber: TextView
        val firstCardImage: ImageView
        val secondCardNumber: TextView
        val secondCardImage: ImageView
        val runningCount: TextView

        init {
            firstCardNumber = itemView.findViewById(R.id.first_card_number_view)
            firstCardImage = itemView.findViewById(R.id.first_card_image)
            secondCardNumber = itemView.findViewById(R.id.second_card_number_view)
            secondCardImage = itemView.findViewById(R.id.second_card_image)
            runningCount = itemView.findViewById(R.id.running_count)
        }
    }

    // Constructor
    init {
        this.handsModelList = handsList
    }
}
