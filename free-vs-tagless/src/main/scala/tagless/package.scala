import cats.Monad
import cats.effect.IO
import cats.syntax.flatMap._

package object tagless {

  implicit def monadIO: Monad[IO] = new Monad[IO] {
    override def pure[A](x: A): IO[A]                                  = IO.pure(x)
    override def flatMap[A, B](fa: IO[A])(f: A => IO[B]): IO[B]        = fa flatMap f
    override def tailRecM[A, B](a: A)(f: A => IO[Either[A, B]]): IO[B] = a tailRecM f
  }

}
