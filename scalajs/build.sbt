Global / version := "0.1"
Global / scalaVersion := "2.13.1"

lazy val root = project
  .in(file("."))
  .settings(
    name := "server",
    //sbtPlugin := true,
    publishArtifact in Test := false,
    (resources in Compile) += {
      (fastOptJS in (client, Compile)).value
      (artifactPath in (client, Compile, fastOptJS)).value
    },
    libraryDependencies ++= Seq(
      Dependencies.catsEffects,
      Dependencies.http4sBlazeServer,
      Dependencies.http4sCirce,
      Dependencies.http4sDsl,
      Dependencies.logback
    ),
    // Allows to read the generated JS on client
    resources in Compile += (fastOptJS in (client, Compile)).value.data,
    // Lets the backend to read the .map file for js
    resources in Compile += (fastOptJS in (client, Compile)).value
      .map((x: sbt.File) => new File(x.getAbsolutePath + ".map"))
      .data,
    // Lets the server read the jsdeps file
    //(managedResources in Compile) += (artifactPath in (client, Compile)).value,
    reStart := (reStart dependsOn (fastOptJS in (client, Compile))).evaluated,
    watchSources ++= (watchSources in client).value,
    mainClass in reStart := Some("Server")
  )
  .dependsOn(sharedJVM)
//.aggregate(sharedJVM, sharedJS)

lazy val client = project
  .in(file("client"))
  .enablePlugins(ScalaJSPlugin)
  .settings(
    name := "client",
    libraryDependencies ++= Seq(
      ),
    scalaJSLinkerConfig ~= { _.withOptimizer(false).withClosureCompilerIfAvailable(false) }
  )
  .dependsOn(sharedJS)

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    name := "shared",
    libraryDependencies ++= Seq(
      Dependencies.circeCore.value,
      Dependencies.circeGeneric.value,
      Dependencies.circeParser.value,
      Dependencies.scalatags.value
    )
  )
lazy val sharedJVM = shared.jvm
lazy val sharedJS  = shared.js

ThisBuild / scalacOptions ++= Seq(
  "-deprecation", // Emit warning and location for usages of deprecated APIs.
  "-encoding",
  "utf-8",                 // Specify character encoding used by source files.
  "-explaintypes",         // Explain type errors in more detail.
  "-feature",              // Emit warning and location for usages of features that should be imported explicitly.
  "-language:higherKinds", // Allow higher-kinded types
  "-Ywarn-unused:imports"  // Warn if an import selector is not referenced.
)
