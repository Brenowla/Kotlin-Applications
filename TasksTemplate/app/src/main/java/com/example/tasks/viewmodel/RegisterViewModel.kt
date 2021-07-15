package com.example.tasks.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tasks.service.model.HeaderModel
import com.example.tasks.service.constants.TaskConstants
import com.example.tasks.service.listener.APIListener
import com.example.tasks.service.listener.ValidationListener
import com.example.tasks.service.repository.PersonRepository
import com.example.tasks.service.repository.local.SecurityPreferences

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val mPersonRepository = PersonRepository(application)
    private val mSharedPreferences = SecurityPreferences(application)

    private val mCreated = MutableLiveData<ValidationListener>()
    var created: LiveData<ValidationListener> = mCreated


    fun create(name: String, email: String, password: String) {
        mPersonRepository.create(name,email,password, object : APIListener<HeaderModel>{
            override fun onSucess(model: HeaderModel) {
                mSharedPreferences.store(TaskConstants.SHARED.TOKEN_KEY,model.token)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_KEY,model.personKey)
                mSharedPreferences.store(TaskConstants.SHARED.PERSON_NAME,model.name)
                val s =""
                mCreated.value = ValidationListener()
            }

            override fun onFailure(str: String) {
                val s =str
                mCreated.value = ValidationListener(str)
            }

        })
    }

}