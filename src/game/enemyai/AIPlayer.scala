package game.enemyai

import game.enemyai.decisiontree.DecisionTreeValue
import game.lo4_data_structures.linkedlist.LinkedListNode
import game.lo4_data_structures.trees.BinaryTreeNode
import game.maps.GridLocation
import game.{AIAction, MovePlayer}

class AIPlayer(val id: String) {


  // TODO: Replace this placeholder code with your own
  def locatePlayer(playerId: String, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {

    if (playerLocations.value.playerId == playerId) {
      return new PlayerLocation(playerLocations.value.x, playerLocations.value.y, playerId)
    }else{
      return locatePlayer(playerId, playerLocations.next)
    }
  }

  // TODO: Replace this placeholder code with your own
  def closestPlayer(playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation = {

    val myLocation = locatePlayer(id, playerLocations)

    return helper(myLocation, playerLocations)
  }

  def distance(player1:PlayerLocation, player2:PlayerLocation): Double ={

    return Math.sqrt(Math.pow(player2.x - player1.x, 2) + Math.pow(player2.y - player1.y, 2))
  }

  def helper(myLocation:PlayerLocation, playerLocations: LinkedListNode[PlayerLocation]): PlayerLocation ={

    if (playerLocations == null){
      return new PlayerLocation(99999999, 99999999, "imagePlayerId")

    }else{
      val recursive = helper(myLocation, playerLocations.next)

      if(playerLocations.value.playerId == this.id){
        return recursive
      }else {
        val minimum = distance(myLocation, recursive)
        val current = distance(myLocation, playerLocations.value)

        if (current < minimum){
          return playerLocations.value
        } else{
          return recursive
        }
      }
    }
  }


  // TODO: Replace this placeholder code with your own
  def computePath(start: GridLocation, end: GridLocation): LinkedListNode[GridLocation] = {


    val interceptX = start.x - end.x
    val interceptY = start.y - end.y
    var Final: LinkedListNode[GridLocation] = new LinkedListNode[GridLocation](new GridLocation(start.x, start.y), null)

    MoveHelper(interceptX, interceptY, start, Final)
    return Final
  }

  def mover(x: Int, y: Int, interceptX:Int, interceptY: Int, Final: LinkedListNode[GridLocation]): Unit={
    val move = new GridLocation(x, y)
    Final.insert(move)
    MoveHelper(interceptX, interceptY, move, Final)
  }

  def MoveHelper (interceptX:Int, interceptY: Int, start: GridLocation, Final: LinkedListNode[GridLocation]): Unit= {

    if (interceptX == 0 && interceptY == 0){

    } else if (interceptX < 0) {
      mover(start.x + 1, start.y, interceptX + 1, interceptY, Final)
    } else if (interceptX > 0) {
      mover(start.x - 1, start.y, interceptX - 1, interceptY, Final)
    }else if (interceptY < 0) {
      mover(start.x, start.y + 1, interceptX, interceptY + 1, Final)
    }else {
      mover(start.x, start.y - 1, interceptX, interceptY - 1, Final)
    }
  }


  // TODO: Replace this placeholder code with your own
  def makeDecision(gameState: AIGameState, decisionTree: BinaryTreeNode[DecisionTreeValue]): AIAction = {
    MovePlayer(this.id, Math.random() - 0.5, Math.random() - 0.5)

    if (decisionTree.value.check(gameState) > 0) {
      makeDecision(gameState, decisionTree.right)
    } else if (decisionTree.value.check(gameState) < 0) {
      makeDecision(gameState, decisionTree.left)
    } else {
      decisionTree.value.action(gameState)
    }
  }



  // TODO: Replace this placeholder code with your own
  def closestPlayerAvoidWalls(gameState: AIGameState): PlayerLocation = {
    closestPlayer(gameState.playerLocations)
  }

  // TODO: Replace this placeholder code with your own
  def getPath(gameState: AIGameState): LinkedListNode[GridLocation] = {
    computePath(locatePlayer(this.id, gameState.playerLocations).asGridLocation(), closestPlayerAvoidWalls(gameState).asGridLocation())
  }




}

