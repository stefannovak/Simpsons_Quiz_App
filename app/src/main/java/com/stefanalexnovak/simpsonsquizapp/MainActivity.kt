package com.stefanalexnovak.simpsonsquizapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Vibrator
import android.view.Menu
import android.view.MenuItem
import android.view.animation.AnimationUtils
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
    private var level: String = "Homer"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Menu bar stuff
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.title = ""

        populateData()

        resetGame()

    }

    /**
     * Overrides the default menu to show our menu
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    /**
     * provides the way for the back button to go back to the menu
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.backButton) {
            menuDialog()
        }
        return true
    }

    /**
     * A dialog that does not stop the timer of the game, and is cancellable from
     * clicking outside of it.
     */
    private fun menuDialog() {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setMessage("Are you sure you want to return to the main menu?")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Go Back") { _, _ -> goToMenu() }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No") { _, _ -> alertDialog.dismiss() }
        alertDialog.show()
    }

    /**
     * Uses an alert dialog to congratulate the user for winning the game.
     * The score is presented, which is used in determineLevel to calculate
     * a fun name for the user to see.
     */
    private fun winDialog() {
        determineLevel()
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setMessage("Congratulations!\n\n" +
                "Your score was ${score}\n\n" +
                "You've achieved the rank of " + level +
                "!\n\nWould you like to try again? Or perhaps try a harder difficulty?\n")
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Play Again") { _, _ -> resetGame() }
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Main menu") { _, _ -> goToMenu() }
        alertDialog.show()
    }

    /**
     * Works very similarly to the win dialog.
     */
    private fun lostDialog() {
        determineLevel()
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setCanceledOnTouchOutside(false)
        alertDialog.setMessage("D'oh! \n\n" +
                "Your score was ${score}\n\n" +
                "You've achieved the rank of " + level +
                "!\n\nWould you like to try again?")
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Try Again") { _, _ -> resetGame() }
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Main menu") { _, _ -> goToMenu() }
        alertDialog.show()

    }

    /**
     * Uses a when control flow to give a name to a certain amount of score.
     */
    private fun determineLevel() {
        level = when(score) {
            in 0..5 -> "Homer"
            in 6..15 -> "Martin Prince"
            in 16..25 -> "Lisa"
            else -> "Professor Frink! You're a Simpsons legend"
        }
    }

    /**
     * The big function of the game. A number of variables are initialised.
     * The onClickListeners are set up.
     * The score and timers are initialised.
     */
    private fun resetGame() {
        score = 0
        questionScore = 1
        errorCount = 0
        questionCounter = 0
        questionList.shuffle()
        initialQuestion()
        ErrorCounterText.text = ""
        AnswerButtonA.setOnClickListener{
            if (AnswerButtonA.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }

        AnswerButtonB.setOnClickListener{
            if (AnswerButtonB.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }

        AnswerButtonC.setOnClickListener{
            if (AnswerButtonC.text == questionList[questionCounter].questionAnswer) {
                incrementScore()
                nextQuestion()
            } else {
                decrementScore()
            }
        }

        AnswerButtonD.setOnClickListener{
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

    /**
     * Sets up the first questions and answers, before nextQuestion()
     * takes care of the rest. To randomise the answers, the answers
     * are put into a list, which is then shuffled.
     */
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

    /**
     * Checks the difficulty from the MainMenu radio group. Sets the timer based on difficulty.
     */
    private fun timer() {
        var timeInMillis: Long = 20000

        if (MainMenuActivity.difficulty == Difficulty.EASY) {
            timeInMillis = 20000
        } else if(MainMenuActivity.difficulty == Difficulty.MEDIUM) {
            timeInMillis = 15000
        } else if (MainMenuActivity.difficulty == Difficulty.HARD) {
            timeInMillis = 10000
        }

        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val timeLeftInSec = millisUntilFinished / 1000
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
            countDownTimer.cancel()
            winDialog()
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
     * subtracts a point from the score, but keeps it at 0 if it's already 0.
     * Animations and vibrations occur when a wrong answer is made.
     */
    private fun decrementScore() {
        var vibrator: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if(score != 0) {
            score -= 1
            errorCount += 1
            val newScoreScore = getString(R.string.ScoreCounterText, score.toString())
            ScoreCounterText.text = newScoreScore
            vibrator.vibrate(200)
            ErrorCounterText.text = ErrorCounterText.text.toString() + "X        "
            shakeAnimation()
        } else if (score == 0) {
            errorCount += 1
            vibrator.vibrate(200)
            ErrorCounterText.text = ErrorCounterText.text.toString() + "X        "
            shakeAnimation()
        }

        if (errorCount == 3) {
            val newScoreScore = getString(R.string.ScoreCounterText, score.toString())
            ScoreCounterText.text = newScoreScore
            lostDialog()
            countDownTimer.cancel()
        }
    }

    /**
     * Shakes the screen when a wrong answer is selected
     */
    private fun shakeAnimation() {
        ErrorCounterText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        QuestionText.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        AnswerButtonA.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        AnswerButtonB.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        AnswerButtonC.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
        AnswerButtonD.startAnimation(AnimationUtils.loadAnimation(this, R.anim.shake))
    }

    /**
     * Go to the main menu
     */
    private fun goToMenu() {
        val menu = Intent(this, MainMenuActivity::class.java)
        startActivity(menu)
    }

    /**
     * Questions
     */
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
    private val question12 = Questions(12, "What is Fat Tony's full name?", "Antonio DiMarco", "Tony Salerno",
        "Joey DiMarcia", "Marion Anthony D'Amico")
    private val question13 = Questions(13, "Which Springfield Elementary teacher is featured on family board games?", "Principle Skinner",
    "Elizabeth Hoover", "Dewey Largo", "Edna Krabappel")
    private val question14 = Questions(14, "What is Barts middle name?", "Joey", "J", "Jay",
        "Jojo")
    private val question15 = Questions(15, "What was the name of Lisa's teacher crush?", "Mr Weinstein", "Mr Frank",
    "Mr Abelman", "Mr Bergstrom")
//    private val question16 = Questions(16)
//    private val question17 = Questions(17)
//    private val question18 = Questions(18)
//    private val question19 = Questions(19)
//    private val question20 = Questions(20)
//    private val question21 = Questions(21)
//    private val question22 = Questions(22)
//    private val question23 = Questions(23)
//    private val question24 = Questions(24)
//    private val question25 = Questions(25)
//    private val question26 = Questions(26)
//    private val question27 = Questions(27)
//    private val question28 = Questions(28)
//    private val question29 = Questions(29)
//    private val question30 = Questions(30)

}