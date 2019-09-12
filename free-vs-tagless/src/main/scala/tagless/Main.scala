package tagless

import cats.Monad
import cats.syntax.flatMap._
import cats.syntax.functor._
import cats.syntax.show._
import tagless.algebra.{Imdb, InOut, Log}

/**
  * Example app that fetches top 10 movies from ''IMDB'', using the ''Tagless Final'' approach.
  */
object Main extends App {

  // --- Program definition ---
  def program[F[_]: Monad](implicit log: Log[F], imdb: Imdb[F], inOut: InOut[F]): F[Unit] =
    for {
      _      <- log.info("Fetching TOP 10 movies from IMDB")
      movies <- imdb.fetchTop10
      _      <- log.info("Movies fetched, here is the list:")
      _      <- inOut.printLine(movies.map(_.show).mkString("\n"))
    } yield ()

  // --- Program execution with dummy Id interpreter ---
  // import tagless.interpret.dummy._
  // program

  // --- Program execution with with real-world IO interpreter ---
  import tagless.interpret.io._
  program.unsafeRunSync()

}
