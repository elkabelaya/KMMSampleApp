package com.elkabelaya.kmmsampleapp.utils.screenutils

enum class ChildType {
    SCREEN,
    ALERT,
    BOTTOMSHEET,
    FULLSCREENCOVER
}
interface TypedChild {
    val type: ChildType
}