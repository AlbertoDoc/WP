package com.example.wps.events.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.wps.databinding.AddPersonLayoutBinding
import com.example.wps.databinding.EventBottomSheetBinding
import com.example.wps.databinding.ParticipantsLayoutBinding
import com.example.wps.events.viewModel.EventBottomSheetViewModel
import com.example.wps.repositories.retrofit.RetrofitClient
import com.example.wps.repositories.room.database.Database
import com.example.wps.repositories.room.database.WPSDatabase
import com.example.wps.repositories.room.entities.Event
import com.example.wps.repositories.room.repository.EventRepository
import com.example.wps.repositories.room.repository.PeopleRepository
import com.example.wps.util.ValidationUtil
import com.google.android.material.bottomsheet.BottomSheetDialog

class EventBottomSheetDialog(context: Context, theme: Int, private val event: Event)
    : BottomSheetDialog(context, theme) {

    private lateinit var binding : EventBottomSheetBinding
    private lateinit var viewModel : EventBottomSheetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EventBottomSheetBinding.inflate(LayoutInflater.from(context))
        viewModel = EventBottomSheetViewModel()
        viewModel.loadRepositories(
            WPSDatabase().getDatabase(context).eventDao(), RetrofitClient().getRetrofitInstance()
        )

        viewModel.getPostNewParticipantResult().observe(this) {
            if (it) {
                Toast.makeText(
                    context, "Participante inserido com sucesso!", Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    context,
                    "Não foi possível inserir o participante, tente novamente mais tarde.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.layoutAddPerson.setOnClickListener {
            showAddPerson()
        }

        binding.layoutParticipants.setOnClickListener {
            showParticipants()
        }

        binding.layoutLocation.setOnClickListener {

        }

        binding.layoutShare.setOnClickListener {

        }

        setContentView(binding.root)
    }

    private fun showAddPerson() {
        val addPersonBinding = AddPersonLayoutBinding.inflate(LayoutInflater.from(context))
        setContentView(addPersonBinding.root)

        addPersonBinding.backArrowImageView.setOnClickListener {
            setContentView(binding.root)
        }

        addPersonBinding.button.setOnClickListener {
            addPersonBinding.nameInputLayout.error = null
            addPersonBinding.emailInputLayout.error = null

            val name = addPersonBinding.nameEditText.text.toString()
            val email = addPersonBinding.emailEditText.text.toString()

            when (viewModel.validateFields(name, email)) {
                ValidationUtil.FIELDS_IS_VALID -> {
                    viewModel.addParticipant(name, email, event.uid)
                }
                ValidationUtil.NAME_FIELD_IS_EMPTY -> {
                    addPersonBinding.nameInputLayout.error = "Preencha o campo Nome."
                }
                ValidationUtil.EMAIL_FIELD_IS_EMPTY -> {
                    addPersonBinding.emailInputLayout.error = "Preencha o campo Email."
                }
                ValidationUtil.EMAIL_FIELD_IS_INVALID -> {
                    addPersonBinding.emailInputLayout.error = "Email inválido."
                }
            }
        }
    }

    fun showParticipants() {
        val participantsBinding = ParticipantsLayoutBinding.inflate(LayoutInflater.from(context))
        setContentView(participantsBinding.root)

        participantsBinding.backArrowImageView.setOnClickListener {
            setContentView(binding.root)
        }
    }
}