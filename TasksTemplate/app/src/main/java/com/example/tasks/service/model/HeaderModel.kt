package com.example.tasks.service.model

import com.google.gson.annotations.SerializedName

class HeaderModel {

    @SerializedName("token")
    var token: String = ""

    var personKey: String = ""

    var name: String = ""

}