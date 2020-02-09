package com.cain.scheduledJobs

import com.cain.util.NHLapiRoutes._
import com.cain.models.teamStats._
import com.cain.persistence.Tables._
import com.cain.persistence.application.Database._
import com.cain.persistence._
import com.cain.route.Main.AppStarter._

import scala.collection.mutable.ListBuffer

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}

import play.api.libs.json.{JsString, Json}

object Main {
	
	def get(url: String): String = scala.io.Source.fromURL(url).mkString

	def addPlayersToDB: Int = {
		var addedRows: Int = 0
		val teamJSONrep = Json.parse(get(allTeams))
		val teamListingsJSON = Some(Json.fromJson[TeamListings](teamJSONrep).get)
		if(teamListingsJSON.isDefined){
			val teamIds: List[Int] = teamListingsJSON.get.teams.map(_.id)
			var newRecords: ListBuffer[PlayersRow] = ListBuffer()
			teamIds.foreach(teamId => {
				val teamDetails = Some(Json.fromJson[TeamBreakdown](Json.parse(get(TeamRosterByID(teamId)))).get)
				if(teamDetails.isDefined){
					teamDetails.get.teams.foreach(team => {
						team.roster.roster.foreach(rosterElement => {
							val playerID = rosterElement.person.id
							val name = rosterElement.person.fullName
							// val teamID = teamId
							val position = rosterElement.position.abbreviation
							val jerseyNumber = rosterElement.jerseyNumber
							val newRecord = new PlayersRow(
								playerPkid = playerID, 
								name = name, 
								teamId = teamId, 
								position = position,
								number = jerseyNumber.toLong
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

	
}