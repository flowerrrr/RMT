/*
 * $Id: org.eclipse.jdt.ui.prefs 5004 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) eelco12 $
 * $Revision: 5004 $
 * $Date: 2006-03-17 20:47:08 -0800 (Fri, 17 Mar 2006) $
 *
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wicket.contrib.gmap3.js;

/**
 * Convenience class to ease generation of JS code.
 */
public final class Constructor {
    private final StringBuffer buffer = new StringBuffer();

    private boolean first = true;

    /**
     * Instantiates a new constructor.
     *
     * @param name the name
     */
    public Constructor(final String name) {
        buffer.append("new ");
        buffer.append(name);
        buffer.append("(");
    }

    /**
     * Adds the string.
     *
     * @param value the value
     * @return the constructor
     */
    public Constructor addString(final Object value) {
        return add("\"" + value + "\"");
    }

    /**
     * Adds the.
     *
     * @param value the value
     * @return the constructor
     */
    public Constructor add(final Object value) {
        if (!first) {
            buffer.append(", ");
        }
        buffer.append(value);

        first = false;
        return this;
    }

    /**
     * To js.
     *
     * @return the string
     */
    public String toJS() {
        buffer.append(")");
        final String string = buffer.toString();
        buffer.deleteCharAt(buffer.length() - 1);
        return string;
    }
}
