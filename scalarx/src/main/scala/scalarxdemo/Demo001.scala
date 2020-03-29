package scalarxdemo

object Demo001 extends App {

  import rx._

  val a = Var(1)
  val b = Var(2)
  val c = Rx { a() + b() }

  println(c.now)
  a() = 4
  println(c.now)
}
