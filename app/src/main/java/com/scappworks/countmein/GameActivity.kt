package com.scappworks.countmein

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.text.isDigitsOnly

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Choices from MainActivity
        val extras = intent.extras
        val playerCount = extras!!.getString("playerCount")!!.toInt()
        val deckCount = extras.getString("deckCount")!!.toInt()

        Log.i("BEFORE", (deckCount * 52).toString())
        val gameVariables = GameVariables(playerCount, deckCount)

        Log.i("AFTER", gameVariables.shoe.count().toString())

        gameVariables.playerHands.forEach {
            Log.i("TEST", it.firstCard)
            Log.i("TEST", it.secondCard)
        }
    }
}