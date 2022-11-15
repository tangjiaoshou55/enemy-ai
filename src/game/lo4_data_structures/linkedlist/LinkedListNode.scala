package game.lo4_data_structures.linkedlist

import scala.annotation.tailrec

// Simple Linked List implementation (note: Cannot represent empty list)
class LinkedListNode[A](var value: A, var next: LinkedListNode[A]) {


  /**
   * returns the number of nodes in the list starting with this node
   */
  @tailrec
  final def sizeTailRec(accumulatedSize: Int): Int = {
    if (this.next == null) {
      accumulatedSize + 1
    } else {
      this.next.sizeTailRec(accumulatedSize + 1)
    }
  }


  def size(): Int = {
    sizeTailRec(0)
  }

  def insert(item: A):Unit = {
    if (this.next == null) {
      this.next = new LinkedListNode[A](item, null)
    }else {
      this.next.insert(item)
    }
  }

  override def toString: String = {
    if (this.next == null) {
      this.value.toString
    } else {
      this.value.toString + ", " + this.next.toString
    }
  }


}
