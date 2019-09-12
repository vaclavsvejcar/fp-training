package tagless.algebra

import common.domain.ImdbMovie

trait Imdb[F[_]] {
  def fetchTop10: F[Seq[ImdbMovie]]
}

object Imdb {
  def apply[F[_]](implicit I: Imdb[F]): Imdb[F] = I
}
