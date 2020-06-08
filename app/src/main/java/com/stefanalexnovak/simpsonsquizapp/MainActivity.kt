package com.stefanalexnovak.simpsonsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        println("Question #1: ${questions[1]}") //Testing
        println(questions.toString()) //Testing
    }

    val questions = listOf(
        "What colour was Chief Wiggum's hair originally?",
        "How many members are in the main Simpsons family?",
        "Marge's sisters are Patty and ?",
        "In what year did the Simpsons first season premiere?",
        "Which was the first Sideshow Bob episode?",
        "What football team is gifted to Homer by Hank Scorpio?",
        "The towns baseball team are called the Springfield ?",
        "Who founded the town of Springfield?",
        "How old are Bart and Lisa?",
        "What is the name of the clown on Channel 6?",
        "Which crank call of Bart's actually answers the phone?")
}
