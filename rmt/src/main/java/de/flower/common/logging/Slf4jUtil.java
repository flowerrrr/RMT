package de.flower.common.logging;

import org.slf4j.Logger;

/**
 * @author oblume
 */
public class Slf4jUtil {

    private Slf4jUtil() {

    }

	public static Logger getLogger() {
		Throwable t = new Throwable();
		StackTraceElement directCaller = t.getStackTrace()[1];
		return org.slf4j.LoggerFactory.getLogger( directCaller.getClassName() );
	}

}

