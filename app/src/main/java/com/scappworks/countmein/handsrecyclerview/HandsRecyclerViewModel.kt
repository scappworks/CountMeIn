package com.scappworks.countmein.handsrecyclerview

class HandsRecyclerViewModel (
    private var firstCardNumber: String, private var firstCardSuite: String,
    private var secondCardNumber: String, private var secondCardSuite: String,
    private var runningCount: Int) {

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

    fun getSecondCardNumber(): String {
        return secondCardNumber
    }

    fun getSecondCardSuite(): String {
        return secondCardSuite
    }

    fun setSecondCardNumber(secondCardNumber: String) {
        this.secondCardNumber = secondCardNumber
    }

    fun setSecondCardSuite(secondCardSuite: String) {
        this.secondCardSuite = secondCardSuite
    }

    fun getRunningCount(): Int {
        return runningCount
    }

    fun setRunningCount(runningCount: Int) {
        this.runningCount = runningCount
    }
}