package com.echo.quiz.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.echo.common.QuizTimer
import com.echo.common.QuizTimer.Companion.update
import com.echo.common.base.BaseFragment
import com.echo.common.base.utils.hide
import com.echo.common.base.utils.show
import com.echo.common.base.utils.showDialog
import com.echo.common.base.utils.toSeconds
import com.echo.common.model.Resource
import com.echo.databinding.QuizScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext

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
                            binding.questionIndexTv.text = "შეკითხვა 1/${it.data.size}"
                        }
                        binding.progressCircular.hide()
                        update().collect {
                            withContext(Dispatchers.Main){
                                binding.timerText.text = it.toSeconds()
                            }
                        }




                    }
                    is Resource.Error -> {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                        binding.progressCircular.hide()
                    }
                    else -> {
                        binding.progressCircular.show()
                    }
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