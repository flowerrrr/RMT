package de.flower.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;



/**
 * The Class SecurityContextWrapper.
 * Used to avoid dependencies between HibernateInterceptor and UI-Layer.
 */
public class SecurityContextWrapper {


	/**
	 * Utility classes should not have a public constructor.
	 */
	private SecurityContextWrapper() {

	}

	/**
	 * Gets the currently logged in user that initiated the http-request.
	 * Returns null if request is made from outside user area (like homepage).
	 *
	 * @return the user
	 */
	public static String getUserFromSecurityContext() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null) {
			return null;
		} else {
			return authentication.getName();
		}
	}

	public static SecurityContext getContext() {
		return SecurityContextHolder.getContext();
	}

}
