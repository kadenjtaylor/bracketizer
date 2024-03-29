import com.raquo.laminar.api.L.{*, given}

object ViewModel {

  enum Stage:
    case CreateMetadata()
    case Signup()
    case Running()
    case Finished()

  // Stage 1
  opaque type Dollars = Int

  enum TournamentStyle:
    case RoundRobin(rounds: Int)
    case SingleElimination()
    case DoubleElimination()

  case class Metadata(buyIn: Dollars, style: TournamentStyle)

  // Stage 2
  case class Player(name: String)

  case class Setup(m: Metadata, boughtIn: Set[Player], signedUp: Set[Player])

  // Stage 3
  opaque type MatchId = String

  enum Ref:
    case WinnerOf(id: MatchId)
    case LoserOf(id: MatchId)

  type Slot = Player | Ref

  case class Matchup(id: MatchId, p1: Slot, p2: Slot)

  enum Event:
    case Log(msg: String)
    case MatchComplete(id: MatchId, winner: Player, loser: Player)

  case class Error(msg: String) extends RuntimeException

  trait Tournament {
    // You should also be able to see what's already happened
    def history: List[Event]
    // There should be some indication of current state or what's supposed to happen now
    def ready: List[Matchup]
    // A tournament is changed when significant legal events occur
    def update(ev: Event): Either[Error, Tournament]
  }

  trait RenderableTournament extends Tournament {
    override def update(ev: Event): Either[Error, RenderableTournament]

    def render: HtmlElement
  }

  // idea
  val p1 = Player("Kaden")
  val p2 = Player("Greg")
  val m1 = Matchup("kg1", p1, p2)

  val p3 = Player("John")
  val p4 = Player("Paul")
  val m2 = Matchup("jp1", p3, p4)

  val m3 = Matchup("w1w2", Ref.WinnerOf(m1.id), Ref.WinnerOf(m2.id))

  val d: Tournament = ???
  val nextSteps     = d.ready
  val history       = d.history
  val updated       = d.update(Event.Log("It works?"))

  // case class SingleEliminationBracket() extends Tournament
}
