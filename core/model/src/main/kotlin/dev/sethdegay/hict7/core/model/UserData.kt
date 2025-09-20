package dev.sethdegay.hict7.core.model

data class UserData(
    val themeConfig: ThemeConfig,
    val dynamicColor: Boolean,
    val tickSound: Boolean,
    val completionSound: Boolean,
    val speakExercise: Boolean,
)