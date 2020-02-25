import scala.concurrent.{ExecutionContext, Future}

def transformFuture(strFt: Future[String])(implicit ec: ExecutionContext): Future[String] =
  for {
    str <- strFt
  } yield str.toUpperCase

def composeFutures(implicit ec: ExecutionContext): Future[String] =
  for {
    f1 <- Future("hello")
    f2 <- Future("world")
  } yield f1 + f2