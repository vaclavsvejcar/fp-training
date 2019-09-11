package free.algebra

import cats.InjectK
import cats.free.Free

sealed trait LogA[A]
object LogA {
  case class Info(message: String) extends LogA[Unit]
}

class Log[F[_]](implicit I: InjectK[LogA, F]) {
  import LogA._

  def info(message: String): Free[F, Unit] = Free.inject[LogA, F](Info(message))
}

object Log {
  implicit def instance[F[_]](implicit I: InjectK[LogA, F]): Log[F] = new Log
}
