package com.scappworks.countmein.activities

import HandsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.countmein.R
import com.scappworks.countmein.variables.GameVariables

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Choices from MainActivity
        val extras = intent.extras
        val playerCount = extras!!.getString("playerCount")!!.toInt()
        val deckCount = extras.getString("deckCount")!!.toInt()
        // Variables for this activity
        val gameVariables = GameVariables(playerCount, deckCount)
        val handsRV = findViewById<RecyclerView>(R.id.player_hands_rv)
        var handsRvList = gameVariables.playerHands
        var handsRvAdapter = HandsAdapter(this, handsRvList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val nextHandButton = findViewById<Button>(R.id.next_hand_button)
        val showHandsTotalButton = findViewById<Button>(R.id.show_hand_totals_button)
        val runningCountGuessEditText = findViewById<EditText>(R.id.running_count_guess)
        val runningCountGuessButton = findViewById<Button>(R.id.running_count_guess_submit)
        val trueCountGuessEditText = findViewById<EditText>(R.id.true_count_guess)
        val trueCountGuessButton = findViewById<Button>(R.id.true_count_guess_submit)
        val showTotalsTextView = findViewById<TextView>(R.id.show_totals_box)

        gameVariables.doUpdateRunningCount()
        setTotalViewText(showTotalsTextView, gameVariables)

        nextHandButton.setOnClickListener {
            if (!gameVariables.checkFinished()) {
                gameVariables.doDrawHands()
                handsRvList = gameVariables.playerHands
                handsRvAdapter = HandsAdapter(this, handsRvList)
                handsRV.adapter = handsRvAdapter
                showTotalsTextView.visibility = View.INVISIBLE
            }

            else {
                showTotalsTextView.visibility = View.VISIBLE
                setTotalViewText(showTotalsTextView, gameVariables)
            }
        }

        showHandsTotalButton.setOnClickListener {
            handsRvAdapter.revealed = !handsRvAdapter.revealed
            handsRvAdapter.notifyDataSetChanged()

            if (showTotalsTextView.visibility == View.VISIBLE) {
                showTotalsTextView.visibility = View.INVISIBLE
            }

            else {
                showTotalsTextView.visibility = View.VISIBLE
            }

            setTotalViewText(showTotalsTextView, gameVariables)
        }

        handsRV.adapter = handsRvAdapter
        handsRV.layoutManager = linearLayoutManager
    }

    private fun setTotalViewText(tvTextView: TextView, gv: GameVariables) {
        // Creates string using string resource with variables
        val totalViewString = getString(R.string.running_and_true_count, gv.runningCount.toString(),
            gv.trueCount.toString())
        tvTextView.text = totalViewString
    }
}