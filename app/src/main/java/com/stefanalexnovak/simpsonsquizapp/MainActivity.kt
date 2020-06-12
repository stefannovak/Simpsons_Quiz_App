package com.stefanalexnovak.simpsonsquizapp

import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var score: Int = 0
    private var questionScore: Int = 1
    private var questionList = mutableListOf<Questions>()
    private var questionCounter = 0
    private lateinit var countDownTimer: CountDownTimer
    private var errorCount = 0
    private var gameStarted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resetGame()

    }

    private fun lostDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage("D'oh! \n\n" +
                "Your score was ${score}\n\n" +
                "Would you like to try again?")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Try Again") {
                dialog, _ -> resetGame()
        }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Main menu") {
                dialog, _ -> endGame()
        }
        alertDialog.show()

    }

    private fun resetGame() {
        gameStarted = true
        score = 0
        questionScore = 1
        errorCount = 0
        questionCounter = 0
        populateData()
        questionList.shuffle()
        initialQuestion()
        ErrorCounterText.text = ""
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

        ScoreCounterText.text = getString(R.string.ScoreCounterText, score.toString())
        QuestionCounterText.text = getString(R.string.QuestionCounterText, questionScore.toString())

        TimerText.text = getString(R.string.timerText, 20)
        timer()
    }

    private fun endGame() {
        //I want this to go to a main menu view
        //but for now just do a toast
        Toast.makeText(this, "Heading to main menu", Toast.LENGTH_LONG).show()
    }

    private fun initialQuestion() {
        QuestionText.text = questionList[questionCounter].question

        val firstFourAnswers = listOf(
            questionList[questionCounter].optionalAnswerA,
            questionList[questionCounter].optionalAnswerB,
            questionList[questionCounter].optionalAnswerC,
            questionList[questionCounter].questionAnswer
        )

        val firstNewList = firstFourAnswers.shuffled()

        QuestionText.text = questionList[questionCounter].question
        AnswerButtonA.text = firstNewList.component1()
        AnswerButtonB.text = firstNewList.component2()
        AnswerButtonC.text = firstNewList.component3()
        AnswerButtonD.text = firstNewList.component4()
    }

    private fun timer() {
        countDownTimer = object : CountDownTimer(20000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var timeLeftInSec = millisUntilFinished / 1000
                TimerText.text = getString(R.string.timerText, timeLeftInSec)
                if (timeLeftInSec == 0.toLong()) {
                    lostDialog()
                }
            }

            override fun onFinish() {
                decrementScore()
            }
        }.start()
    }

    //Add questions to the question list
    private fun populateData() {
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
    }

    /**
     * First checks if the questions have reached the end, which will return the end game screen.
     * then increments the question counter
     * A list of answers for the corresponding question is created, and then randomly shuffed.
     * The button texts take values for the newList.
     * The countdown from the previous question is stopped, and a new one is started.
     */
    private fun nextQuestion() {
        if(questionCounter == questionList.size -1) {
            //This needs to end the quiz
            //Possibly show a new screen, display score, ask to try again
            println("Quiz over")

            questionCounter = 0
            countDownTimer.cancel()
        } else {
            questionCounter += 1

            val fourAnswers = listOf(questionList[questionCounter].optionalAnswerA,
                questionList[questionCounter].optionalAnswerB,
                questionList[questionCounter].optionalAnswerC,
                questionList[questionCounter].questionAnswer)

            val newList = fourAnswers.shuffled()

            QuestionText.text = questionList[questionCounter].question
            AnswerButtonA.text = newList.component1()
            AnswerButtonB.text = newList.component2()
            AnswerButtonC.text = newList.component3()
            AnswerButtonD.text = newList.component4()

            countDownTimer.cancel()
            timer()
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
            errorCount += 1
            val newScoreScore = getString(R.string.ScoreCounterText, score.toString())
            ScoreCounterText.text = newScoreScore
            ErrorCounterText.text = ErrorCounterText.text.toString() + "x"
        } else if (score == 0) {
            errorCount += 1
            ErrorCounterText.text = ErrorCounterText.text.toString() + "x"
        }

        if (errorCount == 3) {
            score += 1
            val newScoreScore = getString(R.string.ScoreCounterText, score.toString())
            ScoreCounterText.text = newScoreScore
            lostDialog()
            countDownTimer.cancel()
        }
    }

    //Questions
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
