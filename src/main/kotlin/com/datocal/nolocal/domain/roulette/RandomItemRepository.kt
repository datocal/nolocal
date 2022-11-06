package com.datocal.nolocal.domain.roulette

interface RandomItemRepository {
    fun save(randomItem: String)
}
