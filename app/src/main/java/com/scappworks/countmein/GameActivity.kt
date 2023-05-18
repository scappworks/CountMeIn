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
        val shoe:MutableList<Any> = createDeck()

        Log.d("DECK", shoe.toString())
    }

    private fun createDeck(): MutableList<Any> {
        val builtDeck: MutableList<Any> = arrayListOf()
        for(i in 1..52) {
            if (i == 1 || i == 11) {
                builtDeck.add("A")
            }
            else if(i >= 10) {
                builtDeck.add(i)
            }

        }

        return builtDeck
    }
}