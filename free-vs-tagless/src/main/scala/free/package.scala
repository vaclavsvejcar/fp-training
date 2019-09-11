import cats.free.Free
import free.algebra.InOutA

package object free {
  type FreeInOutA[A] = Free[InOutA, A]
}
