package io.github.pedrovsn.identity.domain

import java.lang.RuntimeException

class UserNotFoundException(message: String?) : RuntimeException(message) {
}