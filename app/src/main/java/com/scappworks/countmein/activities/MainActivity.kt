package com.scappworks.countmein.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.core.view.WindowCompat
import com.scappworks.countmein.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val runningCountFormula = findViewById<TextView>(R.id.running_count_formula)
        val runningCountFormulaArray = resources.getStringArray(R.array.running_count_formula)
        val playerCountList = resources.getStringArray(R.array.player_count_array).toList()
        val playerCounterAdapter = ArrayAdapter(this, R.layout.player_count, playerCountList)
        val playerCountSpinner = findViewById<Spinner>(R.id.player_spinner)
        val deckCountList = resources.getStringArray(R.array.deck_count_array)
        val deckCountAdapter = ArrayAdapter(this, R.layout.player_count, deckCountList)
        val deckCountSpinner = findViewById<Spinner>(R.id.deck_spinner)
        val playButton = findViewById<Button>(R.id.play_button)
        val gameIntent = Intent(this, GameActivity::class.java)
        playerCountSpinner.adapter = playerCounterAdapter
        playerCountSpinner.setSelection(0)
        deckCountSpinner.adapter = deckCountAdapter
        deckCountSpinner.setSelection(deckCountList.size - 1)

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