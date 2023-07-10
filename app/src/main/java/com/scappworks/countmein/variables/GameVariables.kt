package com.scappworks.countmein.variables

import android.graphics.drawable.Drawable
import android.media.Image
import android.util.Log
import java.util.Locale

data class GameVariables(val playerCount:Int, var deckCount:Int) {
    var shoe: List<String> = createDeck(deckCount)
    val shoeCountOriginal = shoe.count()
    var remainingDecks = deckCount
    var runningCount = 0
    var trueCount = runningCount / remainingDecks
    var playerHands = drawHands(playerCount, shoe, cardImageArray = arrayOf())
    var finished = false

    class PlayerHand(
        firstCardIn: String, secondCardIn: String
    ) {
        val firstCard = firstCardIn
        var firstCardImage = 0
        val secondCard = secondCardIn
        var secondCardImage = 0
        // The amount that the hand will contribute to the total running count
        var handCount = doHandCount(firstCard, secondCard)

        // function for updating the count for an individual hand
        private fun doHandCount(cardOne: String, cardTwo: String) : Int {
            var currentCount = 0
            val cardsArray = arrayOf(cardOne, cardTwo)
            val minusArray = arrayOf("10", "Jack", "Queen", "King", "Ace")

            cardsArray.forEach {
                for (i in 2..6) {
                    if (it.contains(i.toString())) {
                        currentCount++
                    }
                }

                minusArray.forEach { ten ->
                    if (it.contains(ten)) {
                        currentCount--
                    }
                }
            }

            return currentCount
        }
    }

    // Public function to initiate drawing new hands
    fun doDrawHands(cardImageArray: Array<String>) {
        playerHands = drawHands(playerCount, shoe, cardImageArray)
        updateRunningCount()
    }

    // Public function to initiate updating the running count
    fun doUpdateRunningCount() {
        updateRunningCount()
    }

    // Public function to check if the shoe is finished
    fun checkFinished(): Boolean {
        return this.finished
    }
    private fun updateRunningCount() {
        this.playerHands.forEach {
            this.runningCount += it.handCount
        }

        // Update true count as the running count and remaining decks change
       this.trueCount = this.runningCount / this.remainingDecks
    }

    private fun updateDeckCount() {
        val deckBreakingPoints = mutableListOf<Int>()

        // Creates "tiers" to check if the remaining amount of cards in the shoe are signaling
         // that decks need to be removed so the true count can be correct
        for (i in this.deckCount downTo  0) {
            deckBreakingPoints.add(52 * i)
        }

        deckBreakingPoints.forEach {
            if (deckBreakingPoints.indexOf(it) < deckBreakingPoints.count() - 1) {
                if (this.shoeCountOriginal - (this.shoeCountOriginal - this.shoe.count()) < it) {
                    if (this.shoeCountOriginal - (this.shoeCountOriginal - this.shoe.count()) >
                        deckBreakingPoints[deckBreakingPoints.indexOf(it) + 1]) {

                        if (this.remainingDecks != this.deckCount - deckBreakingPoints.indexOf(it)) {
                            this.remainingDecks = this.deckCount - deckBreakingPoints.indexOf(it)
                        }
                    }
                }
            }
        }
    }

    // Logic for drawing hands
    private fun drawHands(playerCount: Int, shoe: List<String>, cardImageArray: Array<String>): List<PlayerHand> {
        val hands = mutableListOf<PlayerHand>()
        val tempShoe = shoe.toMutableList()
        var cardsTaken = 0
        val shoeFinished = tempShoe.isEmpty() || tempShoe.count() < playerCount * 2

        if (!shoeFinished) {
            for (i in 1..playerCount) {
                if (tempShoe.count() > 1) {
                    // Creates a new hand, adds it to the list,
                    // and removes the 2 used cards from the shoe
                    val hand = PlayerHand(tempShoe[0], tempShoe[1])
                    hands.add(hand)
                    tempShoe.removeAt(0)
                    tempShoe.removeAt(0)
                    cardsTaken += 2
                }
            }
        }

        if (shoeFinished) {
            this.finished = shoeFinished
        }

        // Prevent the overdrawing of hands at the end of the shoe
        if (this.finished) {
            hands.clear()
            hands.add(PlayerHand("DONE", "DONE"))
        }

        this.shoe = tempShoe.toList()

        updateDeckCount()

        return hands.toList()
    }

    private fun determineSuit(cardIn: String) : String {
        var suit = ""

        if (cardIn.contains("sp", ignoreCase = true)) {
            suit = "spades"
        }

        if (cardIn.contains("di", ignoreCase = true)) {
            suit = "diamonds"
        }

        if (cardIn.contains("he", ignoreCase = true)) {
            suit = "hearts"
        }

        if (cardIn.contains("cl", ignoreCase = true)) {
            suit = "clubs"
        }

        return suit
    }

    private fun determineNumber(cardIn: String) : String {
        var number = ""

            number = cardIn.substring(cardIn.indexOf("_"))

        return number
    }


        fun updateHandImages(firstCard: String, secondCard: String, cardImageArray: Array<String>) : List<String> {
            val firstCardSuit = determineSuit(firstCard)
            val secondCardSuit = determineSuit(secondCard)
            val firstCardNumber = determineNumber(firstCard)
            val secondCardNumber = determineNumber(secondCard)

            val cardsOut = mutableListOf<String>()

            cardImageArray.forEach {
                // Gets the suit of the card image that it is currently viewing.
                // Creates the suit name from the file path using substring
                val cardImageSuit = it.substring(0, it.indexOf("_"))
                val cardImageNumber = it.substring(it.indexOf("_"))

                if (cardImageSuit.contains(firstCardSuit) && cardImageNumber.contains(firstCardNumber)) {
                    if (cardsOut.isEmpty()) {
                        cardsOut.add(cardImageSuit + cardImageNumber)
                        Log.d("EMPTY1", cardImageSuit + cardImageNumber)
                    }

                    else {
                        cardsOut.add(0, cardImageSuit + cardImageNumber)
                        Log.d("FIRSTINSERT", cardImageSuit + cardImageNumber)
                    }
                }

                else if (cardImageSuit.contains(secondCardSuit) && cardImageNumber.contains(secondCardNumber)) {
                    cardsOut.add(cardImageSuit + cardImageNumber)
                    Log.d("SECONDINSERT", cardImageSuit + cardImageNumber)
                    }
            }

            cardsOut.forEach {
                Log.i("CARDSOUT", it)
            }

            return cardsOut.toList()
        }

    private fun createDeck(deckCount: Int): List<String> {
        val suits = arrayOf("hearts", "spades", "clubs", "diamonds")
        val faceCards = arrayOf("king", "queen", "jack", "ace")
        val builtDeck: MutableList<String> = mutableListOf()

        for (d in 1..deckCount) {
            suits.forEach {
                // Add all 2 - 10 cards per suit
                for (i in 2..10) {
                    builtDeck.add(it + "_" + i)
                }

                // Add all face cards per suit
                faceCards.forEach { fc ->
                    builtDeck.add(it + "_" + fc)
                }
            }
        }

        return builtDeck.shuffled()
    }
}