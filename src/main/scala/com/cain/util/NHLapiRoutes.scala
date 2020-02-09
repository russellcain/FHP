package com.cain.util

object NHLapiRoutes {

	val allTeams: String = "https://statsapi.web.nhl.com/api/v1/teams"
	
	def TeamRosterByID(teamID: Int): String = s"https://statsapi.web.nhl.com/api/v1/teams/${teamID}?expand=team.roster"
	
	val lastNightsGames: String = "https://statsapi.web.nhl.com/api/v1/schedule"

	def boxscoreByGameID(gameID: Int): String = s"https://statsapi.web.nhl.com/api/v1/game/${gameID}/boxscore"
}