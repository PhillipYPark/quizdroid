package edu.washington.pypark.quizdroid

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class AnswerFragment : Fragment() {

    private var quizNumber: Int = 0
    private var currentQuestion: Int = 0
    private var correctTotal : Int = 0
    private var questionTotal : Int = 0
    private var answer : String = ""
    private var correctAnswer: String  = ""

    companion object {
        fun newInstance(quizNumber: Int, currentQuestion: Int, correctTotal: Int, questionTotal: Int, answer: String, correctAnswer: String) = AnswerFragment().apply {
            arguments = Bundle().apply {
                putInt("quizNumber", quizNumber)
                putInt("currentQuestion", currentQuestion)
                putInt("correctTotal", correctTotal)
                putInt("questionTotal", questionTotal)
                putString("answer", answer)
                putString("correctAnswer", correctAnswer)
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        arguments?.getInt("quizNumber")?.let { this.quizNumber = it }
        arguments?.getInt("currentQuestion")?.let { this.currentQuestion = it }
        arguments?.getInt("correctTotal")?.let { this.correctTotal = it }
        arguments?.getInt("questionTotal")?.let { this.questionTotal = it }
        arguments?.getString("answer")?.let { this.answer = it }
        arguments?.getString("correctAnswer")?.let { this.correctAnswer = it }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val main = inflater.inflate(R.layout.answer_fragment, container, false)

        val done = QuestionList.questionList(this.quizNumber)!!.size - this.questionTotal

        val userAnswerView = main.findViewById<TextView>(R.id.userAnswer)
        val correctAnswerView = main.findViewById<TextView>(R.id.userCorrectAnswer)
        val scoreView = main.findViewById<TextView>(R.id.quizScore)
        val nextButton = main.findViewById<Button>(R.id.next_button)

        userAnswerView.text = this.answer
        correctAnswerView.text = this.correctAnswer
        if (done == 0) {
            nextButton.text = "Finish"
        } else {
            nextButton.text = "Next"
        }

        scoreView.text = "You have " + this.correctTotal.toString() +  " out of " + this.questionTotal +  " correct"
        nextButton.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (done == 0) {
                    startActivity(Intent(context, MainActivity::class.java))
                } else {
                    val questionFragment : QuestionFragment = QuestionFragment.newInstance(quizNumber, currentQuestion.inc(), correctTotal, questionTotal)
                    val fragmentListener : FragmentChangeListener = activity as FragmentChangeListener
                    fragmentListener.replaceFragment(questionFragment)
                }
            }
        })
        return main
    }
}