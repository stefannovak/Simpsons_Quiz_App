package com.stefanalexnovak.simpsonsquizapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.main_menu.*

class MainMenuActivity : AppCompatActivity() {

    /**
     * Needed a companion object to set up the difficulty levels.
     */
    companion object {
        var difficulty = Difficulty.EASY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        //Menu bar stuff
        supportActionBar!!.hide()

        playButton.setOnClickListener{
            goToQuiz()
        }

        aboutButton.setOnClickListener{
            aboutDialog()
        }

        radio_easy.setOnClickListener{
            difficulty = Difficulty.EASY
        }

        radio_medium.setOnClickListener{
            difficulty = Difficulty.MEDIUM
        }

        radio_hard.setOnClickListener{
            difficulty = Difficulty.HARD
        }

        //Set the default difficulty to easy
        difficultyRadioGroup.check(radio_easy.id)
    }

    /**
     * Uses an intent called 'quiz' to navigate to the MainActivity class
     * which contains the quiz.
     */
    private fun goToQuiz() {
        val quiz = Intent(this, MainActivity::class.java)
        startActivity(quiz)
    }

    /**
     * A standard about dialog.
     * I'd improve this by making the handles clickable,
     * but I could not find a way at the moment (17/06/20)
     */
    private fun aboutDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage("Hi! I hope you're enjoying this little app. This was the first app I created.\n\n" +
                "If you're interested in getting to know me or contacting me:\n\n" +
                "Twitter - @stefannovak96\n\nGithub - 'stefannovak'\n\n" +
                "There are about 30 questions in the quiz, and they're mostly from seasons 1-10.\n\nI hope you enjoy!")
        alertDialog.show()
    }
}