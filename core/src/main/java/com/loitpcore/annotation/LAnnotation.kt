package com.loitpcore.annotation

// @Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
// @Retention(AnnotationRetention.RUNTIME)
// annotation class LayoutId(val value: Int)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class LogTag(val value: String)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsFullScreen(val value: Boolean)

//@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
//@Retention(AnnotationRetention.RUNTIME)
//annotation class IsShowAdWhenExit(val value: Boolean)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsShowAnimWhenExit(val value: Boolean)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsSwipeActivity(val value: Boolean)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsAutoAnimation(val value: Boolean)

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class IsKeepScreenOn(val value: Boolean)
