import scala.util.Random

object PlainRec extends App {

  def sum(list: List[Int]): Int = list match {
    case Nil     => 0
    case x :: xs => x + sum(xs)
  }

  val testData: List[Int] = Seq.fill(50)(Random.nextInt).toList
  val result: Int         = sum(testData)
  println(s"SUM: $result")

}
