import Domain.*
import com.raquo.laminar.api.L.{*, given}
import org.scalajs.dom.Event

object Main {

  def functionalityTest = {
    val tournament = Storage.loadTournament()

    val names   = List("Kaden", "Greg", "Max", "John", "Paul")
    val players = names.map(newPlayer(_))
    val updated = players.foldRight(tournament)((p, t) => {
      println(s"Adding player: $p")
      t.addPlayer(p)
    })
    println("Storing updated tournament!")
    Storage.storeTournament(updated)
    val round1 = createRound(updated.participants.toList)
    println(s"Round 1: $round1")
    val round2 = createRound(round1)
    println(s"Round 2: $round2")
    val round3 = createRound(round2)
    println(s"Round 3: $round3")
  }

  def app() = {
    val nameSheet: Var[List[String]] = Var(List())
    val addNewPlayer                 = Observer(ev => nameSheet.update(_.appended("New Player")))
    def setPlayerNameByNumber(index: Int) =
      Observer((value: String) => {
        nameSheet.update(_.updated(index, value))
      })
    div(
      h1("Signup"),
      children <-- nameSheet.signal.splitByIndex { case (index, str, strSig) =>
        div(
          input(
            margin       := "5px",
            defaultValue := str,
            onChange.mapToValue --> setPlayerNameByNumber(index),
            value <-- strSig
          )
        )
      },
      button("Add Player", onClick --> addNewPlayer),
      textAlign := "center"
    )
  }

  def main(args: Array[String]): Unit = {
    windowEvents(_.onLoad).foreach { _ =>
      val appContainer = org.scalajs.dom.document.querySelector("#appContainer")
      render(appContainer, app())
    }(unsafeWindowOwner)
  }
}
