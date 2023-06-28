package com.scappworks.countmein.variables

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
        firstCardIn: String, secondCardIn: String,
        firstCardImageIn: Image?, secondCardImageIn: Image?
    ) {
        val firstCard = firstCardIn
        var firstcardImage = firstCardImageIn
        val secondCard = secondCardIn
        var secondCardImage = secondCardImageIn
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
                    val hand = PlayerHand(tempShoe[0], tempShoe[1], null, null)
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
            hands.add(PlayerHand("DONE", "DONE", null, null))
        }

        this.shoe = tempShoe.toList()

        updateDeckCount()

        return hands.toList()
    }

    fun updateHandImages(cardIn: String, cardImageArray: Array<String>) : String {
        var found = false
        var suit = ""
        var number = ""
        var cardOut = ""

        Log.i("IN", cardIn)

        if (cardIn.contains("sp", ignoreCase = true)) {
            suit = "spades"
            number = cardIn.substring(0, cardIn.indexOf(" "))
        }

        if (cardIn.contains("di", ignoreCase = true)) {
            suit = "diamonds"
            number = cardIn.substring(0, cardIn.indexOf(" "))
        }

        if (cardIn.contains("he", ignoreCase = true)) {
            suit = "hearts"
            number = cardIn.substring(0, cardIn.indexOf(" "))
        }

        if (cardIn.contains("cl", ignoreCase = true)) {
            suit = "clubs"
            number = cardIn.substring(0, cardIn.indexOf(" "))
        }

        cardImageArray.forEach {
            if (!found){
                // Gets the suit of the card image that it is currently viewing.
                // Creates the suit name from the file path using substring
                val cardImageSuit = it.substring(it.indexOf("e/") + 2, it.indexOf("_"))
                val cardImageNumber = it.substring(it.indexOf("_") + 1, it.indexOf("."))

                Log.i("CARDSUIT", cardImageSuit + " " + suit)
                Log.i("CARDNUMBER", cardImageNumber + " " + number)

                if (cardImageSuit == suit.lowercase() && cardImageNumber == number.lowercase()) {
                    found = true
                    Log.i("FOUND", cardImageNumber + cardImageSuit)
                    cardOut = cardImageSuit + "_" + cardImageNumber

                }
            }
        }

        Log.i("CARDOUT", cardOut)

        return cardOut
    }

    private fun createDeck(deckCount: Int): List<String> {
        val suits = arrayOf("Hearts", "Spades", "Clubs", "Diamonds")
        val faceCards = arrayOf("King", "Queen", "Jack", "Ace")
        val builtDeck: MutableList<String> = mutableListOf()

        for (d in 1..deckCount) {
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