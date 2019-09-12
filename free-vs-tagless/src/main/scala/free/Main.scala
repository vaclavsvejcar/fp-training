package free

import cats.data.EitherK
import cats.effect.IO
import cats.free.Free
import cats.syntax.show._
import cats.{Id, ~>}
import free.algebra._
import free.interpret.Log2InOut
import free.interpret.dummy.{Imdb2Id, InOut2Id}
import free.interpret.io.{Imdb2IO, InOut2IO}

/**
  * Example app that fetches top 10 movies from ''IMDB'', using the ''Free Monad'' approach.
  */
object Main extends App {

  // --- Combine algebras together ---
  type ProgramA0[A] = EitherK[LogA, InOutA, A]
  type ProgramA[A]  = EitherK[ImdbA, ProgramA0, A]

  // --- Create Log interpreters based on IdOut ones ---
  val Log2Id: LogA ~> Id = Log2InOut.interp(InOut2Id)
  val Log2IO: LogA ~> IO = Log2InOut.interp(InOut2IO)

  // --- Program definition ---
  def program(implicit log: Log[ProgramA], imdb: Imdb[ProgramA], inOut: InOut[ProgramA]): Free[ProgramA, Unit] =
    for {
      _      <- log.info("Fetching TOP 10 movies from IMDB")
      movies <- imdb.fetchTop10
      _      <- log.info("Movies fetched, here is the list:")
      _      <- inOut.printLine(movies.map(_.show).mkString("\n"))
    } yield ()

  // --- Program Interpreters ---
  val interpId: ProgramA ~> Id = Imdb2Id or (Log2Id or InOut2Id)
  val interpIO: ProgramA ~> IO = Imdb2IO or (Log2IO or InOut2IO)

  // --- Program executions ---
  def programId(): Unit   = program.foldMap(interpId)
  val programIO: IO[Unit] = program.foldMap(interpIO)

  //programId() // Execute program with dummy Id interpreter
  programIO.unsafeRunSync() //Execute program with real-world IO interpreter

}
