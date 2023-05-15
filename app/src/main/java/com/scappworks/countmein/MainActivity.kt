package com.scappworks.countmein

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val runningCountFormula = findViewById<TextView>(R.id.running_count_formula)
        val runningCountFormulaArray = resources.getStringArray(R.array.running_count_formula)
        val playerCountList = resources.getStringArray(R.array.player_count_array).toList()
        val playerCounterAdapter = ArrayAdapter(this, R.layout.player_count, playerCountList)
        val playerCountSpinner = findViewById<Spinner>(R.id.player_spinner)
        playerCountSpinner.adapter = playerCounterAdapter
        playerCountSpinner.setSelection(0)
        val deckCountList = resources.getStringArray(R.array.deck_count_array)
        val deckCountAdapter = ArrayAdapter(this, R.layout.player_count, deckCountList)
        val deckCountSpinner = findViewById<Spinner>(R.id.deck_spinner)
        deckCountSpinner.adapter = deckCountAdapter
        deckCountSpinner.setSelection(deckCountList.size - 1)
        val playButton = findViewById<Button>(R.id.play_button)
        val gameIntent = Intent(this, GameActivity::class.java)


        runningCountFormulaArray.forEachIndexed {index, it ->
            if (index == 0) {
                runningCountFormula.text = ""
                runningCountFormula.append("\n" + it)
            }
            else {
                runningCountFormula.append("\n" + it)
            }
        }

        playButton.setOnClickListener {
            val extras = Bundle()
            val playerCountInt = playerCountSpinner.selectedItem.toString()
            val deckCount = deckCountSpinner.selectedItem.toString()
            extras.putString("playerCount", playerCountInt)
            extras.putString("deckCount", deckCount)
            gameIntent.putExtras(extras)
            startActivity(gameIntent)
        }
    }
}