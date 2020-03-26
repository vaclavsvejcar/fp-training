package demo002

import akka.actor.typed.{ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object AlarmDemo extends App {

  sealed trait AlarmMessage
  case object EnableAlarm               extends AlarmMessage
  case class DisableAlarm(pin: String)  extends AlarmMessage
  case class ActivitySeen(what: String) extends AlarmMessage

  def disabledAlarm(pinCode: String): Behavior[AlarmMessage] = Behaviors.receive { (actorCtx, msg) =>
    msg match {
      case EnableAlarm =>
        actorCtx.log.info("Enabling alarm")
        enabledAlarm(pinCode)
      case _ =>
        actorCtx.log.warn("Invalid state")
        Behaviors.same
    }
  }

  def enabledAlarm(pinCode: String): Behavior[AlarmMessage] = Behaviors.receive { (actorCtx, msg) =>
    msg match {
      case ActivitySeen(what) =>
        actorCtx.log.warn("ALERT, activity detected: {}", what)
        Behaviors.same
      case DisableAlarm(triedPinCode) if triedPinCode == pinCode =>
        actorCtx.log.info("Alarm disabled")
        disabledAlarm(pinCode)
      case DisableAlarm(_) =>
        actorCtx.log.warn("ALERT, invalid PIN entered")
        Behaviors.same
      case _ =>
        Behaviors.unhandled

    }
  }

  val system = ActorSystem(enabledAlarm("0000"), "alarm")
  system ! ActivitySeen("door opened")
  system ! DisableAlarm("1234")
  system ! DisableAlarm("0000")
  system ! ActivitySeen("window opened")
  system ! EnableAlarm

}
