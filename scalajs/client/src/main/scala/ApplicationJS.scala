import cats.implicits._
import org.scalajs.dom
import org.scalajs.dom.ext.Ajax

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js.annotation.JSExportTopLevel

@JSExportTopLevel("ApplicationJS")
object ApplicationJS {

  def main(): Unit = {

  }

  def appendText(targetNode: dom.Node, text: String): Unit = {
    val parNode  = dom.document.createElement("p")
    val textNode = dom.document.createTextNode(text)
    parNode.appendChild(textNode)
    targetNode.appendChild(parNode)
  }

  @JSExportTopLevel("jsTest")
  def jsTest(): Unit =
    appendText(dom.document.body, "You Clicked The Button")

  @JSExportTopLevel("ajaxTest")
  def ajaxTest(): Unit = {
    Ajax
      .get("/json/test")
      .map(_.responseText)
      .map(
        json =>
          io.circe.parser.parse(json).flatMap(SharedData.sharedDataDec.decodeJson) match {
            case Left(e)  => e.getMessage
            case Right(d) => show"Decoded: $d Raw: $json"
          }
      )
      .map(appendText(dom.document.body, _))
      .onComplete(_ => ())
  }
}
