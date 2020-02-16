package com.cain.fhp.util

object NHLapiRoutes {

	val allTeams: String = "https://statsapi.web.nhl.com/api/v1/teams"
	
	def teamRosterByID(teamID: Int): String = s"https://statsapi.web.nhl.com/api/v1/teams/${teamID}?expand=team.roster"

	def playerStatsByID(playerID: Int): String = s"https://statsapi.web.nhl.com/api/v1/people/${playerID}/stats?stats=statsSingleSeason&season=20192020"
	
	val lastNightsGames: String = "https://statsapi.web.nhl.com/api/v1/schedule"

	def boxscoreByGameID(gameID: Int): String = s"https://statsapi.web.nhl.com/api/v1/game/${gameID}/boxscore"
}