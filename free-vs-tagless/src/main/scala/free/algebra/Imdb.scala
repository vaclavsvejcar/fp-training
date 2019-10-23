package free.algebra

import cats.InjectK
import cats.free.Free
import common.domain.ImdbMovie

/**
  * Algebra for ''IMDB'' related operations.
  *
  * @tparam T operation return type
  */
sealed trait ImdbA[T]
object ImdbA {
  case object FetchTop10 extends ImdbA[Seq[ImdbMovie]]
}

/**
  * [[ImdbA]] algebra lifted into Free monads.
  *
  * @param I instance of `InjectK`
  * @tparam F final algebra
  */
class Imdb[F[_]](implicit I: InjectK[ImdbA, F]) {
  import ImdbA._

  def fetchTop10: Free[F, Seq[ImdbMovie]] = Free.inject[ImdbA, F](FetchTop10)
}

/** Helper instances */
object Imdb {
  implicit def instance[F[_]](implicit I: InjectK[ImdbA, F]): Imdb[F] = new Imdb
}
