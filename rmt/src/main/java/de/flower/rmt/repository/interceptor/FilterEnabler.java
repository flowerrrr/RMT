package de.flower.rmt.repository.interceptor;

import de.flower.common.util.logging.Slf4jUtil;
import org.hibernate.EmptyInterceptor;
import org.hibernate.Transaction;
import org.slf4j.Logger;

/**
 * @author oblume
 */
public class FilterEnabler extends EmptyInterceptor {

    private final static Logger log = Slf4jUtil.getLogger();


    @Override
    public void afterTransactionBegin(Transaction tx) {

        // need Session and current Club-Id
        log.info("§§§§§§§§§§§§§§§§§§§§§§§§§§§§§§ Interceptor called ");
    }
}
