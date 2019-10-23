package tagless.algebra

import common.domain.ImdbMovie

trait Imdb[F[_]] {
  def fetchTop10: F[Seq[ImdbMovie]]
}
