package free

import cats.data.EitherK
import cats.free.Free
import cats.{Id, ~>}
import free.algebra._
import free.interpret.Log2InOut
import free.interpret.dummy.{Imdb2Id, InOut2Id}

object Main extends App {

  type ProgramA0[A] = EitherK[LogA, InOutA, A]
  type ProgramA[A]  = EitherK[ImdbA, ProgramA0, A]

  val Log2Id: LogA ~> Id = Log2InOut.interp(InOut2Id)

  // --- Program definition ---
  def program(implicit log: Log[ProgramA], imdb: Imdb[ProgramA], inOut: InOut[ProgramA]): Free[ProgramA, Unit] =
    for {
      _      <- log.info("Fetching TOP 10 movies from IMDB")
      movies <- imdb.fetchTop10
      _      <- log.info("Movies fetched, here is the list:")
      _      <- inOut.printLine(movies.mkString("\n"))
    } yield ()

  // --- Interpreters ---
  val interpId: ProgramA ~> Id = Imdb2Id or (Log2Id or InOut2Id)

  // --- Program executions ---
  def programId(): Unit = program.foldMap(interpId)

  programId() // Execute program with ID interpreter

}
