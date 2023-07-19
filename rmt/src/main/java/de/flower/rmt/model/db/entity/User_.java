package de.flower.rmt.model.db.entity;

import de.flower.rmt.model.db.entity.User.Status;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.joda.time.DateTime;

@StaticMetamodel(User.class)
public abstract class User_ extends AbstractClubRelatedEntity_ {

	public static volatile SingularAttribute<User, DateTime> lastLogin;
	public static volatile SingularAttribute<User, String> phoneNumber;
	public static volatile SingularAttribute<User, String> initialPassword;
	public static volatile ListAttribute<User, Player> players;
	public static volatile SetAttribute<User, Role> roles;
	public static volatile SingularAttribute<User, String> secondEmail;
	public static volatile SingularAttribute<User, String> fullname;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Boolean> enabled;
	public static volatile SingularAttribute<User, Boolean> invitationSent;
	public static volatile SingularAttribute<User, String> encryptedPassword;
	public static volatile SingularAttribute<User, Status> status;

}

