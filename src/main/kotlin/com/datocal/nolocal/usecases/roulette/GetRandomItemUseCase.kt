package com.datocal.nolocal.usecases.roulette

import com.datocal.nolocal.domain.roulette.Roulette
import com.datocal.nolocal.domain.roulette.RouletteItem

open class GetRandomItemUseCase {

    open fun execute(request: GetRandomItemUseCaseRequest): GetRandomItemUseCaseResponse {
        val roulette = Roulette(request.items.map { RouletteItem(it) })
        return GetRandomItemUseCaseResponse(roulette.random()?.value)
    }
}

data class GetRandomItemUseCaseRequest(
    val items: List<String>
)

data class GetRandomItemUseCaseResponse(
    val item: String?
)
