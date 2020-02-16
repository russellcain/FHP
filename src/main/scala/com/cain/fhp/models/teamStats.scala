package com.cain.fhp.models

import play.api.libs.json._
//import play.api.libs.functional.syntax._

object teamStats {


  case class TypeBis(
    displayName: String
  )
  object TypeBis {
    implicit val TypeBisType: OFormat[TypeBis] = Json.format[TypeBis]
  }


  // case class IndvStat(
  //   timeOnIce: String,
  //   assists: Double,
  //   goals: Double,
  //   pim: Double,
  //   shots: Double,
  //   games: Double,
  //   hits: Double,
  //   powerPlayGoals: Double,
  //   powerPlayPoints: Double,
  //   powerPlayTimeOnIce: String,
  //   evenTimeOnIce: String,
  //   penaltyMinutes: String,
  //   faceOffPct: Double,
  //   shotPct: Double,
  //   gameWinningGoals: Double,
  //   overTimeGoals: Double,
  //   shortHandedGoals: Double,
  //   shortHandedPoints: Double,
  //   shortHandedTimeOnIce: String,
  //   blocked: Double,
  //   plusMinus: Double,
  //   points: Double,
  //   shifts: Double,
  //   timeOnIcePerGame: String,
  //   evenTimeOnIcePerGame: String,
  //   shortHandedTimeOnIcePerGame: String,
  //   powerPlayTimeOnIcePerGame: String
  // )
  // object IndvStat {

  //   val firstHalf: Reads[(String, Double, Double, Double, Double, Double, Double, Double, Double, String, String, String, Double, Double)] = (
  //       (__ \ "timeOnIce").read[String] and
  //       (__ \ "assists").read[Double] and
  //       (__ \ "goals").read[Double] and
  //       (__ \ "pim").read[Double] and
  //       (__ \ "shots").read[Double] and
  //       (__ \ "games").read[Double] and
  //       (__ \ "hits").read[Double] and
  //       (__ \ "powerPlayGoals").read[Double] and
  //       (__ \ "powerPlayPoints").read[Double] and
  //       (__ \ "powerPlayTimeOnIce").read[String] and
  //       (__ \ "evenTimeOnIce").read[String] and
  //       (__ \ "penaltyMinutes").read[String] and
  //       (__ \ "faceOffPct").read[Double] and
  //       (__ \ "shotPct").read[Double]
  //   ).tupled

  //   val secondHalf: Reads[(Double, Double, Double, Double, String, Double, Double, Double, Double, String, String, String, String)] = (
  //     (__ \ "gameWinningGoals").read[Double] and
  //     (__ \ "overTimeGoals").read[Double] and
  //     (__ \ "shortHandedGoals").read[Double] and
  //     (__ \ "shortHandedPoints").read[Double] and
  //     (__ \ "shortHandedTimeOnIce").read[String] and
  //     (__ \ "blocked").read[Double] and
  //     (__ \ "plusMinus").read[Double] and
  //     (__ \ "points").read[Double] and
  //     (__ \ "shifts").read[Double] and
  //     (__ \ "timeOnIcePerGame").read[String] and
  //     (__ \ "evenTimeOnIcePerGame").read[String] and
  //     (__ \ "shortHandedTimeOnIcePerGame").read[String] and
  //     (__ \ "powerPlayTimeOnIcePerGame").read[String]
  //   ).tupled

  //   val combinedStats: ((String, Double, Double, Double, Double, Double, Double, Double, Double, String, String, String, Double, Double), (Double, Double, Double, Double, String, Double, Double, Double, Double, String, String, String, String)) => IndvStat = {
  //     case (
  //       (timeOnIce, assists, goals, pim, shots, games, hits, powerPlayGoals, powerPlayPoints, powerPlayTimeOnIce, evenTimeOnIce, penaltyMinutes, faceOffPct, shotPct), 
  //       (gameWinningGoals, overTimeGoals, shortHandedGoals, shortHandedPoints, shortHandedTimeOnIce, blocked, plusMinus, points, shifts, timeOnIcePerGame, evenTimeOnIcePerGame, shortHandedTimeOnIcePerGame, powerPlayTimeOnIcePerGame)) 
  //     => IndvStat(timeOnIce, assists, goals, pim, shots, games, hits, powerPlayGoals, powerPlayPoints, powerPlayTimeOnIce, evenTimeOnIce, penaltyMinutes, faceOffPct, shotPct, gameWinningGoals, overTimeGoals, shortHandedGoals, shortHandedPoints, shortHandedTimeOnIce, blocked, plusMinus, points, shifts, timeOnIcePerGame, evenTimeOnIcePerGame, shortHandedTimeOnIcePerGame, powerPlayTimeOnIcePerGame)
  //   }

