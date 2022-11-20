package com.datocal.nolocal.application

import kotlin.random.Random

class RandomProvider {

    fun randomOfTen(): Int {
        return Random.nextInt(10)
    }
}
