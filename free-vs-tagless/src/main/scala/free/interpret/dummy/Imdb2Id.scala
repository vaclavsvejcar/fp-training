package free.interpret.dummy

import cats.{Id, ~>}
import free.algebra.ImdbA
import free.domain.ImdbMovie

object Imdb2Id extends (ImdbA ~> Id) {
  override def apply[A](fa: ImdbA[A]): Id[A] = fa match {
    case ImdbA.FetchTop10 =>
      Seq(
        ImdbMovie("movie1", 1.2),
        ImdbMovie("movie2", 5.6),
        ImdbMovie("movie3", 3.4),
        ImdbMovie("movie4", 9.3),
        ImdbMovie("movie5", 8.2),
        ImdbMovie("movie6", 7.3),
        ImdbMovie("movie7", 5.5),
        ImdbMovie("movie8",1.2),
        ImdbMovie("movie9", 4.5),
        ImdbMovie("movie10", 3.3)
      )
  }
}
