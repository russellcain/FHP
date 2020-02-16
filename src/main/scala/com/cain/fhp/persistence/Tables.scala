package com.cain.fhp.persistence

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile
  import profile.api._
  import slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import slick.jdbc.{GetResult => GR}

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription = Players.schema ++ Teams.schema ++ Users.schema
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  /** Entity class storing rows of table Players
   *  @param playerPkid Database column PLAYER_PKID SqlType(int8), PrimaryKey
   *  @param name Database column Name SqlType(varchar), Length(150,true)
   *  @param teamId Database column Team_ID SqlType(int8)
   *  @param position Database column Position SqlType(varchar), Length(5,true)
   *  @param number Database column Number SqlType(int8)
   *  @param ownedby Database column OwnedBy SqlType(int8), Default(None)
   *  @param goals Database column Goals SqlType(int8), Default(None)
   *  @param assists Database column Assists SqlType(int8), Default(None)
   *  @param wins Database column Wins SqlType(int8), Default(None)
   *  @param salary Database column Salary SqlType(int8), Default(None) */
  case class PlayersRow(playerPkid: Long, name: String, teamId: Long, position: String, number: Long, ownedby: Option[Long] = None, goals: Option[Long] = None, assists: Option[Long] = None, wins: Option[Long] = None, salary: Option[Long] = None)
  /** GetResult implicit for fetching PlayersRow objects using plain SQL queries */
  implicit def GetResultPlayersRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Long]]): GR[PlayersRow] = GR{
    prs => import prs._
    PlayersRow.tupled((<<[Long], <<[String], <<[Long], <<[String], <<[Long], <<?[Long], <<?[Long], <<?[Long], <<?[Long], <<?[Long]))
  }
  /** Table description of table players. Objects of this class serve as prototypes for rows in queries. */
  class Players(_tableTag: Tag) extends profile.api.Table[PlayersRow](_tableTag, Some("local"), "players") {
    def * = (playerPkid, name, teamId, position, number, ownedby, goals, assists, wins, salary) <> (PlayersRow.tupled, PlayersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(playerPkid), Rep.Some(name), Rep.Some(teamId), Rep.Some(position), Rep.Some(number), ownedby, goals, assists, wins, salary)).shaped.<>({r=>import r._; _1.map(_=> PlayersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get, _6, _7, _8, _9, _10)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column PLAYER_PKID SqlType(int8), PrimaryKey */
    val playerPkid: Rep[Long] = column[Long]("PLAYER_PKID", O.PrimaryKey)
    /** Database column Name SqlType(varchar), Length(150,true) */
    val name: Rep[String] = column[String]("Name", O.Length(150,varying=true))
    /** Database column Team_ID SqlType(int8) */
    val teamId: Rep[Long] = column[Long]("Team_ID")
    /** Database column Position SqlType(varchar), Length(5,true) */
    val position: Rep[String] = column[String]("Position", O.Length(5,varying=true))
    /** Database column Number SqlType(int8) */
    val number: Rep[Long] = column[Long]("Number")
    /** Database column OwnedBy SqlType(int8), Default(None) */
    val ownedby: Rep[Option[Long]] = column[Option[Long]]("OwnedBy", O.Default(None))
    /** Database column Goals SqlType(int8), Default(None) */
    val goals: Rep[Option[Long]] = column[Option[Long]]("Goals", O.Default(None))
    /** Database column Assists SqlType(int8), Default(None) */
    val assists: Rep[Option[Long]] = column[Option[Long]]("Assists", O.Default(None))
    /** Database column Wins SqlType(int8), Default(None) */
    val wins: Rep[Option[Long]] = column[Option[Long]]("Wins", O.Default(None))
    /** Database column Salary SqlType(int8), Default(None) */
    val salary: Rep[Option[Long]] = column[Option[Long]]("Salary", O.Default(None))
  }
  /** Collection-like TableQuery object for table Players */
  lazy val Players = new TableQuery(tag => new Players(tag))

  /** Entity class storing rows of table Teams
   *  @param teamPkid Database column TEAM_PKID SqlType(int8), PrimaryKey
   *  @param name Database column Name SqlType(varchar), Length(150,true)
   *  @param points Database column Points SqlType(int8)
   *  @param cap Database column Cap SqlType(int8)
   *  @param playerIds Database column Player_IDs SqlType(varchar), Length(150,true) */
  case class TeamsRow(teamPkid: Long, name: String, points: Long, cap: Long, playerIds: String)
  /** GetResult implicit for fetching TeamsRow objects using plain SQL queries */
  implicit def GetResultTeamsRow(implicit e0: GR[Long], e1: GR[String]): GR[TeamsRow] = GR{
    prs => import prs._
    TeamsRow.tupled((<<[Long], <<[String], <<[Long], <<[Long], <<[String]))
  }
  /** Table description of table teams. Objects of this class serve as prototypes for rows in queries. */
  class Teams(_tableTag: Tag) extends profile.api.Table[TeamsRow](_tableTag, Some("local"), "teams") {
    def * = (teamPkid, name, points, cap, playerIds) <> (TeamsRow.tupled, TeamsRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(teamPkid), Rep.Some(name), Rep.Some(points), Rep.Some(cap), Rep.Some(playerIds))).shaped.<>({r=>import r._; _1.map(_=> TeamsRow.tupled((_1.get, _2.get, _3.get, _4.get, _5.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column TEAM_PKID SqlType(int8), PrimaryKey */
    val teamPkid: Rep[Long] = column[Long]("TEAM_PKID", O.PrimaryKey)
    /** Database column Name SqlType(varchar), Length(150,true) */
    val name: Rep[String] = column[String]("Name", O.Length(150,varying=true))
    /** Database column Points SqlType(int8) */
    val points: Rep[Long] = column[Long]("Points")
    /** Database column Cap SqlType(int8) */
    val cap: Rep[Long] = column[Long]("Cap")
    /** Database column Player_IDs SqlType(varchar), Length(150,true) */
    val playerIds: Rep[String] = column[String]("Player_IDs", O.Length(150,varying=true))
  }
  /** Collection-like TableQuery object for table Teams */
  lazy val Teams = new TableQuery(tag => new Teams(tag))

  /** Entity class storing rows of table Users
   *  @param userPkid Database column USER_PKID SqlType(int8), PrimaryKey
   *  @param username Database column Username SqlType(varchar), Length(150,true)
   *  @param password Database column Password SqlType(varchar), Length(150,true)
   *  @param email Database column Email SqlType(varchar), Length(150,true)
   *  @param teamid Database column TeamID SqlType(int8), Default(None) */
  case class UsersRow(userPkid: Long, username: String, password: String, email: String, teamid: Option[Long] = None)
  /** GetResult implicit for fetching UsersRow objects using plain SQL queries */
  implicit def GetResultUsersRow(implicit e0: GR[Long], e1: GR[String], e2: GR[Option[Long]]): GR[UsersRow] = GR{
    prs => import prs._
    UsersRow.tupled((<<[Long], <<[String], <<[String], <<[String], <<?[Long]))
  }
  /** Table description of table users. Objects of this class serve as prototypes for rows in queries. */
  class Users(_tableTag: Tag) extends profile.api.Table[UsersRow](_tableTag, Some("local"), "users") {
    def * = (userPkid, username, password, email, teamid) <> (UsersRow.tupled, UsersRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(userPkid), Rep.Some(username), Rep.Some(password), Rep.Some(email), teamid)).shaped.<>({r=>import r._; _1.map(_=> UsersRow.tupled((_1.get, _2.get, _3.get, _4.get, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column USER_PKID SqlType(int8), PrimaryKey */
    val userPkid: Rep[Long] = column[Long]("USER_PKID", O.PrimaryKey)
    /** Database column Username SqlType(varchar), Length(150,true) */
    val username: Rep[String] = column[String]("Username", O.Length(150,varying=true))
    /** Database column Password SqlType(varchar), Length(150,true) */
    val password: Rep[String] = column[String]("Password", O.Length(150,varying=true))
    /** Database column Email SqlType(varchar), Length(150,true) */
    val email: Rep[String] = column[String]("Email", O.Length(150,varying=true))
    /** Database column TeamID SqlType(int8), Default(None) */
    val teamid: Rep[Option[Long]] = column[Option[Long]]("TeamID", O.Default(None))
  }
  /** Collection-like TableQuery object for table Users */
  lazy val Users = new TableQuery(tag => new Users(tag))
}
