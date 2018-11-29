package com.yashganorkar.jeopardy.models

import java.util.*

class Categories(
    val id: Int?,
    val answer: String?,
    val question: String?,
    val value: Int?,
    val airdate: Date?,
    val created_at: Date?,
    val updated_at: Date?,
    val category_id: Int?,
    val game_id: Int?,
    val invalid_count: Int?,
    val category: Category?
)