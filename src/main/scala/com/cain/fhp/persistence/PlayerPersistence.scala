package com.cain.fhp.persistence

import com.cain.fhp.route.Main.AppStarter._
import slick.profile._


object PlayerPersistence extends Persistence {

  import session.profile.api._
  import slick.driver._

  val playersTable = Tables.Players

  def all: Query[Tables.Players, Tables.PlayersRow, Seq] = {
    playersTable
  }

  def byId(playerPkid: Long): Query[Tables.Players, Tables.PlayersRow, Seq] = {
    val returnedPlayer = playersTable.filter(_.playerPkid === playerPkid)
    println("FOUND:",returnedPlayer)
    returnedPlayer
  }

  def byLikeName(queryString: String): Query[Tables.Players, Tables.PlayersRow, Seq] = {
    val returnedPlayer = playersTable.filter(_.name.toLowerCase like s"%${queryString.toLowerCase}%") // wrap in % for SQL like
    println("FOUND player? ", returnedPlayer.exists)
    returnedPlayer
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
