package com.example.wps.events.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wps.repositories.room.daos.EventDAO
import com.example.wps.repositories.room.repository.EventRepository
import com.example.wps.repositories.room.repository.PeopleRepository
import com.example.wps.util.ValidationUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit

class EventBottomSheetViewModel : ViewModel() {

    private lateinit var eventRepo : EventRepository
    private lateinit var peopleRepo : PeopleRepository

    private lateinit var postNewParticipantResult : MutableLiveData<Boolean>

    fun loadRepositories(eventDAO: EventDAO, retrofit: Retrofit) {
        eventRepo = EventRepository(eventDAO, retrofit)
        peopleRepo = PeopleRepository(eventDAO, retrofit)
    }

    fun getPostNewParticipantResult() : LiveData<Boolean> {
        postNewParticipantResult = MutableLiveData(false)
        return postNewParticipantResult
    }

    fun validateFields(name: String, email: String) : String {
        if (name.isEmpty()) {
            return ValidationUtil.NAME_FIELD_IS_EMPTY
        }

        if (email.isEmpty()) {
            return ValidationUtil.EMAIL_FIELD_IS_EMPTY
        }

        if (!ValidationUtil.isEmailValid(email)) {
            return ValidationUtil.EMAIL_FIELD_IS_INVALID
        }

        return ValidationUtil.FIELDS_IS_VALID
    }

    fun addParticipant(name: String, email: String, eventUid: String) {
        viewModelScope.launch(Dispatchers.IO) {
            postNewParticipantResult.postValue(peopleRepo.postNewParticipant(name, email, eventUid))
        }
    }
}