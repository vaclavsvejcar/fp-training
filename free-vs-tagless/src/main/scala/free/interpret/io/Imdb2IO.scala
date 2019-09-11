package free.interpret.io

import cats.effect.IO
import cats.~>
import free.algebra.ImdbA
import free.logic.ImdbScraper

object Imdb2IO extends (ImdbA ~> IO) {
  override def apply[A](fa: ImdbA[A]): IO[A] = fa match {
    case ImdbA.FetchTop10 => ImdbScraper.fetchTopN(10)
  }
}
