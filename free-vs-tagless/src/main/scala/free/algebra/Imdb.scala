package free.algebra

import cats.InjectK
import cats.free.Free
import free.domain.ImdbMovie

sealed trait ImdbA[A]
object ImdbA {
  case object FetchTop10 extends ImdbA[Seq[ImdbMovie]]
}

class Imdb[F[_]](implicit I: InjectK[ImdbA, F]) {
  import ImdbA._

  def fetchTop10: Free[F, Seq[ImdbMovie]] = Free.inject[ImdbA, F](FetchTop10)
}

object Imdb {
  implicit def instance[F[_]](implicit I: InjectK[ImdbA, F]): Imdb[F] = new Imdb
}