  //   implicit val IndvStatRead: Reads[IndvStat] = (
  //     firstHalf and secondHalf
  //   ) { combinedStats }

  // }

  case class Splits(
    season: String,
    stat: String // too many fields, so going to interact with this JSON in place
  )
  object Splits {
    implicit val SplitsType: OFormat[Splits] = Json.format[Splits]
  }

  case class Stats(
    `type`: TypeBis,
    splits: List[Splits]
  )
  object Stats {
    implicit val StatsType: OFormat[Stats] = Json.format[Stats]
  }
  case class IndvPlayerStatsCall(
    copyright: String,
    stats: List[Stats]
  )
  object IndvPlayerStatsCall {
    implicit val IndvPlayerStatsCallType: OFormat[IndvPlayerStatsCall] = Json.format[IndvPlayerStatsCall]
  }

  case class TimeZone(
    id: String,
    offset: Int,
    tz: String
  )
  object TimeZone {
    implicit val TimeZoneType: OFormat[TimeZone] = Json.format[TimeZone]
  }

  case class Venue(
    name: String,
    link: String,
    city: String,
    timeZone: TimeZone
  )
  object Venue {
    implicit val VenueType: OFormat[Venue] = Json.format[Venue]
  }

  case class Division(
    id: Int,
    name: String,
    nameShort: String,
    link: String,
    abbreviation: String
  )
  object Division {
    implicit val DivisionType: OFormat[Division] = Json.format[Division]
  }

  case class Conference(
    id: Int,
    name: String,
    link: String
  )
  object Conference {
    implicit val ConferenceType: OFormat[Conference] = Json.format[Conference]
  }

  case class Franchise(
    franchiseId: Int,
    teamName: String,
    link: String
  )
  object Franchise {
    implicit val FranchiseType: OFormat[Franchise] = Json.format[Franchise]
  }

  case class Person(
    id: Int,
    fullName: String,
    link: String
  )
  object Person {
    implicit val PersonType: OFormat[Person] = Json.format[Person]
  }

  case class Position(
    code: String,
    name: String,
    `type`: String,
    abbreviation: String
  )
  object Position {
    implicit val PositionType: OFormat[Position] = Json.format[Position]
  }

  case class Roster(
    person: Person,
    jerseyNumber: String,
    position: Position
  )
  object Roster {
    implicit val RosterType: OFormat[Roster] = Json.format[Roster]
  }

  case class RosterList(
    roster: List[Roster],
    link: String
  )
  object RosterList {
    implicit val RosterListType: OFormat[RosterList] = Json.format[RosterList]
  }

  case class TeamsWithRoster(
    id: Int,
    name: String,
    link: String,
    venue: Venue,
    abbreviation: String,
    teamName: String,
    locationName: String,
    firstYearOfPlay: String,
    division: Division,
    conference: Conference,
    franchise: Franchise,
    roster: RosterList,
    shortName: String,
    officialSiteUrl: String,
    franchiseId: Int,
    active: Boolean
  )
  object TeamsWithRoster {
    implicit val TeamsType: OFormat[TeamsWithRoster] = Json.format[TeamsWithRoster]
  }

  case class TeamBreakdown(
    copyright: String,
    teams: List[TeamsWithRoster]
  )
  object TeamBreakdown {
    implicit val TeamBreakdownType: OFormat[TeamBreakdown] = Json.format[TeamBreakdown]
  }

  case class TeamOverviews(
    id: Int,
    name: String,
    link: String,
    venue: Venue,
    abbreviation: String,
    teamName: String,
    locationName: String,
    firstYearOfPlay: String,
    division: Division,
    conference: Conference,
    franchise: Franchise,
    shortName: String,
    officialSiteUrl: String,
    franchiseId: Int,
    active: Boolean
  )
  object TeamOverviews {
    implicit val TeamOverviewsType: OFormat[TeamOverviews] = Json.format[TeamOverviews]
  }

  case class TeamListings(
    copyright: String,
    teams: List[TeamOverviews]
  )
  object TeamListings {
    implicit val TeamListingsType: OFormat[TeamListings] = Json.format[TeamListings]
  }
}