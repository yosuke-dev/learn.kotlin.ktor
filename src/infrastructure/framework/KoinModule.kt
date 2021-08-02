package infrastructure.framework

import org.koin.dsl.module
import interfaces.controllers.BookController
import interfaces.repositories.BookRepository
import interfaces.repositories.BookRepositoryImpl
import org.koin.core.component.KoinApiExtension
import usecase.BookService
import javax.validation.Validation
import javax.validation.Validator

@kotlin.OptIn(KoinApiExtension::class)
val koinModules = module {
    single { BookController() }
    single { BookService() }
    factory<Validator> { Validation.buildDefaultValidatorFactory().validator }
    factory<BookRepository> { BookRepositoryImpl() }
}