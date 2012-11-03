/*
 *  Copyright 2001-2011 Stephen Colebourne
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package de.flower.rmt.model.db.type;

import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.joda.time.DateTime;
import org.joda.time.contrib.hibernate.PersistentDateTime;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Persist {@link SplitDateTime} via hibernate.
 * 
 * @author flowerrrr
 */
@Deprecated // not used
public class PersistentSplitDateTime extends PersistentDateTime {

    public static final PersistentSplitDateTime INSTANCE = new PersistentSplitDateTime();

    public Class returnedClass() {
        return SplitDateTime.class;
    }

    public boolean equals(Object x, Object y) throws HibernateException {
        if (x == y) {
            return true;
        }
        if (x == null || y == null) {
            return false;
        }
        DateTime dtx = ((SplitDateTime) x).getDateTime();
        DateTime dty = ((SplitDateTime) y).getDateTime();

        return dtx.equals(dty);
    }

    public Object nullSafeGet(ResultSet resultSet, String string) throws SQLException {
        Object timestamp = StandardBasicTypes.TIMESTAMP.nullSafeGet(resultSet, string);
        if (timestamp == null) {
            return null;
        }

        return new SplitDateTime(timestamp);
    }

    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int index) throws HibernateException, SQLException {
        if (value == null) {
            StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, null, index);
        } else {
            StandardBasicTypes.TIMESTAMP.nullSafeSet(preparedStatement, ((SplitDateTime) value).toDate(), index);
        }
    }

    public boolean isMutable() {
        return true;
    }

    public Object fromXMLString(String string) {
        return new SplitDateTime(string);
    }

}
