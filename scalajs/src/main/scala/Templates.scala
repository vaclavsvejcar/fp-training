import scalatags.Text.all.Modifier

object Templates {

  def template(bodyContent: Seq[Modifier]) = {
    import scalatags.Text.all._

    html(
      head(
        meta(charset := "utf8"),
      ),
      body(
        bodyContent,
        script(src := "client-fastopt.js")
      )
    )
  }

  val buttonTest: Seq[Modifier] = {
    import scalatags.Text.all._
    Seq(
      button(
        id := "click-me-button",
        `type` := "button",
        onclick := "jsTest()",
        "JS Test"
      ),
      button(
        id := "click-me-button",
        `type` := "button",
        onclick := "ajaxTest()",
        "AJAX Test"
      )
    )
  }

}
