package free.algebra

import cats.InjectK
import cats.free.Free

/**
  * Algebra for logging related operations.
  *
  * @tparam T operation return type
  */
sealed trait LogA[T]
object LogA {
  case class Info(message: String) extends LogA[Unit]
}

/**
  * [[LogA]] algebra lifted into Free monads.
  *
  * @param I instance of `InjectK`
  * @tparam F final algebra
  */
class Log[F[_]](implicit I: InjectK[LogA, F]) {
  import LogA._

  def info(message: String): Free[F, Unit] = Free.inject[LogA, F](Info(message))
}

/** Helper instances */
object Log {
  implicit def instance[F[_]](implicit I: InjectK[LogA, F]): Log[F] = new Log
}
