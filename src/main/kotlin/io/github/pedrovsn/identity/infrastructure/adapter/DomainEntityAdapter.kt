package io.github.pedrovsn.identity.infrastructure.adapter

interface DomainEntityAdapter<A, B> {

    fun fromDomain(a: A): B
    fun fromEntity(b: B): A
}