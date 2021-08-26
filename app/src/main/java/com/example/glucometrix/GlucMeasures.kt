package com.example.glucometrix

import java.sql.Date
import java.sql.Time

data class GlucMeasures(
    val id: Int,
    val time: Time,
    val date: Date,
    val gluc: Int
)
