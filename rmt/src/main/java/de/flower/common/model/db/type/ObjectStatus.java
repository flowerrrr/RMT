package de.flower.common.model.db.type;

/**
 * <p>
 * Defines possible object statuses.
 * </p>
 * <p>
 * <b>Important: Changing Enum order will mess up an existing database, because our Enums are stored
 * by their ordinal value.</b>
 * </p>
 * 
 * @author flowerrrr
 */
public enum ObjectStatus {

	/** Active object. */
	ACTIVE,

	/** Soft-deleted object. */
	DELETED,

	/** Fixed object (should never be deleted or deactivated). */
	FIXED;

}
