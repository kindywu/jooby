package org.jooby

import org.jooby.spi.Server
import java.util.*
import kotlin.reflect.KClass

@DslMarker
annotation class DslJooby

/**
  * Collection of utility class and method to make Jooby more Kotlin.
  */
@DslJooby
open class Kooby constructor(): Jooby() {
  constructor(init: Kooby.() -> Unit): this() {
    this.init()
  }

  fun <T:Any> use(klass: KClass<T>): Route.Collection {
    return use(klass.java)
  }

  fun <T:Any> use(path: String, klass: KClass<T>): Route.Collection {
    return use(path, klass.java)
  }

  fun <T:Session.Store> session(klass: KClass<T>): Session.Definition {
    return session(klass.java)
  }

  fun <T:Server> server(klass: KClass<T>): Jooby {
    return server(klass.java)
  }

  fun get(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return get(pattern, {req-> req.init()})
  }

  fun post(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return post(pattern, {req-> req.init()})
  }

  fun put(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return put(pattern, {req-> req.init()})
  }

  fun patch(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return patch(pattern, {req-> req.init()})
  }

  fun delete(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return delete(pattern, {req-> req.init()})
  }

  fun trace(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return trace(pattern, {req-> req.init()})
  }

  fun connect(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return connect(pattern, {req-> req.init()})
  }

  fun options(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return options(pattern, {req-> req.init()})
  }

  fun head(pattern: String = "/", init: Request.() -> Any): Route.Definition {
    return head(pattern, {req-> req.init()})
  }

  fun onStart(init: Registry.() -> Unit): Jooby {
    this.onStart({registry-> registry.init()})
    return this
  }

  fun onStarted(init: Registry.() -> Unit): Jooby {
    this.onStarted({registry-> registry.init()})
    return this
  }

  fun onStop(init: Registry.() -> Unit): Jooby {
    this.onStop({registry-> registry.init()})
    return this
  }
}

/**
 * Creates jooby application and return it.
 *
 * <pre>
 * val app = jooby {
 *  get("/") {-> "Hi Kotlin"}
 * }
 * </pre>
 */
fun jooby(init: Kooby.() -> Unit): Jooby {
  val app = Kooby()
  app.init()
  return app
}

/**
 * Creates and run jooby application.
 *
 * <pre>
 * run(*args) {
 *  get("/") {-> "Hi Kotlin"}
 * }
 * </pre>
 */
fun run(vararg args: String, init: Kooby.() -> Unit): Unit {
  Jooby.run({-> Kooby(init)}, args)
}

fun run(supplier: () -> Jooby, vararg args: String): Unit {
  Jooby.run(supplier, args)
}

// Redefine functions with class arguments
fun <T:Throwable> Router.err(klass: KClass<T>, handler: Err.Handler): Router {
  return this.err(klass.java, handler)
}

// *********************************** Registry **************************************************
fun <T:Any> Registry.require(klass: KClass<T>): T {
  return this.require(klass.java)
}

fun <T:Any> Registry.require(name: String, klass: KClass<T>): T {
  return this.require(name, klass.java)
}

// *********************************** LifeCycle **************************************************
fun <T:Any> LifeCycle.lifeCycle(klass: KClass<T>): LifeCycle {
  return this.lifeCycle(klass.java)
}

fun Env.onStart(init: Registry.() -> Unit): LifeCycle {
  return this.onStart({registry-> registry.init()})
}

fun Env.onStarted(init: Registry.() -> Unit): LifeCycle {
  return this.onStarted({registry-> registry.init()})
}

fun Env.onStop(init: Registry.() -> Unit): LifeCycle {
  return this.onStop({registry-> registry.init()})
}

// *********************************** Mutant *****************************************************
val Mutant.booleanValue: Boolean
  get() = booleanValue()

val Mutant.shortValue: Short
  get() = shortValue()

val Mutant.charValue: Char
  get() = charValue()

val Mutant.byteValue: Byte
  get() = byteValue()

val Mutant.intValue: Int
  get() = intValue()

val Mutant.longValue: Long
  get() = longValue()

val Mutant.floatValue: Float
  get() = floatValue()

val Mutant.doubleValue: Double
  get() = doubleValue()

val Mutant.value: String
  get() = value()

inline fun <reified T> Mutant.to(): T {
  // FIXME: https://stackoverflow.com/questions/36253310/how-to-get-actual-type-arguments-of-a-reified-generic-parameter-in-kotlin
  return this.to(T::class.java)
}

fun <T:Any> Mutant.to(type: KClass<T>): T {
  // FIXME: https://stackoverflow.com/questions/36253310/how-to-get-actual-type-arguments-of-a-reified-generic-parameter-in-kotlin
  return this.to(type.java)
}

inline fun <reified T> Mutant.to(contentType: MediaType): T {
  return this.to(T::class.java, contentType)
}

fun <T:Any> Mutant.to(type: KClass<T>, contentType: MediaType): T {
  return this.to(type.java, contentType)
}

inline fun <reified T> Mutant.to(contentType: String): T {
  return this.to(T::class.java, contentType)
}

fun <T:Any> Mutant.to(type: KClass<T>, contentType: String): T {
  return this.to(type.java, contentType)
}

fun <T:Enum<T>> Mutant.toEnum(type: KClass<T>): T {
  return this.toEnum(type.java)
}

inline fun <reified T:Enum<T>> Mutant.toEnum(): T {
  return this.toEnum(T::class.java)
}

fun <T:Any> Mutant.toOptional(type: KClass<T>): java.util.Optional<T> {
  return this.toOptional(type.java)
}

inline fun <reified T> Mutant.toOptional(): java.util.Optional<T> {
  return this.toOptional(T::class.java)
}

fun <T:Any> Mutant.toList(type: KClass<T>): List<T> {
  return this.toList(type.java)
}

inline fun <reified T> Mutant.toList(): List<T> {
  return this.toList(T::class.java)
}

fun <T:Any> Mutant.toSet(type: KClass<T>): Set<T> {
  return this.toSet(type.java)
}

inline fun <reified T> Mutant.toSet(): Set<T> {
  return this.toSet(T::class.java)
}

fun <T:Comparable<T>> Mutant.toSortedSet(type: KClass<T>): SortedSet<T> {
  return this.toSortedSet(type.java)
}

inline fun <reified T:Comparable<T>> Mutant.toSortedSet(): SortedSet<T> {
  return this.toSortedSet(T::class.java)
}

// *********************************** Request *****************************************************
fun <T:Any> Request.set(type: KClass<T>, value: Any): Request {
  return this.set(type.java, value)
}

inline fun <reified T> Request.param(name: String): T {
  return param(name).to(T::class.java)
}

inline fun <reified T> Request.header(name: String): T {
  return param(name).to(T::class.java)
}

inline fun <reified T> Request.body(): T {
  return body().to(T::class.java)
}

inline fun <reified T> Request.params(): T {
  return params(T::class.java)
}
