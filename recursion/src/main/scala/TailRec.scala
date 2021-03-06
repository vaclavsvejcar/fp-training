import scala.annotation.tailrec
import scala.util.Random

object TailRec extends App {

  def sum(list: List[Int]): Int = {
    @tailrec
    def impl(list0: List[Int], acc: Int): Int = list0 match {
      case Nil     => acc
      case x :: xs => impl(xs, x + acc)
    }

    impl(list, 0)
  }

  val x = List(1,2).

  val testData: List[Int] = Seq.fill(500_000_000)(1).toList
  val result: Int         = sum(testData)
  println(s"SUM: $result")
}
