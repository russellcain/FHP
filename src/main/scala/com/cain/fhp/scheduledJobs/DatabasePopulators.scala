package com.cain.fhp.scheduledJobs

import com.cain.fhp.models.teamStats.{IndvPlayerStatsCall, TeamBreakdown, TeamListings}
import com.cain.fhp.persistence.PlayerPersistence
import com.cain.fhp.util.NHLapiRoutes._
import com.cain.fhp.models.teamStats._
import com.cain.fhp.persistence.Tables._
import com.cain.fhp.persistence.application.Database._
import com.cain.fhp.persistence._
import com.cain.fhp.route.Main.AppStarter._

import scala.collection.mutable.ListBuffer
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}
import play.api.libs.json.{JsString, Json}

object DatabasePopulators {

	case class PlayerStats(goals: Long, assists: Long, wins: Long)
	
	def makeHTTPScall(url: String): String = {
		val tempSource = scala.io.Source.fromURL(url) //extended so as to close the Source
		val returnedString = tempSource.mkString
		tempSource.close()
		returnedString
	}

	def addPlayersToDB(): Int = {
		var addedRows: Int = 0
		val teamJSONrep = Json.parse(makeHTTPScall(allTeams))
		val teamListingsJSON = Some(Json.fromJson[TeamListings](teamJSONrep).get)
		if(teamListingsJSON.isDefined){
			val teamIds: List[Int] = teamListingsJSON.get.teams.map(_.id)
			var newRecords: ListBuffer[PlayersRow] = ListBuffer()
			teamIds.foreach(teamId => {
				val teamDetails = Some(Json.fromJson[TeamBreakdown](Json.parse(makeHTTPScall(teamRosterByID(teamId)))).get)
				if(teamDetails.isDefined){
					teamDetails.get.teams.foreach(team => {
						team.roster.roster.foreach(rosterElement => {
							val playerID = rosterElement.person.id
							val name = rosterElement.person.fullName
							// val teamID = teamId
							val position = rosterElement.position.abbreviation
							val jerseyNumber = rosterElement.jerseyNumber
							val playerStats = get2020StatsById(playerID)
							val newRecord = new PlayersRow(
								playerPkid = playerID, 
								name = name, 
								teamId = teamId, 
								position = position,
								number = jerseyNumber.toLong,
								goals = Some(playerStats.goals),
								assists = Some(playerStats.assists),
								wins = Some(playerStats.wins)
							)
							newRecords += newRecord
						})
					})
				}
				println(s"We now have ${newRecords.size} players")			
			})

			newRecords.foreach(newPlayerRow =>{
				PlayerPersistence.single(PlayerPersistence.add(newPlayerRow)).onComplete({
					case Success(x) =>
					  println("added a new player")
					case Failure(exception) =>
					  println("Uhoh, couldn't add player", exception)
				})
			})
			addedRows = newRecords.size
		}
		addedRows
	}

	def get2020StatsById(playerID: Int): PlayerStats = {
		val playerStatsJSONrep = Json.parse(makeHTTPScall(playerStatsByID(playerID)))
		println("first comes in as", playerStatsJSONrep)
		val indvStatsJSON = Some(Json.fromJson[IndvPlayerStatsCall](playerStatsJSONrep))
		println(s"Trying to marshal ${indvStatsJSON}")
		new PlayerStats(1, 1, 1)
	}

	
}