import scalatags.Text.all._

object Server extends cask.MainRoutes {

  @cask.staticFiles("/resources")
  def favicon() = "frontend/resources"

  @cask.get("/")
  def hello() = cask.Response(
    headers = Seq(
      "Content-Type" -> "text/html",
      "Server"       -> "cask"
    ),
    data = doctype("html")(
      html(
        lang := "en",
        head(
          tag("title")("Bracketizer"),
          link(rel   := "icon", `type` := "image/x-icon", href := "resources/favicon.ico"),
          script(src := "/files/frontend/fastLinkJS.dest/main.js")
        ),
        body(
          div(id := "appContainer")
        )
      )
    )
  )

  @cask.staticFiles("/files/")
  def staticFiles() =
    "out/" // serve files from ./out/ in the root of the project

  initialize()
}
