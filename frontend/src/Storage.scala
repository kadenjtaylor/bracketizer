import org.scalajs.dom.window.localStorage
import Domain.*

object Storage {

  val storageKey = "forks"

  def loadTournament() = {
    Option(localStorage.getItem(storageKey)) match
      case None => {
        println("No saved tournament found - creating new tournament.")
        Tournament()
      }
      case Some(tourneyString) => {
        println(s"Found value: $tourneyString")
        // TODO: Deserialize tournament from string
        Tournament()
      }
  }

  def storeTournament(t: Tournament) = {
    // TODO: Actually serialize the tournament
    val storageString = t.toString()
    localStorage.setItem(storageKey, storageString)
  }

}
