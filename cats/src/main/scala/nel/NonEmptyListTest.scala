package nel

import cats.data.NonEmptyList

object NonEmptyListTest extends App {
  import cats.syntax.list._

  val one: NonEmptyList[Int]               = NonEmptyList.one(42)
  val more: NonEmptyList[Int]              = NonEmptyList.of(1, 2, 3, 4)
  val fromList1: Option[NonEmptyList[Int]] = NonEmptyList.fromList(List(1, 2, 3))
  val fromList2: Option[NonEmptyList[Int]] = List(1, 2, 3).toNel
}
