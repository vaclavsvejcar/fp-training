package demo003

import akka.actor.typed.receptionist.Receptionist.{Listing, Register, Subscribe}
import akka.actor.typed.receptionist.ServiceKey
import akka.actor.typed.scaladsl.Behaviors
import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.cluster.typed.{Cluster, Join}
import com.typesafe.config.ConfigFactory

object DistributedAlarm extends App {

  import demo002.AlarmDemo._

  val serviceKey = ServiceKey[ActivitySeen]("alarm")
  val config = ConfigFactory.parseString(
    """
      |akka.actor.provider = cluster
      |akka.remote.artery.enabled = true
      |akka.remote.artery.transport = tcp
      |akka.remote.artery.canonical.port = 0
      |akka.remote.artery.canonical.host = 127.0.0.1
      |
      |## disabled for demo
      |akka.cluster.jmx.multi-mbeans-in-same-jvm = on
      |akka.actor.warn-about-java-serializer-usage = off
      |akka.actor.allow-java-serialization = on
      |""".stripMargin
  )

  def distributedAlarm(pinCode: String): Behavior[AlarmMessage] = Behaviors.setup { ctx =>
    ctx.log.info("Starting up alarm")
    ctx.system.receptionist ! Register(serviceKey, ctx.self)

    enabledAlarm(pinCode)
  }

  sealed trait SensorMessage
  case object Trigger                                           extends SensorMessage
  case class AlarmsChanged(alarms: Set[ActorRef[ActivitySeen]]) extends SensorMessage

  def sensor(where: String): Behavior[SensorMessage] = Behaviors.setup { ctx =>
    val adapter = ctx.messageAdapter[Listing] {
      case serviceKey.Listing(alarms) =>
        AlarmsChanged(alarms)
    }

    val subscribe = Subscribe(serviceKey, adapter)
    ctx.system.receptionist ! subscribe

    sensorWithAlarms(where, Set.empty)
  }

  def sensorWithAlarms(where: String, alarms: Set[ActorRef[ActivitySeen]]): Behavior[SensorMessage] =
    Behaviors.receive { (ctx, msg) =>
      msg match {
        case Trigger =>
          val event = ActivitySeen(where)
          alarms.foreach(_ ! event)
          Behaviors.same
        case AlarmsChanged(newAlarms) =>
          ctx.log.info("New set of alarms: {}", newAlarms)
          sensorWithAlarms(where, newAlarms)
      }
    }

  val system1 = ActorSystem(distributedAlarm("0000"), "alarm", config)
  val system2 = ActorSystem(sensor("window"), "alarm", config)
  val system3 = ActorSystem(sensor("door"), "alarm", config)

  // in real world, each cluster should probably run on separate JVM
  val node1 = Cluster(system1)
  val node2 = Cluster(system2)
  val node3 = Cluster(system3)

  // node1 joins itself to form the cluster
  node1.manager ! Join(node1.selfMember.address)

  // node2 and node3 joins the existing cluster
  node2.manager ! Join(node1.selfMember.address)
  node3.manager ! Join(node1.selfMember.address)

  // just let the cluster boot up
  Thread.sleep(10000)

  system2 ! Trigger
  system3 ! Trigger
}
