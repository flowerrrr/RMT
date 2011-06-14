package de.flower.common.validation;

/**
 * @author oblume
 */
import de.flower.common.util.logging.Slf4jUtil;
import de.flower.rmt.repository.ITeamRepo;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueValidator /*extends SessionAwareConstraintValidator<String> */implements ConstraintValidator<Unique, String> {

    private final static Logger log = Slf4jUtil.getLogger();

    private String[] fields;

    @Autowired
    private ITeamRepo teamRepo;

    public UniqueValidator() {
        log.debug("foobar");
    }

    public void initialize(Unique constraintAnnotation) {
        this.fields = constraintAnnotation.fields();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        assert (teamRepo != null);
        return true;
    }

 /*   @Override
    public boolean isValidInSession(String value, ConstraintValidatorContext context) {
        assert (getTmpSession() != null);
        return true;
    }*/
}