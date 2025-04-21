package com.yiyongc.sbpkotlin.dto

data class ApiResponse<T>(
    val data: T
)

data class SuccessData(
    val status: String = "OK",
    val action: String
)

data class ErrorData(
    val error: String
)