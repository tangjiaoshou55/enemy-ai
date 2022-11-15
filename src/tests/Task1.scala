package tests

import game.enemyai.{AIPlayer, PlayerLocation}
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.maps.GridLocation
import org.scalatest._

class Task1 extends FunSuite {


  test("1-test locatePlayer") {
    val player: AIPlayer = new AIPlayer("Player1")
    var list: LinkedListNode[PlayerLocation] = new LinkedListNode(
      new PlayerLocation(8.0, 4.0, "Player2"), null)
    list = new LinkedListNode(
      new PlayerLocation(10.0, 1.0, "Player1"), list
    )
    val actual: PlayerLocation = player.locatePlayer("Player2", list)
    assert(actual.playerId == "Player2")
    assert(Math.abs(actual.x - 8.0) < 0.0001)
    assert(Math.abs(actual.y - 4.0) < 0.0001)
  }

  test("2-find closest player") {
    val player: AIPlayer = new AIPlayer("Player1")

    var list: LinkedListNode[PlayerLocation] = new LinkedListNode(
      new PlayerLocation(1.0, 3.0, "Player2"), null
    )
    list = new LinkedListNode(
      new PlayerLocation(1.0, 2.0, "Player1"), list
    )
    list = new LinkedListNode[PlayerLocation](
      new PlayerLocation(10.0, 10.0, "Player3"), list
    )

    val actual: PlayerLocation = player.closestPlayer(list)
    assert(actual.playerId == "Player2")
    assert(Math.abs(actual.x - 1.0) < 0.0001)
    assert(Math.abs(actual.y - 3.0) < 0.0001)
  }

  test("3. test computePath") {
    val player: AIPlayer = new AIPlayer("Player1")
    val path = player.computePath(new GridLocation(3, 7), new GridLocation(3, 8))

    var list = new LinkedListNode[GridLocation](new GridLocation(3, 8), null)
    list = new LinkedListNode[GridLocation](new GridLocation(3, 7), list)

    assert(path.value == list.value)
    assert(path.next.value == list.next.value)
    assert(path.size() == 2)
  }

  test("4. test computePath") {
    val player: AIPlayer = new AIPlayer("Player1")
    val path = player.computePath(new GridLocation(-3, -7), new GridLocation(-3, -8))

    assert(path.size() == 2)
  }

  test("5. test computePath") {
    val player: AIPlayer = new AIPlayer("Player1")
    val path = player.computePath(new GridLocation(-2, -2), new GridLocation(2, -3))

    assert(path.size() == 6)
  }

  test("6. test reversed computePath") {
    val player: AIPlayer = new AIPlayer("Player1")
    val path1 = player.computePath(new GridLocation(1, 3), new GridLocation(2, 2))

    assert(path1.size() == 3)

    val player2: AIPlayer = new AIPlayer("Player2")
    val path2 = player2.computePath(new GridLocation(2, 2), new GridLocation(1, 3))

    assert(path2.size() == 3)
  }

}
