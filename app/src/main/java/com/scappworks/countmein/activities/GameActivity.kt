package com.scappworks.countmein.activities

import HandsAdapter
import android.content.res.Configuration
import android.media.tv.TvTrackInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.countmein.R
import com.scappworks.countmein.variables.GameVariables
import kotlin.math.log

private var changeHand = true
private var firstRun = true
private var previousOrientation = 0
private lateinit var gameVariablesTrue: GameVariables

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()

        // Choices from MainActivity
        val extras = intent.extras
        val playerCount = extras!!.getString("playerCount")!!.toInt()
        val deckCount = extras.getString("deckCount")!!.toInt()
        // Variables for this activity
        val gameVariables = if(changeHand) {
            gameVariablesTrue = GameVariables(playerCount, deckCount)
            gameVariablesTrue
        }

        else {
            gameVariablesTrue
        }
        val handsRV = findViewById<RecyclerView>(R.id.player_hands_rv)
        var handsRvList = gameVariables.playerHands
        var handsRvAdapter = HandsAdapter(this, handsRvList, gameVariables)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val nextHandButton = findViewById<Button>(R.id.next_hand_button)
        val showHandsTotalButton = findViewById<Button>(R.id.show_hand_totals_button)
        val showTotalsTextView = findViewById<TextView>(R.id.show_totals_box)
        val cardImageArray = resources.getStringArray(R.array.card_images)

        previousOrientation = resources.configuration.orientation
        changeHand = previousOrientation == resources.configuration.orientation

        if (changeHand) {
            if (firstRun) {
                gameVariables.doUpdateRunningCount()
                firstRun = false
            }

            setTotalViewText(showTotalsTextView, gameVariables)

            nextHandButton.setOnClickListener {
                if (!gameVariables.checkFinished()) {
                    gameVariables.doDrawHands(cardImageArray)
                    handsRvList = gameVariables.playerHands
                    handsRvAdapter = HandsAdapter(this, handsRvList, gameVariables)
                    handsRV.adapter = handsRvAdapter
                    showTotalsTextView.visibility = View.INVISIBLE
                    gameVariables.setColors(-1)

                    // This is used to detect if the deck is finished before all the hands
                    // have been dealt. It is to push the final state of finished without
                    // having to push any more buttons, and display promptly
                    if (gameVariables.checkFinished()) {
                        setTotalViewText(showTotalsTextView, gameVariables)
                        showTotalsTextView.visibility = View.VISIBLE
                        handsRvAdapter.revealed = true
                    } else {
                        updateHandImages(gameVariables, cardImageArray)
                    }
                } else {
                    showTotalsTextView.visibility = View.VISIBLE
                    handsRvAdapter.revealed = true
                    setTotalViewText(showTotalsTextView, gameVariables)
                }
            }

            showHandsTotalButton.setOnClickListener {
                handsRvAdapter.notifyDataSetChanged()

                if (!gameVariables.checkFinished()) {
                    if (showTotalsTextView.visibility == View.VISIBLE) {
                        showTotalsTextView.visibility = View.INVISIBLE
                        handsRvAdapter.revealed = false
                    } else {
                        showTotalsTextView.visibility = View.VISIBLE
                        handsRvAdapter.revealed = true
                    }

                    setTotalViewText(showTotalsTextView, gameVariables)
                }
            }

            updateHandImages(gameVariables, cardImageArray)

            handsRV.adapter = handsRvAdapter
            handsRV.layoutManager = linearLayoutManager
            changeHand = false
        }
    }

    private fun updateHandImages(gameVariables: GameVariables, cardImageArray: Array<String>) {
        if (!gameVariables.checkFinished()) {
            gameVariables.playerHands.forEach {
                val cardImagePaths =
                    gameVariables.updateHandImages(it.firstCard, it.secondCard, cardImageArray)
                val uriList = mutableListOf<Int>()

                cardImagePaths.forEach { path ->
                    val card = path.substring(path.indexOf("e/") + 2, path.indexOf(".p"))

                    cardImageArray.forEach { image ->
                        if (image.contains(card)) {
                            val imageResource =
                                resources.getIdentifier(card, "drawable", packageName)
                            uriList.add(imageResource)
                        }
                    }
                }

                it.firstCardImage = uriList.first()
                it.secondCardImage = uriList.last()

            }
        }
    }

    private fun setTotalViewText(tvTextView: TextView, gv: GameVariables) {
        // Creates string using string resource with variables
        val totalViewString = getString(R.string.running_and_true_count, gv.runningCount.toString(),
            gv.trueCount.toString())
        tvTextView.text = totalViewString

        // Set show totals text sizes based on which layout is used
        if (tvTextView.tag == "stb_phone_portrait") {
            val screenDensity = resources.displayMetrics.density
            tvTextView.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (45 / screenDensity), resources.displayMetrics
            )
        }

        else if (tvTextView.tag == "stb_phone_landscape") {
            val screenDensity = resources.displayMetrics.density
            tvTextView.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (35 / screenDensity), resources.displayMetrics
            )
        }

        else if (tvTextView.tag == "stb_7-tab_portrait") {
            val screenDensity = resources.displayMetrics.density
            tvTextView.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (50 / screenDensity), resources.displayMetrics
            )
        }

        else if (tvTextView.tag == "stb_7-tab_landscape") {
            val screenDensity = resources.displayMetrics.density
            tvTextView.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (35 / screenDensity), resources.displayMetrics
            )
        }

        else if (tvTextView.tag == "stb_10-tab_portrait") {
            val screenDensity = resources.displayMetrics.density
            tvTextView.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (60 / screenDensity), resources.displayMetrics
            )
        }

        else if (tvTextView.tag == "stb_10-tab_landscape") {
            val screenDensity = resources.displayMetrics.density
            tvTextView.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, (45 / screenDensity), resources.displayMetrics
            )
        }
    }
}