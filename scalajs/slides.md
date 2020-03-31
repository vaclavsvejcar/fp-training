---
title: FP Training - Scala.js
author: Vaclav Svejcar
...

# Table of Contents

1. __sbt-revolver__
1. cats-effects
1. circe
1. scalatags
1. scala.js
1. http4s

---

# sbt-revolver

- __sbt plugin__ for hot-reloading project on code change
- Starting and stopping your application in the background of your interactive
  SBT shell (in a forked JVM)
- Triggered restart: automatically restart your application as soon as some
  of its sources have been changed

```
addSbtPlugin("io.spray" % "sbt-revolver" % "0.9.1")
```

```
$ sbt
sbt:project> ~reStart
```

---

# Table of Contents

1. sbt-revolver
1. __cats-effects__
1. circe
1. scalatags
1. scala.js
1. http4s

---

# cats-effects - IO Monad
- _IO Monad_ & friends for Cats and Scala ecosystem
```
libraryDependencies += "org.typelevel" %% "cats-effect" % "2.1.2"
```

```scala
import cats.effect.IO

val ioa = IO { println("hey!") }
val program: IO[Unit] =
  for {
     _ <- ioa
     _ <- ioa
  } yield ()

program.unsafeRunSync()     // prints "hey" 2 times
```

---

# cats-effects - IOApp
- replacement for Scala's `App`, which executes a cats.effect.IO, as an entry
  point to a pure FP program

```scala
import cats.effect.{ExitCode, IO, IOApp}
import cats.syntax.all._

object Test extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    val ioa = IO { println("hey!") }
    val program = for {
      _ <- ioa
      _ <- ioa
    } yield ()

    program.as(ExitCode.Success)
  }
}
```

---

# Table of Contents

1. sbt-revolver
1. cats-effects
1. __circe__
1. scalatags
1. scala.js
1. http4s

---

# circe
- _JSON_ library for Scala
- circe’s working title was _jfc_, which stood for _“JSON for cats”_

```scala
val circeVersion = "0.12.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
```

---

# circe - example

```scala
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

sealed trait Foo
case class Bar(xs: Vector[String]) extends Foo
case class Qux(i: Int, d: Option[Double]) extends Foo

val foo: Foo = Qux(13, Some(14.0))

val json = foo.asJson.noSpaces
println(json)

val decodedFoo = decode[Foo](json)
println(decodedFoo)
```

---

# circe - encoding/decoding options
- semi-automatic derivation
- automatic derivation
- custom codecs

---

# circe - semi-automatic derivation
- similar to _Play JSON's_ `Reads` and `Writes` type classes
- user must define implicit values with instance of `Decoder`/`Encoder` type class

```scala
import io.circe._, io.circe.generic.semiauto._

case class Foo(a: Int, b: String, c: Boolean)

implicit val fooDecoder: Decoder[Foo] = deriveDecoder
implicit val fooEncoder: Encoder[Foo] = deriveEncoder
```

---

# circe - automatic derivation
- _Shapeless_-powered automatic derivation with zero boilerplate

```scala
import io.circe.generic.auto._, io.circe.syntax._

case class Person(name: String)
case class Greeting(salutation: String, person: Person, exclamationMarks: Int)

Greeting("Hey", Person("Chris"), 3).asJson
```

---

# circe - custom codecs
- manual way how to define encoding/decoding to/from JSON __#streetscala__

```scala
import io.circe.{ Decoder, Encoder, HCursor, Json }

class Thing(val foo: String, val bar: Int)

implicit val encodeFoo: Encoder[Thing] = new Encoder[Thing] {
  final def apply(a: Thing): Json = Json.obj(
    ("foo", Json.fromString(a.foo)),
    ("bar", Json.fromInt(a.bar))
  )
}
```

---

# Table of Contents

1. sbt-revolver
1. cats-effects
1. circe
1. __scalatags__
1. scala.js
1. http4s

---

# scalatags
- _HTML/XML/CSS_ construction library for _Scala_ and _Scala.js_
- __type safe__ definitions using Scala DSL instead of plain text
    - IDE hints & code completion
    - error highlighting
    - easier code refactoring (broken code = compiler error)

```scala
html(
  head(
    script("some script")
  ),
  body(
    h1("This is my title"),
    div(
      p("This is my first paragraph"),
      p("This is my second paragraph")
    )
  )
)
```

---

# scalatags - usage

```scala
a(href:="www.google.com")(p("Goooogle"))                            // attributes
a(attr("href") := "www.google.com")(p("Goooogle"))                  // custom attributes
h1(backgroundColor := "blue", color := "red")("This is my title")   // inline styles
h1("This is my ", title)                                            // variables

// control flow
for ((name, text) <- posts) yield div(
  h2("Post by ", name),
  p(text)
)
```

---

# scalatags - CSS

```scala
object Simple extends StyleSheet {
  initStyleSheet()

  val x = cls(
    backgroundColor := "red",
    height := 125
  )
  val y = cls.hover(
    opacity := 0.5
  )

  val z = cls(x.splice, y.splice)
}

val x = div(
  Simple.x,
  Simple.y
)
```

