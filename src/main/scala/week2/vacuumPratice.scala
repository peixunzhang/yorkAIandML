package week2

final case class Room(id: Int, dirt: Boolean, left: Option[Int], right: Option[Int]) {
  def vacuum: Room = copy(dirt = false)
 // def done: Boolean = left.fold(true)(x => !x.dirt) && right.fold(true)(r => !r.dirt) && !dirt
 }

sealed trait Action

object Action {
  case object  Vacuum extends Action
  case object  MoveLeft extends Action
  case object MoveRight extends Action
}

final case class World(currentRoom: Int, rooms: Map[Int, Room]) {
  def done: Boolean =
    rooms.forall(!_._2.dirt)

  def actions: List[(Action, World)] = {
    rooms.get(currentRoom).toList.flatMap { room =>
      val moveLeft = room.left.map(id => (Action.MoveLeft, copy(currentRoom = id)))
      val moveRight = room.right.map(id => (Action.MoveRight, copy(currentRoom = id)))
      val clean = 
        if (room.dirt)
          Some((Action.Vacuum, copy(rooms = rooms.updated(room.id, room.copy(dirt = false)))))
        else
          None
      List(moveLeft, moveRight, clean).flatten
    }
  }
}

object Main extends App {
  def bfs[S, A](state: S, actions: S => List[(A, S)], done: S => Boolean): Option[List[A]] = {
    def go(state: S, actionsTaken: List[A], statesToExplore: List[(List[A], S)]): Option[List[A]] =
      if (done(state)) 
        Some(actionsTaken.reverse) 
      else {
        val nextStates = actions(state).map { case (a,s) => (a :: actionsTaken, s) }
        val nextSearch = statesToExplore ++ nextStates
        nextSearch match {
          case (acc, nextState) :: rest => go(nextState, acc, rest)
          case Nil => None
        }
      }
    go(state, Nil, Nil)
  }
  val world = World(2, List(Room(1, false, None, Some(2)), Room(2, true, Some(1), None)).map(r => (r.id, r)).toMap)
  print(bfs[World, Action](world, _.actions, _.done))
}
