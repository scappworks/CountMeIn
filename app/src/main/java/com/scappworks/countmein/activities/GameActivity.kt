package com.scappworks.countmein.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.scappworks.countmein.R
import com.scappworks.countmein.recyclerview.HandsRecyclerView
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

        //val handsRecyclerView = HandsRecyclerView(gameVariables.playerHandsArray)

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
    }
}