import org.scalajs.dom.window.localStorage
import Domain.*
import cats.implicits
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

object Storage {

  // TODO: Both of these methods should be modeled as effects, right?

  val storageKey = "pool-tournament"

  def loadTournament(): Tournament = {
    Option(localStorage.getItem(storageKey)) match
      case None => {
        println("No saved tournament found - creating new tournament.")
        Tournament()
      }
      case Some(tourneyString) => {
        decode[Tournament](tourneyString) match
          case Left(value) => {
            println(s"Unable to parse: $tourneyString as a Tournament.")
            Tournament()
          }
          case Right(tournament) => {
            println(s"Parsed tournament from local storage.")
            tournament
          }
      }
  }

  def storeTournament(t: Tournament) = {
    localStorage.setItem(storageKey, t.asJson.noSpaces)
  }

}
