package com.faircorp.model

enum class windowStatus { OPEN, CLOSED}

data class WindowDto(val id: Long, val name: String, val windowStatus: windowStatus, val roomName: String, val roomId: Int)

