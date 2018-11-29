package com.yashganorkar.jeopardy.models

import java.util.*

class Clue(
    val id: Int?,
    val answer: String?,
    val question: String?,
    val value: Int?,
    val airdate: Date?,
    val category_id: Int?,
    val game_id: String?,
    val invalid_count: String?
) {
}