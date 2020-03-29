import cats.data._
import cats.effect._
import cats.implicits._
import io.circe.syntax._
import org.http4s.CacheDirective._
import org.http4s.{MediaType, _}
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.headers._
import org.http4s.implicits._

import scala.concurrent.ExecutionContext.global

object Application {

  private val supportedStaticExtensions = List(".html", ".js", ".map", ".css", ".png", ".ico")
  private val blocker                   = Blocker.liftExecutionContext(global)

  def service[F[_]](implicit F: Effect[F], cs: ContextShift[F]): ReaderT[F, Request[F], Response[F]] = {
    def getResource(pathInfo: String) = F.delay(getClass.getResource(pathInfo))

    import Templates._
    object dsl extends Http4sDsl[F]
    import dsl._

    HttpRoutes
      .of[F] {
        case GET -> Root =>
          Ok(template(buttonTest).render)
            .map(withHeaders)

        case GET -> Root / "json" / name =>
          Ok(SharedData(name).asJson)

        case req if supportedStaticExtensions.exists(req.pathInfo.endsWith) =>
          StaticFile
            .fromResource[F](req.pathInfo, blocker, req.some)
            .orElse(OptionT.liftF(getResource(req.pathInfo)).flatMap(StaticFile.fromURL[F](_, blocker, req.some)))
            .map(_.putHeaders(`Cache-Control`(NonEmptyList.of(`no-cache`()))))
            .fold(NotFound())(_.pure[F])
            .flatten
      }
      .orNotFound
  }

  def withHeaders[F[_]](response: Response[F]): Response[F] = {
    response
      .withContentType(`Content-Type`(MediaType.text.html, Charset.`UTF-8`))
      .putHeaders(`Cache-Control`(NonEmptyList.of(`no-cache`())))
  }

}
