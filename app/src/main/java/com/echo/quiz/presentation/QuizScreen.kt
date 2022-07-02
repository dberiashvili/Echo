package com.echo.quiz.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.echo.R
import com.echo.common.base.BaseFragment
import com.echo.common.base.utils.showDialog
import com.echo.common.model.Resource
import com.echo.databinding.QuizScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class QuizScreen : BaseFragment<QuizScreenBinding, QuizViewModel>() {
    override val viewModel: QuizViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getQuestions(1)
        lifecycleScope.launchWhenStarted {
            viewModel.questions.collect {
                when(it){
                    is Resource.Success -> {
                        it.data?.let { questions ->
                            binding.quizQuestion.text = questions[0].question
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }


    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): QuizScreenBinding {
        return QuizScreenBinding.inflate(layoutInflater)
    }
}