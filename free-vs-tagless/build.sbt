name := "free-vs-tagless"
version := "0.1"
scalaVersion := "2.12.10"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "2.0.0",
  "org.typelevel" %% "cats-effect" % "2.0.0",
  "org.typelevel" %% "cats-free" % "2.0.0",
  "net.ruippeixotog" %% "scala-scraper" % "2.2.0"
)

scalacOptions ++= Seq(
  "-Ypartial-unification",
  "-language:higherKinds"
)
