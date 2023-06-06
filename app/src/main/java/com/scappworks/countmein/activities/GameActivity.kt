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

        gameVariables.updateRunningCount()

        Log.i("RC", gameVariables.runningCount.toString())
        Log.i("SC", gameVariables.shoe.count().toString())

        gameVariables.playerHands.forEach {
            Log.i("C1", it.firstCard)
            Log.i("C2", it.secondCard)
        }

        val b = findViewById<Button>(R.id.test_button)
        b.setOnClickListener {
            gameVariables.doDrawHands()
            gameVariables.updateRunningCount()
        }

        val testRV = findViewById<RecyclerView>(R.id.player_hands_rv)
        val testList = gameVariables.playerHands
        val testAdapter = HandsAdapter(this, testList)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        testRV.adapter = testAdapter
        testRV.layoutManager = linearLayoutManager


    }
}