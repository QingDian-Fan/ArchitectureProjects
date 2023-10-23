package com.demo.aspectj

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class SingleClick constructor(
    /**
     * 快速点击的间隔
     */
    val value: Long = 800
)