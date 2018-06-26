package com.example.fa.billspliter.data.model

class UserData {
    var name: String? = null
    var email: String? = null
    var url: String? = null

    constructor(name: String?, email: String?, url: String?) {
        this.name = name
        this.email = email
        this.url = url
    }
}