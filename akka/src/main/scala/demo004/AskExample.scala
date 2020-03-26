package demo004

import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Scheduler}

import scala.concurrent.{ExecutionContextExecutor, Future}
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object CookieFabric {
  sealed trait Command {}
  case class GiveMeCookies(count: Int, replyTo: ActorRef[Reply]) extends Command

  sealed trait Reply
  case class Cookies(count: Int)            extends Reply
  case class InvalidRequest(reason: String) extends Reply

  def apply(): Behaviors.Receive[CookieFabric.GiveMeCookies] =
    Behaviors.receiveMessage { message =>
      if (message.count >= 5)
        message.replyTo ! InvalidRequest("Too many cookies.")
      else
        message.replyTo ! Cookies(message.count)
      Behaviors.same
    }
}

object AskExample extends App {
  import akka.actor.typed.scaladsl.AskPattern._
  import akka.util.Timeout

  implicit val timeout: Timeout = 3.seconds

  val cookieFabric = ActorSystem(CookieFabric(), "cookie-fabric")

  implicit val ec: ExecutionContextExecutor = cookieFabric.executionContext
  implicit val scheduler: Scheduler         = cookieFabric.scheduler

  val result: Future[CookieFabric.Reply] = cookieFabric.ask(ref => CookieFabric.GiveMeCookies(3, ref))

  result.onComplete {
    case Success(CookieFabric.Cookies(count))         => println(s"Yay, $count cookies!")
    case Success(CookieFabric.InvalidRequest(reason)) => println(s"No cookies for me. $reason")
    case Failure(ex)                                  => println(s"Boo! didn't get cookies: ${ex.getMessage}")
  }
}
