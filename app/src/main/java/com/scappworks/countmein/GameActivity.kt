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
        // Variables for this activity
        var shoe:List<String> = createDeck(deckCount)
        var remainingShoe = shoe
        var remainingDecks = deckCount
        var remainingCardCount = remainingShoe.count()
        var runningCount = 0
        var trueCount = runningCount / remainingDecks
        var playerHands = drawHands(playerCount, remainingShoe)

        playerHands.forEach {
            Log.i("BIGTEST", it.firstCard)
            Log.i("BIGTEST2", it.secondCard)
        }
    }

    class PlayerHand(firstCardIn:String, secondCardIn:String) {
        val firstCard = firstCardIn
        val secondCard = secondCardIn
    }

    private fun subtractShoe(playerCount:Int, shoeCount:Int): Int {
        return shoeCount - (playerCount * 2)
    }

    private fun drawHands(playerCount:Int, shoe:List<String>): List<PlayerHand> {
        val hands = mutableListOf<PlayerHand>()
        val tempShoe = shoe.toMutableList()

        for(i in 1..playerCount) {
            val hand = PlayerHand(tempShoe[0], tempShoe[1])
            hands.add(hand)
            tempShoe.removeAt(0)
            tempShoe.removeAt(0)
        }

        return hands.toList()
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