package com.echo.quiz.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.echo.common.base.BaseFragment
import com.echo.common.base.utils.hide
import com.echo.common.base.utils.show
import com.echo.common.base.utils.showDialog
import com.echo.common.base.utils.toSeconds
import com.echo.common.model.Resource
import com.echo.databinding.QuizScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class QuizScreen : BaseFragment<QuizScreenBinding, QuizViewModel>() {
    override val viewModel: QuizViewModel by viewModels()



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenStarted {
            viewModel.getQuestions(1)
        }

        lifecycleScope.launchWhenStarted {

            viewModel.questions.collect {
                when(it){
                    is Resource.Success -> {

                        it.data?.let { questions ->
                            viewModel.index.observe(viewLifecycleOwner) {
                                binding.quizQuestion.text = questions[viewModel.index.value!!].question
                                binding.questionIndexTv.text = "შეკითხვა ${viewModel.index.value!!+1}/${questions.size}"
                                binding.option1Text.text = questions[viewModel.index.value!!].answers[0].answer
                                binding.option2Text.text = questions[viewModel.index.value!!].answers[1].answer
                                binding.option3Text.text = questions[viewModel.index.value!!].answers[2].answer
                                binding.option4Text.text = questions[viewModel.index.value!!].answers[3].answer

                            }

                            binding.option1Bg.setOnClickListener {
                                lifecycleScope.launch {
                                    delay(2000)
                                    viewModel.nextQuestion()
                                }


                            }

                        }

                        binding.progressCircular.hide()
                        viewModel.timer.start().collect { time ->
                            binding.timerText.text = time.toSeconds()
                        }



                    }
                    is Resource.Error -> {
                        binding.progressCircular.hide()
                    }
                    else -> {
                        binding.progressCircular.show()
                    }
                }
            }


        }

        lifecycleScope.launchWhenStarted {
            viewModel.isFinished.collectLatest {
                if (it) {
                    showDialog("morcha kino", "wait saxlshi")
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