package demo001

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorSystem, Behavior}

object TypedExample extends App {

  case class Hello(who: String)

  def typedActor(count: Int): Behavior[Hello] = Behaviors.receiveMessage {
    case Hello(who) =>
      val newCount = count + 1
      println(s"Hello, $who, responded $newCount times")
      typedActor(newCount)
  }

  val system = ActorSystem(typedActor(0), "typed-actor")

  system ! Hello("John Smith")
  system ! Hello("Rose Tyler")
}
