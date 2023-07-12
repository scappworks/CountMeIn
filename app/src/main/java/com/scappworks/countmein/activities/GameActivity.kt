package com.scappworks.countmein.activities

import HandsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
        val gameVariables = GameVariables(playerCount, deckCount)
        val handsRV = findViewById<RecyclerView>(R.id.player_hands_rv)
        var handsRvList = gameVariables.playerHands
        var handsRvAdapter = HandsAdapter(this, handsRvList, gameVariables)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val nextHandButton = findViewById<Button>(R.id.next_hand_button)
        val showHandsTotalButton = findViewById<Button>(R.id.show_hand_totals_button)
        val runningCountGuessEditText = findViewById<EditText>(R.id.running_count_guess)
        val trueCountGuessEditText = findViewById<EditText>(R.id.true_count_guess)
        val showTotalsTextView = findViewById<TextView>(R.id.show_totals_box)
        val cardImageArray = resources.getStringArray(R.array.card_images)

        gameVariables.doUpdateRunningCount()
        setTotalViewText(showTotalsTextView, gameVariables)

        nextHandButton.setOnClickListener {
            if (!gameVariables.checkFinished()) {
                gameVariables.doDrawHands(cardImageArray)
                handsRvList = gameVariables.playerHands
                handsRvAdapter = HandsAdapter(this, handsRvList, gameVariables)
                handsRV.adapter = handsRvAdapter
                showTotalsTextView.visibility = View.INVISIBLE
                runningCountGuessEditText.text.clear()
                updateHandImages(gameVariables, cardImageArray)
                // This allows the color to change for the new hand
                gameVariables.changeColor = true
                gameVariables.changeColor = false

                // This is used to detect if the deck is finished before all the hands
                // have been dealt. It is to push the final state of finished without
                // having to push any more buttons, and display promptly
                if (gameVariables.checkFinished()) {
                    setTotalViewText(showTotalsTextView, gameVariables)
                    showTotalsTextView.visibility = View.VISIBLE
                    handsRvAdapter.revealed = true
                }
            }

            else {
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
                }

                else {
                    showTotalsTextView.visibility = View.VISIBLE
                    handsRvAdapter.revealed = true
                }

                setTotalViewText(showTotalsTextView, gameVariables)
            }
        }

        updateHandImages(gameVariables, cardImageArray)
        gameVariables.doDrawHands(cardImageArray)

        handsRV.adapter = handsRvAdapter
        handsRV.layoutManager = linearLayoutManager
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
    }
}