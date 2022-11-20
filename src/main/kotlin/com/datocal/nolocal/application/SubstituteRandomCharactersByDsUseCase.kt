package com.datocal.nolocal.application

class SubstituteRandomCharactersByDsUseCase(
    private val randomProvider: RandomProvider
) {
    private companion object {
        private const val PROBABILITY_OUT_OF_TEN = 1
        private val LIST_OF_UNCHANGEABLE_CHARACTERS = listOf(
            '¿', '?', '!', '¡', 'a', 'e', 'i', 'o', 'u', ' ',
            'y', 'A', 'E', 'I', 'O', 'U'
        )
    }

    fun execute(request: String): String {
        return request.map(this::randomReplaceCharacter).joinToString("")
    }

    private fun randomReplaceCharacter(character: Char): Char {
        if (isChangeableCharacter(character) && happens()) {
            return replaceCharByD(character)
        }
        return character
    }

    private fun isChangeableCharacter(character: Char) = !LIST_OF_UNCHANGEABLE_CHARACTERS.contains(character)

    private fun replaceCharByD(character: Char): Char {
        if (character.isLowerCase()) {
            return 'd'
        }
        return 'D'
    }

    private fun happens(): Boolean {
        return randomProvider.randomOfTen() <= PROBABILITY_OUT_OF_TEN
    }
}
