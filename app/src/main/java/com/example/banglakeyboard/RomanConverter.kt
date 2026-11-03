package com.example.banglakeyboard

object RomanConverter {

    private const val HALANT = "\u09CD"

    private val VOWELS = mapOf(
        "অ" to "o", "আ" to "a", "ই" to "i", "ঈ" to "ii", "উ" to "u", "ঊ" to "uu",
        "ঋ" to "ri", "এ" to "e", "ঐ" to "oi", "ও" to "o", "ঔ" to "ou"
    )

    private val CONSONANTS = mapOf(
        "ক" to "k", "খ" to "kh", "গ" to "g", "ঘ" to "gh", "ঙ" to "ng",
        "চ" to "ch", "ছ" to "chh", "জ" to "j", "ঝ" to "jh", "ঞ" to "n",
        "ট" to "t", "ঠ" to "th", "ড" to "d", "ঢ" to "dh", "ণ" to "n",
        "ত" to "t", "থ" to "th", "দ" to "d", "ধ" to "dh", "ন" to "n",
        "প" to "p", "ফ" to "ph", "ব" to "b", "ভ" to "bh", "ম" to "m",
        "য" to "y", "র" to "r", "ল" to "l", "শ" to "sh", "ষ" to "sh",
        "স" to "s", "হ" to "h",
        "\u09DC" to "r", "\u09DD" to "rh", "\u09DF" to "y", "\u09CE" to "t"
    )

    private val SIGNS = mapOf("ং" to "ng", "ঃ" to "h", "ঁ" to "n")

    private val MATRAS = mapOf(
        "া" to "a", "ি" to "i", "ী" to "ii", "ু" to "u", "ূ" to "uu",
        "ৃ" to "ri", "ে" to "e", "ৈ" to "oi", "ো" to "o", "ৌ" to "ou"
    )

    fun convert(items: List<String>): String {
        val sb = StringBuilder()
        for (i in items.indices) {
            val ch = items[i]
            when {
                CONSONANTS.containsKey(ch) -> {
                    sb.append(CONSONANTS[ch])
                    val next = items.getOrNull(i + 1)
                    if (next != null &&
                        (CONSONANTS.containsKey(next) || VOWELS.containsKey(next) || SIGNS.containsKey(next))
                    ) {
                        sb.append("o")
                    }
                }
                VOWELS.containsKey(ch) -> sb.append(VOWELS[ch])
                SIGNS.containsKey(ch) -> sb.append(SIGNS[ch])
                MATRAS.containsKey(ch) -> sb.append(MATRAS[ch])
                ch == HALANT -> { }
                else -> sb.append(ch)
            }
        }
        return sb.toString()
    }
}
