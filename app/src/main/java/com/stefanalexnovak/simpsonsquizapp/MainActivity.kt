package com.stefanalexnovak.simpsonsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private var questionScore: Int = 1
    private val correctAnswer: Boolean = false
    private var questionList = mutableListOf<Questions>()
    private val questionListSize = questionList.size
    private var questionCounter = 0



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

        //Testing populating the questions and answers.
        QuestionText.text = questionList[questionCounter].question
        AnswerButtonA.text = questionList[questionCounter].optionalAnswerA
        AnswerButtonB.text = questionList[questionCounter].optionalAnswerB
        AnswerButtonC.text = questionList[questionCounter].optionalAnswerC
        AnswerButtonD.text = questionList[questionCounter].questionAnswer

        AnswerButtonA.setOnClickListener{view ->
            if (AnswerButtonA.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }

        AnswerButtonB.setOnClickListener{view ->
            if (AnswerButtonB.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }

        AnswerButtonC.setOnClickListener{view ->
            if (AnswerButtonC.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }

        AnswerButtonD.setOnClickListener{view ->
            if (AnswerButtonD.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }


        //Score and Question Counter stuff
        ScoreCounterText.text = getString(R.string.ScoreCounterText, score.toString())
        QuestionCounterText.text = getString(R.string.QuestionCounterText, questionScore.toString())

    }

    private fun populateData() {

    }

    /**
     * First checks if the questions have reached the end, which will return the end game screen.
     * then increments the question counter and shows the next question and answer.
     */
    private fun nextQuestion() {
        if(questionCounter == questionList.size -1) {
            //This needs to end the quiz
            //Possibly show a new screen, display score, ask to try again
            println("Quiz over")
            questionCounter = 0
        } else {
            questionCounter += 1

            QuestionText.text = questionList[questionCounter].question
            AnswerButtonA.text = questionList[questionCounter].optionalAnswerA
            AnswerButtonB.text = questionList[questionCounter].optionalAnswerB
            AnswerButtonC.text = questionList[questionCounter].optionalAnswerC
            AnswerButtonD.text = questionList[questionCounter].questionAnswer
        }
    }

    /**
     * Adds 1 to both question and score counters. Used in the correct answer button onclicklistener
     */
    private fun incrementScore() {
        score += 1
        questionScore += 1
        val newScoreScore = getString(R.string.ScoreCounterText, score.toString())
        val newQuestionScore = getString(R.string.QuestionCounterText, questionScore.toString())
        ScoreCounterText.text = newScoreScore
        QuestionCounterText.text = newQuestionScore
    }

    /**
     * subtracts a point from the score, but keeps it at 0 if it's already 0
     */
    private fun decrementScore() {
        if(score != 0) {
            score -= 1
            val newScoreScore = getString(R.string.ScoreCounterText, score.toString())
            ScoreCounterText.text = newScoreScore
        }
    }

    private val question1 = Questions(1, "What colour was Chief Wiggum's hair originally?",
        "Blue", "Grey", "Green", "Black")
    private val question2 = Questions(2, "How many members are in the main Simpsons family?",
        "6","4", "3","5")
    private val question3 = Questions(3, "Marge's sisters are Patty and ?",
        "Edna", "Maggie", "Margeret", "Selma")
    private val question4 = Questions(4, "In what year did the Simpsons first season premiere?",
        "1990","1991","1992","1989")
    private val question5 = Questions(5, "Which was the first Sideshow Bob centered episode?",
        "The Telltale Head", "Cape Feare", "Sideshow Bob's Last Gleaming", "Krusty Gets Busted")
    private val question6 = Questions(6, "What football team is gifted to Homer by Hank Scorpio?",
        "Dallas Cowboys", "Green Bay Packers", "New England Patriots", "Denver Broncos")
    private val question7 = Questions(7, "The towns baseball team are called the Springfield ?",
        "Dodgers", "Nucleus","Jets", "Isotopes")
    private val question8 = Questions(8, "Who founded the town of Springfield?",
        "Abraham Simpson", "Springfield Manhatten", "Cornelius Quimby", "Jebadiah Springfield")
    private val question9 = Questions(9, "How old are Bart and Lisa?",
        "9 and 10", "10 and 12", "7 and 9", "10 and 8")
    private val question10 = Questions(10, "What is the name of the clown on Channel 6?",
        "Handsome Pete", "Krumpet the Klown", "Gabbo", "Krusty the Clown")
    private val question11 = Questions(11, "Which crank call of Bart's actually answers the phone?",
        "Amada Huggenkiss", "B.O Problem", "I.P Freely", "Hugh Jazz")


}
