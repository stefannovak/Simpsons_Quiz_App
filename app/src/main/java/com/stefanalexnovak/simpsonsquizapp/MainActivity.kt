package com.stefanalexnovak.simpsonsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var score: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Add questions to the question list
        questionList.add(question1)
        questionList.add(question2)
        questionList.add(question3)
        questionList.add(question4)
        questionList.add(question5)
        questionList.add(question6)
        questionList.add(question7)
        questionList.add(question8)
        questionList.add(question9)
        questionList.add(question10)
        questionList.add(question11)

//        var startScore = 0
//        var startQuestionCounter = 0
//        QuestionCounterText.text = getString(R.string.QuestionCounterText, startQuestionCounter)
//        ScoreCounterText.text = getString(R.string.ScoreCounterText, startScore)
    }

    private fun incrementScore() {

        score += 1
//        val newScore = getString(R.string.ScoreCounterText, score)
//        ScoreCounterText.text = newScore
    }

    var questionList = mutableListOf<Questions>()

    val question1 = Questions(1, "What colour was Chief Wiggum's hair originally?",
        "Blue", "Grey", "Green", "Black")
    val question2 = Questions(2, "How many members are in the main Simpsons family?",
        "6","4", "3","5")
    val question3 = Questions(3, "Marge's sisters are Patty and ?",
        "Edna", "Maggie", "Margeret", "Selma")
    val question4 = Questions(4, "In what year did the Simpsons first season premiere?",
        "1990","1991","1992","1989")
    val question5 = Questions(5, "Which was the first Sideshow Bob centered episode?",
        "The Telltale Head", "Cape Feare", "Sideshow Bob's Last Gleaming", "Krusty Gets Busted")
    val question6 = Questions(6, "What football team is gifted to Homer by Hank Scorpio?",
        "Dallas Cowboys", "Green Bay Packers", "New England Patriots", "Denver Broncos")
    val question7 = Questions(7, "The towns baseball team are called the Springfield ?",
        "Dodgers", "Nucleus","Jets", "Isotopes")
    val question8 = Questions(8, "Who founded the town of Springfield?",
        "Abraham Simpson", "Springfield Manhatten", "Cornelius Quimby", "Jebadiah Springfield")
    val question9 = Questions(9, "How old are Bart and Lisa?",
        "9 and 10", "10 and 12", "7 and 9", "10 and 8")
    val question10 = Questions(10, "What is the name of the clown on Channel 6?",
        "Handsome Pete", "Krumpet the Klown", "Gabbo", "Krusty the Clown")
    val question11 = Questions(11, "Which crank call of Bart's actually answers the phone?",
        "Amada Huggenkiss", "B.O Problem", "I.P Freely", "Hugh Jazz")

}
