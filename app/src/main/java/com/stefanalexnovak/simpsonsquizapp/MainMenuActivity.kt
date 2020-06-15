package com.stefanalexnovak.simpsonsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.util.Linkify
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.main_menu.*

class MainMenuActivity : AppCompatActivity() {

    companion object {
        var difficulty = Difficulty.EASY
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
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

    private fun goToQuiz() {
        val quiz = Intent(this, MainActivity::class.java)
        startActivity(quiz)
    }

    private fun aboutDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage("Hi! I hope you're enjoying this little app. This was the first app I created.\n\n" +
                "If you're interested in getting to know me or contacting me, here is a list of my socials:\n\n" +
                "Github\n\n" +
                "LinkedIn\n\n" +
                "Twitter")
        alertDialog.show()
    }
}