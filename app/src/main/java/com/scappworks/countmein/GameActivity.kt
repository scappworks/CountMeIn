package com.scappworks.countmein

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.isDigitsOnly

class GameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val extras = intent.extras
        val playerCount = extras!!.getString("playerCount")!!.toInt()
        val deckCount = extras.getString("deckCount")!!.toInt()
        val shoe:List<String> = createDeck(deckCount)


        val test1 = PlayerHand(shoe[0], shoe[1])
        val test2 = PlayerHand(shoe[2], shoe[3])

        Log.i("TEST", test1.firstCardSuit + test1.secondCardSuit)
        Log.i("TEST", test2.firstCardSuit + test2.secondCardSuit)
    }

    class PlayerHand(firstCard:String, secondCard:String) {
        val firstCardNumber = firstCard.isDigitsOnly()
        val firstCardSuit = firstCard.filter { it.isLetter() }
        val secondCardNumber = secondCard.isDigitsOnly()
        val secondCardSuit = secondCard.filter { it.isLetter() }
    }

    private fun createDeck(deckCount:Int): List<String> {
        val suits = arrayOf("Hearts", "Spades", "Clubs", "Diamonds")
        val faceCards = arrayOf("King", "Queen", "Jack", "Ace")
        val builtDeck: MutableList<String> = mutableListOf()

        for(d in 1..deckCount) {
            suits.forEach {
                // Add all 2 - 10 cards per suit
                for (i in 2..10) {
                    builtDeck.add("$i $it")
                }

                // Add all face cards per suit
                faceCards.forEach { fc ->
                    builtDeck.add("$fc $it")
                }
            }
        }

        return builtDeck.shuffled()
    }
}