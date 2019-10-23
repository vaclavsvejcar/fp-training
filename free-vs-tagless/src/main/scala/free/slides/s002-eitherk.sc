import cats.{Id, ~>}
import cats.data.EitherK
import cats.free.Free
import common.domain.ImdbMovie
import free.algebra._

object s002Eitherk {

  type CombinedA[T] = EitherK[InOutA, ImdbA, T]

  val inOutOp: Free[CombinedA, Unit]          = Free.liftF(EitherK.leftc(InOutA.PrintLine("foo")))
  val imdbOp: Free[CombinedA, Seq[ImdbMovie]] = Free.liftF(EitherK.rightc(ImdbA.FetchTop10))

  object InOut2Id extends (InOutA ~> Id) {
    override def apply[A](fa: InOutA[A]) = ???
  }

  object Imdb2Id extends (ImdbA ~> Id) {
    override def apply[A](fa: ImdbA[A]) = ???
  }

  val interpreter = InOut2Id or Imdb2Id

}
