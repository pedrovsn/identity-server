package io.github.pedrovsn.identity.domain

interface UserService {

    fun createUser(user: User): User

    fun loadByEmail(username: String): User
}