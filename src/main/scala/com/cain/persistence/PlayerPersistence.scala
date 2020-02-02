package com.cain.persistence
import com.cain.route.Main.AppStarter._


object PlayerPersistence extends Persistence {

  import session.profile.api._

  val playersTable = Tables.Players

  def all: Query[Tables.Players, Tables.PlayersRow, Seq] = {
    playersTable
  }

  def byId(playerPkid: Long): Query[Tables.Players, Tables.PlayersRow, Seq] = {
    playersTable.filter(_.playerPkid === playerPkid)
  }

  def add(players: Tables.PlayersRow): DBIO[Long] = {
    ((playersTable returning playersTable.map(_.playerPkid)) += players).transactionally
  }

  def update(players: Tables.PlayersRow): DBIO[Int] = {
    playersTable.filter(_.playerPkid === players.playerPkid).update(players)
  }

  def delete(players: Tables.PlayersRow): DBIO[Int] = {
    playersTable.filter(_.playerPkid === players.playerPkid).delete.transactionally
  }

  def deleteAll(): DBIO[Int] = {
    playersTable.delete.transactionally
  }

  def deleteById(playerPkid: Long): DBIO[Int] = {
    playersTable.filter(_.playerPkid === playerPkid).delete.transactionally
  }

}
