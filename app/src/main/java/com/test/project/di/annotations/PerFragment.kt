package com.test.project.di.annotations

import javax.inject.Named
import javax.inject.Scope

@Scope
@Named("SingleThread")
@Retention(AnnotationRetention.RUNTIME)
annotation class PerFragment 