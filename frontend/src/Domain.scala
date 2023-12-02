object Domain {

  enum BracketNode:
    case Player(name: String)
    case Bye(p: Player)
    case NewMatch(bestOf: Int, p1: BracketNode, p2: BracketNode)
    case OngoingMatch(bestOf: Int, p1: Player, p2: Player, p1Deficit: Int)

  case class Tournament(participants: List[BracketNode.Player] = List.empty) {
    def addPlayer(p: BracketNode.Player) = Tournament(participants.appended(p))
    def createRound(
        players: List[BracketNode.Player],
        nodes: List[BracketNode] = List.empty
    ): (List[BracketNode.Player], List[BracketNode]) =
      players match {
        case a :: b :: remaining =>
          createRound(remaining, nodes.appended(BracketNode.NewMatch(3, a, b)))
        case last :: Nil => (List(), players.appended(BracketNode.Bye(last)))
        case Nil         => (players, nodes)
      }
  }

}
