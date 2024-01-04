package com.datocal.nolocal.domain.roulette

class Roulette(
    private val items: List<RouletteItem>,
) {
    fun random(): RouletteItem? {
        return items.filter(RouletteItem::isValidItem).randomOrNull()
    }
}

class RouletteItem(
    val value: String,
) {
    fun isValidItem(): Boolean {
        return !value.contains(INVALIDATION_SEQUENCE)
    }

    companion object {
        private const val INVALIDATION_SEQUENCE = "~~"
    }
}
