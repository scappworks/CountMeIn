package com.scappworks.countmein

data class GameVariables(val playerCount:Int, val deckCount:Int) {
    var shoe: List<String> = createDeck(deckCount)
    var remainingDecks = deckCount
    var runningCount = 0
    var trueCount = runningCount / remainingDecks
    var playerHands = drawHands(playerCount, shoe)


    class PlayerHand(firstCardIn: String, secondCardIn: String) {
        val firstCard = firstCardIn
        val secondCard = secondCardIn
    }

    private fun drawHands(playerCount: Int, shoe: List<String>): List<PlayerHand> {
        val hands = mutableListOf<PlayerHand>()
        val tempShoe = shoe.toMutableList()
        var cardsTaken = 0

        for (i in 1..playerCount) {
            val hand = PlayerHand(tempShoe[0], tempShoe[1])
            hands.add(hand)
            tempShoe.removeAt(0)
            tempShoe.removeAt(0)
            cardsTaken += 2
        }

        this.shoe = tempShoe.toList()

        return hands.toList()
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