package de.flower.test

import org.springframework.stereotype.Service
import de.flower.rmt.model.{Team, Club}
import org.springframework.beans.factory.annotation.Autowired
import scala.Predef._
import de.flower.rmt.service.{ITeamManager, TeamManager}
import de.flower.rmt.repository.{ITeamRepo, IClubRepo}
import de.flower.common.util.Check._
import org.apache.commons.lang3.Validate

/**
 * 
 * @author oblume
 */

@Service
class TestData {

    @Autowired
    var teamRepo: ITeamRepo = _

    @Autowired
    var clubRepo: IClubRepo = _

    def getClub(): Club = {
        // load some often used entities
        return Validate.notNull(clubRepo.findOne(1L))

    }

    def getJuveAmateure(): Team = {
        return Validate.notNull(teamRepo.findOne(1L))
    }

}