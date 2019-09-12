package tagless.interpret

import cats.Id
import common.domain.ImdbMovie
import tagless.algebra.{Imdb, InOut, Log}

object dummy {

  implicit object InOut2Id extends InOut[Id] {
    override def printLine(line: String): Id[Unit] = println(line)
  }

  implicit object Log2Id extends Log[Id] {
    override def info(message: String): Id[Unit] = InOut2Id.printLine(s"[INFO]: $message")
  }

  implicit object Imdb2Id extends Imdb[Id] {
    override def fetchTop10: Id[Seq[ImdbMovie]] =
      Seq(
        ImdbMovie("movie1", 1.3),
        ImdbMovie("movie2", 5.6),
        ImdbMovie("movie3", 3.4),
        ImdbMovie("movie4", 9.3),
        ImdbMovie("movie5", 8.2),
        ImdbMovie("movie6", 7.3),
        ImdbMovie("movie7", 5.5),
        ImdbMovie("movie8", 1.2),
        ImdbMovie("movie9", 4.5),
        ImdbMovie("movie10", 3.3)
      )
  }

}
