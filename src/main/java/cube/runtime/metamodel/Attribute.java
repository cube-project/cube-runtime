/*
 * Copyright 2011-2013 Adele Research Group (http://adele.imag.fr/) 
 * LIG Laboratory (http://www.liglab.fr)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package cube.runtime.metamodel;

import java.io.Serializable;

/**
 * Author: debbabi
 * Date: 4/25/13
 * Time: 12:14 PM
 */
public class Attribute implements Serializable, Cloneable {

    private String name = null;
    private String value = null;

    public Attribute(String name, String value) throws InvalidNameException {
        if (name == null || name.length() == 0)
            throw new InvalidNameException("Invalid name for the new Reference!");
        this.name = name.toLowerCase();
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public synchronized  Object clone() throws CloneNotSupportedException {
        try {
            return new Attribute(this.name, this.value);
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
        return null;
    }
}
