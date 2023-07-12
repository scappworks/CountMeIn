import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.countmein.R
import com.scappworks.countmein.variables.GameVariables
import kotlin.random.Random

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
        holder.firstCardImage.setImageResource(model.firstCardImage)
        holder.secondCardImage.setImageResource(model.secondCardImage)
        holder.runningCount.text = model.handCount.toString()
        changeBackgroundColor(itemCount, holder.layout)

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
        val firstCardImage: ImageView
        val secondCardImage: ImageView
        val runningCount: TextView
        val layout: ConstraintLayout

        init {
            firstCardImage = itemView.findViewById(R.id.first_card_image)
            secondCardImage = itemView.findViewById(R.id.second_card_image)
            runningCount = itemView.findViewById(R.id.running_count)
            layout = itemView.findViewById(R.id.player_hand_container)
        }
    }

    // Constructor
    init {
        this.handsModelList = handsList
    }

    private fun changeBackgroundColor(count: Int, layout: ConstraintLayout) {
        val color = Color.argb(255, Random.nextInt(256),Random.nextInt(256),Random.nextInt(256))

        layout.setBackgroundColor(color)
    }
}
