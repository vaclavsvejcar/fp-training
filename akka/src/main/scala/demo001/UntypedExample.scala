package demo001

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object UntypedExample extends App {

  case class Hello(who: String)

  class UntypedActor extends Actor {
    var count = 0

    override def receive: Receive = {
      case Hello(who) =>
        count += 1
        println(s"Hello, $who, responded $count times")
    }
  }

  val system = ActorSystem("test-system")
  val actorRef: ActorRef = system.actorOf(Props(new UntypedActor), "untyped-actor")

  actorRef ! Hello("John Smith")
  actorRef ! Hello("Rose Tyler")

}
