package com.datocal.nolocal.application.roulette

import com.datocal.nolocal.domain.roulette.RandomItemRepository
import com.datocal.nolocal.domain.roulette.Roulette
import com.datocal.nolocal.domain.roulette.RouletteItem

class GetRandomItemUseCase(
    private val repository: RandomItemRepository,
) {

    fun execute(request: GetRandomItemUseCaseRequest): GetRandomItemUseCaseResponse {
        val roulette = Roulette(request.items.map { RouletteItem(it) })

        val randomItem = roulette.random()?.value

        saveItemIfNotNull(randomItem)

        return GetRandomItemUseCaseResponse(randomItem)
    }

    private fun saveItemIfNotNull(randomItem: String?) {
        randomItem?.let { repository.save(randomItem) }
    }
}

data class GetRandomItemUseCaseRequest(
    val items: List<String>,
)

data class GetRandomItemUseCaseResponse(
    val item: String?,
)
