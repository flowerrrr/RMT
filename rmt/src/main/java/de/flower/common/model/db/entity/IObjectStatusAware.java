package de.flower.common.model.db.entity;

import de.flower.common.model.db.type.ObjectStatus;

/**
 * Should be implemented by classes which status is manageable by a <code>ObjectStatus</code> enum.
 */
public interface IObjectStatusAware {

	/**
	 * Gets the object status.
	 * 
	 * @return the object status
	 * @deprecated Use #isDeleted or #isActive if testing for a specific object state
	 */
	@Deprecated
    ObjectStatus getObjectStatus();

	boolean isDeleted();

	boolean isFixed();

	/**
	 * Sets the object status.
	 * 
	 * @param objectStatus
	 *            the object status to set
	 * @deprecated Use #deactivate and #activate whenever possible. Deactivating an entity might have some implications. Be sure to know what you're doing. 
	 *
	 */
	@Deprecated
	void setObjectStatus(ObjectStatus objectStatus);

}
