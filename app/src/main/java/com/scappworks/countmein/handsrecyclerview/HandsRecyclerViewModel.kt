package com.scappworks.countmein.handsrecyclerview

class HandsRecyclerViewModel (
    private var firstCardNumber: String, private var firstCardSuite: String,
    private var secondCard: String, private var runningCount: Int) {

    // Getter and Setter
    fun getFirstCardNumber(): String {
        return firstCardSuite
    }

    fun getFirstCardSuite(): String {
        return firstCardSuite
    }

    fun setFirstCardNumber(firstCardNumber: String) {
        this.firstCardNumber = firstCardNumber
    }

    fun setFirstCardSuite(firstCardSuite: String) {
        this.firstCardNumber = firstCardSuite
    }

    fun getSecondCard(): String {
        return secondCard
    }

    fun setSecondCard(secondCard: String) {
        this.secondCard = secondCard
    }

    fun getRunningCount(): Int {
        return runningCount
    }

    fun setRunningCount(runningCount: Int) {
        this.runningCount = runningCount
    }
}