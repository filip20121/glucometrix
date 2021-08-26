package com.example.glucometrix.dataClass

data class User(
        private var id: Int,
        private var login: String,
        private var password: String
){
    fun getId(): Int {
        return id
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getLogin():String {
        return login
    }

    fun setLogin(login: String) {
        this.login = login
    }

    fun getPassword():String {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }


}