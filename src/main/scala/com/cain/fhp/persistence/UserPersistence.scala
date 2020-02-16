package com.cain.fhp.persistence

import com.cain.fhp.route.Main.AppStarter._


object UserPersistence extends Persistence {

  import session.profile.api._

  val usersTable = Tables.Users

  def all: Query[Tables.Users, Tables.UsersRow, Seq] = {
    usersTable
  }

  def byId(userPkid: Long): Query[Tables.Users, Tables.UsersRow, Seq] = {
    usersTable.filter(_.userPkid === userPkid)
  }

  def add(users: Tables.UsersRow): DBIO[Long] = {
    ((usersTable returning usersTable.map(_.userPkid)) += users).transactionally
  }

  def update(users: Tables.UsersRow): DBIO[Int] = {
    usersTable.filter(_.userPkid === users.userPkid).update(users)
  }

  def delete(users: Tables.UsersRow): DBIO[Int] = {
    usersTable.filter(_.userPkid === users.userPkid).delete.transactionally
  }

  def deleteAll(): DBIO[Int] = {
    usersTable.delete.transactionally
  }

  def deleteById(userPkid: Long): DBIO[Int] = {
    usersTable.filter(_.userPkid === userPkid).delete.transactionally
  }

}
