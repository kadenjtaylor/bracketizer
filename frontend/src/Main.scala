import Domain.*

object Main {
  def main(args: Array[String]): Unit = {
    val tournament            = Storage.loadTournament()
    val p: BracketNode.Player = BracketNode.Player("Kaden")
    val updated               = tournament.addPlayer(p)
    println(s"Added player: $p")
    Storage.storeTournament(updated)
  }
}
