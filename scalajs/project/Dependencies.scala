import org.portablescala.sbtplatformdeps.PlatformDepsPlugin.autoImport._
import sbt._

object Dependencies {
  object Versions {
    val cats      = "2.1.2"
    val circe     = "0.13.0"
    val http4s    = "0.21.2"
    val logback   = "1.2.3"
    val scalatags = "0.8.6"
  }

  val catsCore          = "org.typelevel"  %% "cats-core"           % Versions.cats
  val catsEffects       = "org.typelevel"  %% "cats-effect"         % Versions.cats
  val http4sBlazeServer = "org.http4s"     %% "http4s-blaze-server" % Versions.http4s
  val http4sCirce       = "org.http4s"     %% "http4s-circe"        % Versions.http4s
  val http4sDsl         = "org.http4s"     %% "http4s-dsl"          % Versions.http4s
  val logback           = "ch.qos.logback" % "logback-classic"      % Versions.logback

  val circeCore    = Def.setting("io.circe"    %%% "circe-core"    % Versions.circe)
  val circeGeneric = Def.setting("io.circe"    %%% "circe-generic" % Versions.circe)
  val circeParser  = Def.setting("io.circe"    %%% "circe-parser"  % Versions.circe)
  val scalatags    = Def.setting("com.lihaoyi" %%% "scalatags"     % Versions.scalatags)

}
