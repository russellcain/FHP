package com.cain.models

import play.api.libs.json.{Json, OFormat}

object teamStats {

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