---

# scalatags - CSS

```css
.$pkg-Simple-x{
  background-color: red;
  height: 125px;
}
.$pkg-Simple-y:hover{
  opacity: 0.5;
}
.$pkg-Simple-z{
  background-color: red;
  height: 125px;
  opacity: 0.5;
}
```

---

# Table of Contents

1. sbt-revolver
1. cats-effects
1. circe
1. scalatags
1. __scala.js__
1. http4s

---

# scala.js
- plugin to Scala compiler generating JavaScript instead of Java Bytecode

## Why would I ever need this?

```javascript
["10", "10", "10", "10"].map(parseInt)
// res0: [10, NaN, 2, 3] // WTF
```

```scala
List("10", "10", "10", "10").map(parseInt)
// res0: List(10, 10, 10, 10) // Yay!
```

---

# scala.js
- how would you write the following JS code in Scala.js?

```scala
var xhr = new XMLHttpRequest()

xhr.open("GET",
  "https://api.twitter.com/1.1/search/" +
  "tweets.json?q=%23scalajs"
)
xhr.onload = { (e: Event) =>
  if (xhr.status == 200) {
    val r = JSON.parse(xhr.responseText)
    $("#tweets").html(parseTweets(r))
  }
}
xhr.send()
```

---

# scala.js - project setup

1. load the sbt plugin (`projects/plugins.sbt`)
   `addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.1")`
1. enable the plugin (`build.sbt`)
   `lazy val root = project.enablePlugins(ScalaJSPlugin)`

## Compilation in SBT

```sh
sbt> compile        # generates .sjsir and .class files
sbt> fastOptJS      # generates -fastopt.js file (minimum optimizations, DEV)
sbt> fullOptJS      # generates -opt.js file (full optimizations, PROD)
```

---

# scala.js - compilation
- where _scalac_ produces `.class` files, _scala.js_ produces `.sjsir`
- __SJSIR__ (scala.js intermediate representation) is specific for
    - Scala major version (`2.12`, `2.13`) __AND__
    - Scala.js major version (`0.6`, `1.0`)

## Sharing code between JVM and JS
- can be done using _cross building_ (`sbt-crossproject` SBT plugin)
- https://www.scala-js.org/doc/project/cross-build.html

---

# scala.js - Cooperation with JS
- __type façades__ (similar to _TypeScript_) are used to create type safe API
  around JavaScript API
- usually already available for most popular JS libraries

```scala
@js.native
trait Window extends js.Object {
  val document: HTMLDocument = js.native
  var location: String = js.native

  def innerWidth: Int = js.native
  def innerHeight: Int = js.native

  def alert(message: String): Unit = js.native

  def open(url: String, target: String,
           features: String = ""): Window = js.native
  def close(): Unit = js.native
}
```

---

# scala.js - Performance
- https://www.scala-js.org/doc/internals/performance.html

## fastOptJS vs JVM
- between around __0.9x and 6x slower__ than the JVM

## fullOptJS vs JVM
- between __0.9x and 3x slower__ than the JVM

## fullOptJS vs hand-written JS
- Octane benchmark suite
- in the worst case, `deltablue`, fully optimized Scala.js is __1.27x slower__
- In the best case, `tracer`, Scala.js executes in __0.67x__ the time of
  hand-written JavaScript, i.e., __33% faster__

---

# Table of Contents

1. sbt-revolver
1. cats-effects
1. circe
1. scalatags
1. scala.js
1. __http4s__

---

# http4s
- idiomatic Scala interface for HTTP, main features are
    - maximum type safety
    - FP interface
    - built on _FS2_, effects done using _cats-effects_

```scala
libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-dsl" % "0.21.1",
  "org.http4s" %% "http4s-blaze-server" % "0.21.1",
)
```

---

# http4s - Defining Service
- __service__ is the piece of code responsible for handling requests and
  resulting in response
- `HttpRoutes[F]` is type alias for `Kleisli[OptionT[F, *], Request, Response]`
    - _Kleisli_ is just `A => F[B]`, so this is `Request => F[Response]`

```scala
import cats.effect._, org.http4s._, org.http4s.dsl.io._,
import scala.concurrent.ExecutionContext.Implicits.global

implicit val cs: ContextShift[IO] = IO.contextShift(global)
implicit val timer: Timer[IO]     = IO.timer(global)

val helloWorldService: HttpRoutes[IO] = HttpRoutes.of[IO] {
  case GET -> Root / "hello" / name =>
    Ok(s"Hello, $name.")
}
```

---

# http4s - Running Service
- _service_ can be executed using the `IOApp` from _cats-effects_

```scala
object Main extends IOApp {

  val helloWorldService = HttpRoutes.of[IO] {
    case GET -> Root / "hello" / name =>
      Ok(s"Hello, $name.")
  }.orNotFound

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(helloWorldService)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
```