package com.scappworks.countmein.activities

import HandsAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
        val handsRvList = gameVariables.playerHands
        val handsRvAdapter = HandsAdapter(this, handsRvList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        gameVariables.updateRunningCount()

        val b = findViewById<Button>(R.id.test_button)
        b.setOnClickListener {
            gameVariables.doDrawHands()
            gameVariables.updateRunningCount()

        }

        handsRV.adapter = handsRvAdapter
        handsRV.layoutManager = linearLayoutManager
    }
}