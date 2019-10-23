package tagless.algebra

import common.domain.ImdbMovie

/**
  * ''IMDB'' related operations.
  * @tparam F wrapper type
  */
trait Imdb[F[_]] {
  def fetchTop10: F[Seq[ImdbMovie]]
}
