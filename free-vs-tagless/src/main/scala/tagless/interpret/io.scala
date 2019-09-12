package tagless.interpret

import cats.effect.IO
import common.domain.ImdbMovie
import common.logic.ImdbScraper
import tagless.algebra.{Imdb, InOut, Log}

object io {

  implicit object InOut2IO extends InOut[IO] {
    override def printLine(line: String): IO[Unit] = IO { println(line) }
  }

  implicit object Log2IO extends Log[IO] {
    override def info(message: String): IO[Unit] = InOut2IO.printLine(s"[INFO]: $message")
  }

  implicit object Imdb2IO extends Imdb[IO] {
    override def fetchTop10: IO[Seq[ImdbMovie]] = ImdbScraper.fetchTopN(10)
  }
}
