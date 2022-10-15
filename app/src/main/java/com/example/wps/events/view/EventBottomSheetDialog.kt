package com.example.wps.events.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.wps.databinding.AddPersonLayoutBinding
import com.example.wps.databinding.EventBottomSheetBinding
import com.example.wps.databinding.ParticipantsLayoutBinding
import com.example.wps.databinding.ShareLayoutBinding
import com.example.wps.events.viewModel.EventBottomSheetViewModel
import com.example.wps.repositories.retrofit.RetrofitClient
import com.example.wps.repositories.room.database.WPSDatabase
import com.example.wps.repositories.room.entities.Event
import com.example.wps.util.ValidationUtil
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.text.SimpleDateFormat
import java.util.*

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
            showLocation()
        }

        binding.layoutShare.setOnClickListener {
            showShare()
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

    private fun showParticipants() {
        val participantsBinding = ParticipantsLayoutBinding.inflate(LayoutInflater.from(context))
        setContentView(participantsBinding.root)

        participantsBinding.backArrowImageView.setOnClickListener {
            setContentView(binding.root)
        }
    }

    private fun showLocation() {
        val gmmIntentUri = Uri.parse("geo:0,0?q="
                + event.latitude
                + ","
                + event.longitude
                + "("
                + event.title
                + ")"
        )

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        mapIntent.resolveActivity(context.packageManager)?.let {
            startActivity(context, mapIntent, null)
        }
    }

    private fun showShare() {
        val shareBinding = ShareLayoutBinding.inflate(LayoutInflater.from(context))
        setContentView(shareBinding.root)

        shareBinding.backArrowImageView.setOnClickListener {
            setContentView(binding.root)
        }

        shareBinding.layoutWpp.setOnClickListener {
            callInviteApp("com.whatsapp")
        }

        shareBinding.layoutMessenger.setOnClickListener {
            callInviteApp("com.facebook.orca")
        }

        shareBinding.layoutTelegram.setOnClickListener {
            callInviteApp("org.telegram.messenger")
        }

        shareBinding.layoutOthers.setOnClickListener {
            callInviteApp(null);
        }
    }

    private fun callInviteApp(inviteApp: String?) {
        val eventDate = Date(event.date)

        val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
        val dateOutput: String = formatter.format(eventDate)

        val eventShareText = "Venha participar do evento " +
                event.title + "\n" +
                "Entrada: R$" + String.format("%.2f", event.price) + "\n" +
                "Data: " + dateOutput

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, eventShareText)
        intent.setPackage(inviteApp)

        if (intent.resolveActivity(context.packageManager) != null) {
            startActivity(context, intent, null)
        } else {
            Toast.makeText(context, "App não instalado.", Toast.LENGTH_SHORT).show()
        }
    }
}