import scala.util.Random
import scala.util.control.TailCalls._

object Trampoline extends App {

  def sum(list: List[Int]): TailRec[Int] = list match {
    case Nil     => done(0)
    case x :: xs => tailcall(sum(xs).map(x + _))
  }

  val testData: List[Int]   = Seq.fill(500000)(Random.nextInt).toList
  val sumCall: TailRec[Int] = sum(testData)
  val result: Int           = sumCall.result

  println(s"SUM: $result")
}
