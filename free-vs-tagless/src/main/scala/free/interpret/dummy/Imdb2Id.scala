package free.interpret.dummy

import cats.{Id, ~>}
import free.algebra.ImdbA
import free.domain.ImdbMovie

object Imdb2Id extends (ImdbA ~> Id) {
  override def apply[A](fa: ImdbA[A]): Id[A] = fa match {
    case ImdbA.FetchTop10 =>
      Seq(
        ImdbMovie("movie1"),
        ImdbMovie("movie2"),
        ImdbMovie("movie3"),
        ImdbMovie("movie4"),
        ImdbMovie("movie5"),
        ImdbMovie("movie6"),
        ImdbMovie("movie7"),
        ImdbMovie("movie8"),
        ImdbMovie("movie9"),
        ImdbMovie("movie10")
      )
  }
}
