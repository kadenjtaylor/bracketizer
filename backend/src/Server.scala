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
          tag("title")("Hello from ScalaJS"),
          link(rel   := "icon", `type` := "image/x-icon", href := "resources/favicon.ico"),
          script(src := "/files/frontend/fastLinkJS.dest/main.js")
        ),
        body(
          h1("Hello :)")
        )
      )
    )
  )

  def clickAction() = println("WOAH")

  @cask.staticFiles("/files/")
  def staticFiles() =
    "out/" // serve files from ./out/ in the root of the project

  @cask.staticResources(
    "/static/"
  ) // unused, just documented as it was not very clear in docs
  def staticResources() = "." // serve files from resouces in this module

  initialize()
}
