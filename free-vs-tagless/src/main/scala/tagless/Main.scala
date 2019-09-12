package tagless

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import tagless.algebra.{Imdb, InOut, Log}

/**
  * Example app that fetches top 10 movies from ''IMDB'', using the ''Tagless Final'' approach.
  */
object Main extends App {

  // --- Program definition ---
  def program[F[_]: Monad: Log: InOut: Imdb]: F[Unit] =
    for {
      _      <- Log[F].info("Fetching TOP 10 movies from IMDB")
      movies <- Imdb[F].fetchTop10
      _      <- Log[F].info("Movies fetched, here is the list:")
      _      <- InOut[F].printLine(movies.mkString("\n"))
    } yield ()

  // --- Program execution with dummy Id interpreter ---
  // import tagless.interpret.dummy._
  // program

  // --- Program execution with with real-world IO interpreter ---
  import tagless.interpret.io._
  program.unsafeRunSync()

}
