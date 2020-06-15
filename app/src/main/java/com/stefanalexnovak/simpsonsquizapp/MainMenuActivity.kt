package com.stefanalexnovak.simpsonsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_menu.*

class MainMenuActivity : AppCompatActivity() {

    companion object {
        const val INTENT_KEY = "quiz"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        supportActionBar!!.hide()

        playButton.setOnClickListener{
            goToQuiz()
        }
    }

    private fun goToQuiz() {
        val quiz = Intent(this, MainActivity::class.java)
//        quiz.putExtra(INTENT_KEY)
        startActivity(quiz)
    }
}