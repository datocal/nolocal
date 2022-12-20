package com.datocal.nolocal.application

class SubstituteRandomCharactersByDsUseCase(
    private val randomProvider: RandomProvider
) {
    private companion object {
        private const val PROBABILITY_OUT_OF_TEN = 1
        private const val ASCII_CODE_START_UPPER = 65
        private const val ASCII_CODE_END_UPPER = 90
        private const val ASCII_CODE_START_LOWER = 97
        private const val ASCII_CODE_END_LOWER = 122
        private val LIST_OF_VOWELS = listOf('a', 'e', 'i', 'o', 'u')
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

    private fun isChangeableCharacter(character: Char): Boolean {
        return character.isBasicAsciiCharacter() && !character.isVowel()
    }

    private fun replaceCharByD(character: Char): Char {
        if (character.isLowerCase()) {
            return 'd'
        }
        return 'D'
    }

    private fun happens(): Boolean {
        return randomProvider.randomOfTen() <= PROBABILITY_OUT_OF_TEN
    }

    private fun Char.isVowel(): Boolean {
        return LIST_OF_VOWELS.contains(this)
    }

    private fun Char.isBasicAsciiCharacter(): Boolean {
        return code in ASCII_CODE_START_UPPER..ASCII_CODE_END_UPPER ||
            code in ASCII_CODE_START_LOWER..ASCII_CODE_END_LOWER
    }
}
