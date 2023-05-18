package com.scappworks.countmein

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val extras = intent.extras
        val playerCount = extras!!.getString("playerCount")!!.toInt()
        val deckCount = extras.getString("deckCount")!!.toInt()
        val shoe = createDeck()
    }

    private fun createDeck(): List<Any> {
        val suits = arrayOf("Hearts", "Spades", "Clubs", "Diamonds")
        val faceCards = arrayOf("King", "Queen", "Jack", "Ace")
        val builtDeck: MutableList<Any> = mutableListOf()

        suits.forEach {
            // Add all 2 - 10 cards per suit
            for (i in 2..10) {
                builtDeck.add("$i $it")
            }

            // Add all face cards per suit
            faceCards.forEach {fc ->
                builtDeck.add("$fc $it")
            }
        }

        return builtDeck.shuffled()
    }
}