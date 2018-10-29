package com.example.muhammadlutfis.footballapp

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

class TCProvider : CCProvider() {
    override val main: CoroutineContext = Unconfined
}