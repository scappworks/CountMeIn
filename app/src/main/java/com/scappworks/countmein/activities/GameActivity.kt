package com.scappworks.countmein.activities

import HandsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        val gameVariables = GameVariables(playerCount, deckCount)
        val handsRV = findViewById<RecyclerView>(R.id.player_hands_rv)
        var handsRvList = gameVariables.playerHands
        var handsRvAdapter = HandsAdapter(this, handsRvList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val nextHandButton = findViewById<Button>(R.id.next_hand_button)
        val showHandsTotalButton = findViewById<Button>(R.id.show_hand_totals_button)


        gameVariables.updateRunningCount()

        nextHandButton.setOnClickListener {
            if (!gameVariables.checkFinished()) {
                gameVariables.doDrawHands()
                gameVariables.updateRunningCount()
                handsRvList = gameVariables.playerHands
                handsRvAdapter = HandsAdapter(this, handsRvList)
                handsRV.adapter = handsRvAdapter
            }
        }

        handsRV.adapter = handsRvAdapter
        handsRV.layoutManager = linearLayoutManager

        showHandsTotalButton.setOnClickListener {
            handsRvAdapter.revealed = !handsRvAdapter.revealed
            handsRvAdapter.notifyDataSetChanged()
        }
    }
}