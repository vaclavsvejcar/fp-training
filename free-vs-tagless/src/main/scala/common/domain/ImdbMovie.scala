package common.domain

import cats.Show

case class ImdbMovie(title: String, rating: Double)

object ImdbMovie {
  implicit val show: Show[ImdbMovie] = Show.show { movie =>
    s"${movie.rating} - ${movie.title}"
  }
}
