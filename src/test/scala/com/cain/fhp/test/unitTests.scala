package com.cain.fhp.test

import org.scalatest.FlatSpec

import com.cain.fhp.scheduledJobs.DatabasePopulators._

class unitTests extends FlatSpec {

  "All active NHL players" should "be uploaded into the database" in {
    // There are currently 771 players registered to NHL teams. Let's try to add them all to the db
    // assert(addPlayersToDB == 771)
    println("Let's look at baby burt's stats:")
    get2020StatsById(8477479)
  }
}