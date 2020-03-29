import cats.Show
import io.circe.generic.semiauto._
import io.circe.{Decoder, Encoder}

case class SharedData(name: String)

object SharedData {
  implicit val sharedDataEnc: Encoder[SharedData] = deriveEncoder[SharedData]
  implicit val sharedDataDec: Decoder[SharedData] = deriveDecoder[SharedData]
  implicit val sharedDataShow: Show[SharedData]   = Show.fromToString[SharedData]
}
