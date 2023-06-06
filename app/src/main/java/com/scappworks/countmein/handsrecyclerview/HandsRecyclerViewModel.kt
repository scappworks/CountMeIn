package com.scappworks.countmein.handsrecyclerview

class HandsRecyclerViewModel (private var firstCard: String, private var secondCard: String, private var runningCount: Int) {

    // Getter and Setter
    fun getFirstCard(): String {
        return firstCard
    }

    fun setFirstCard(firstCard: String) {
        this.firstCard = firstCard
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