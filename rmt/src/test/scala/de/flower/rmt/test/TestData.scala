package de.flower.rmt.test

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired
import scala.Predef._
import de.flower.rmt.model.event.{EventType, Event}
import java.util.Date
import org.joda.time.LocalTime
import de.flower.rmt.model._
import de.flower.rmt.service.{IUserManager, ITeamManager, IResponseManager, IEventManager}
import org.apache.commons.lang3.{RandomStringUtils, Validate}
import scala.collection.JavaConversions._
import collection.mutable.ListBuffer
import de.flower.rmt.repository.{IPlayerRepo, IEventRepo, ITeamRepo, IClubRepo}

/**
 *
 * @author flowerrrr
 */

@Service
class TestData {

    @Autowired
    var teamRepo: ITeamRepo = _

    @Autowired
    var clubRepo: IClubRepo = _

    @Autowired
    var eventRepo: IEventRepo = _

    @Autowired
    var playerRepo: IPlayerRepo = _

    @Autowired
    var teamManager: ITeamManager = _

    @Autowired
    var eventManager: IEventManager = _

    @Autowired
    var responseManager: IResponseManager = _

    @Autowired
    var userManager: IUserManager = _

    def getClub(): Club = {
        // load some often used entities
        return Validate.notNull(clubRepo.findOne(1L))
    }

    def getJuveAmateure(): Team = {
        return Validate.notNull(teamRepo.findOne(1L))
    }

    def getTeamWithPlayer(name: String): Team = {
        var team = teamRepo.findByNameAndClub(name, getClub())
        if (team == null) {
            team = createTeam(name)
            val users = createUsers(20)
            teamManager.addPlayers(team, users)
        }
        return team;
    }

    def createTeam(name: String): Team = {
        val team = teamManager.newTeamInstance();
        team.setName(name)
        teamManager.save(team);
        return team
    }

    def createUsers(count: Int): ListBuffer[User] = {
        var users = ListBuffer[User]()
        for (i <- 1 until count) {
            val user = userManager.newUserInstance()
            user.setEmail(RandomStringUtils.randomAlphabetic(8) + "@acme.com")
            user.setFullname(RandomStringUtils.randomAlphabetic(10))
            userManager.save(user)
            users.add(user)
        }
        return users
    }

    def getEvent(): Event = {
        val event = eventManager.newInstance(EventType.Training)
        event.setDate(new Date())
        event.setTime(LocalTime.now())
        event.setSummary("Summary")
        event.setTeam(getTeamWithPlayer("Socca Five 2"))
        eventManager.save(event)
        return event
    }

    /**
     * Creates an event with some responses.
     */
    def getEventWithResponses(): Event = {
        val event = getEvent();
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(15), RSVPStatus.ACCEPTED, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(1), RSVPStatus.DECLINED, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(2), RSVPStatus.DECLINED, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(3), RSVPStatus.ACCEPTED, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(4), RSVPStatus.UNSURE, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(5), RSVPStatus.UNSURE, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(10), RSVPStatus.ACCEPTED, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(11), RSVPStatus.ACCEPTED, "some comment")
        responseManager.respond(event, playerRepo.findByTeam(event.getTeam()).get(12), RSVPStatus.ACCEPTED, "some comment")
        return event
    }
}