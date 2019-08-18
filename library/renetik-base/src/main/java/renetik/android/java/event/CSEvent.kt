package renetik.android.java.event

import renetik.android.java.event.CSEvent.CSEventRegistration

fun <T> event(): CSEvent<T> {
    return CSEventImpl()
}

fun CSEvent<Unit>.fire() = fire(Unit)

fun <T> CSEvent<T>.execute(function: (argument: T) -> Unit): CSEventRegistration {
    return this.run { _, argument -> function(argument) }
}

interface CSEvent<T> {

    fun run(listener: (registration: CSEventRegistration, argument: T) -> Unit): CSEventRegistration

    fun fire(argument: T)

    fun clear()

    interface CSEventRegistration {

        var isActive: Boolean

        fun cancel()

        fun event(): CSEvent<*>
    }
}

fun <T> CSEvent<T>.runOnce(listener: (registration: CSEventRegistration, argument: T) -> Unit): CSEventRegistration {
    return run { registration, argument ->
        registration.cancel()
        listener(registration, argument)
    }
}

