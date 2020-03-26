name := "akka"

version := "0.1"

scalaVersion := "2.13.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.4",
  "com.typesafe.akka" %% "akka-cluster-typed" % "2.6.4",
  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
