package future

object s001SimpleExample extends App {

  def blockingOperation: String = { Thread.sleep(5000); "result" }

  println("Executing blocking operation")
  val result = blockingOperation
  println("Result is: " + result)

}
