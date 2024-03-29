object Domain {

  opaque type MatchId = String

  enum BracketNode:
    case Player(name: String)
    case Bye(p: BracketNode)
    case NewMatch(bestOf: Int, p1: BracketNode, p2: BracketNode)
    case OngoingMatch(bestOf: Int, p1: Player, p2: Player, p1Deficit: Int)
    case MatchResult(bestOf: Int, winner: Player, loser: Player)

    def id: MatchId = this.hashCode().toHexString

  def newPlayer(name: String): BracketNode.Player =
    BracketNode.Player(name)

  case class Tournament(participants: Set[BracketNode.Player] = Set.empty) {
    def addPlayer(p: BracketNode.Player) = Tournament(participants + p)
  }

  def createRound(
      seeds: List[BracketNode],
      nodes: List[BracketNode] = List.empty
  ): List[BracketNode] =
    seeds match {
      case a :: b :: remaining =>
        createRound(remaining, nodes.appended(BracketNode.NewMatch(3, a, b)))
      case last :: Nil => nodes.appended(BracketNode.Bye(last))
      case Nil         => nodes
    }

}
