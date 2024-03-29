import mill._
import mill.api.Loose
import mill.define.Target
import mill.scalajslib.ScalaJSModule
import mill.scalalib._
import mill.scalalib.api.CompilationResult

val projectScalaVersion = "3.3.1"

object frontend extends ScalaJSModule {

  override def scalaVersion   = projectScalaVersion
  override def scalaJSVersion = "1.14.0"

  val circeVersion = "0.14.1"

  override def ivyDeps = Agg(
    ivy"org.scala-js::scalajs-dom::2.8.0",
    ivy"io.circe::circe-core::0.14.1",
    ivy"io.circe::circe-generic::0.14.1",
    ivy"io.circe::circe-parser::0.14.1",
    ivy"com.raquo::laminar::16.0.0"
    // ivy"org.typelevel::cats-effect::3.5.2" - to be used if needed
  )

}

object backend extends ScalaModule {

  override def scalaVersion = projectScalaVersion

  override def ivyDeps = Agg(
    ivy"com.lihaoyi::cask:0.9.1",
    ivy"com.lihaoyi::scalatags:0.12.0"
  )

  override def compile: T[CompilationResult] = T {
    frontend.fastLinkJS.apply()
    super.compile.apply()
  }

}
