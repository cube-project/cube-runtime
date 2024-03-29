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
import java.util.ArrayList;
import java.util.List;

/**
 * Author: debbabi
 * Date: 4/25/13
 * Time: 12:16 PM
 */
public class Reference implements Serializable, Cloneable {

    private String name = null;
    private boolean unary = false;

    private List<String> referencedElements = new ArrayList<String>();

    /**
     * Constructor
     * @param name the name of this reference
     * @param unary if TRUE, this managed element will accept at max one referenced element
     */
    public Reference(String name, boolean unary) throws InvalidNameException {
        if (name == null || name.length() == 0)
            throw new InvalidNameException("Invalid name for the new Reference!");
        this.name = name.toLowerCase();
        this.unary = unary;
    }

    public String getName() {
        return name;
    }

    public synchronized List<String> getReferencedElements() {
        return referencedElements;
    }

    public boolean isUnary() {
        return unary;
    }

    public synchronized void setReferencedElements(List<String> referencedElements) {
        this.referencedElements = referencedElements;
    }

    /**
     * Add Referenced ElementDescription.
     * If the reference has at max one referenced element (onlyone attribut is set to true), the existing referenced
     * elements will be removed, and the new one identified by 'elementUUID' will be added.
     *
     * @param elementUUID
     * @return FALSE if 'elementUUID' is null or the element already referenced; TRUE if added properly.
     */
    public synchronized boolean addReferencedElement(String elementUUID) {
        if (elementUUID == null || elementUUID.length() == 0) {
            return false;
        }
        if (this.referencedElements.contains(elementUUID)) {
            return false;
        }
        if (this.unary == true) {
            if (this.referencedElements.size() > 0) {
                if (this.referencedElements.get(0).equalsIgnoreCase(elementUUID))
                    return true;
                return false;
            }
        }
        return this.referencedElements.add(elementUUID);
    }

    public synchronized boolean removeReferencedElement(String elementUUID) {
        if (elementUUID == null || elementUUID.length() == 0) {
            return false;
        }
        if (!this.referencedElements.contains(elementUUID)) {
            return false;
        }
        return this.referencedElements.remove(elementUUID);
    }

    public synchronized boolean hasReferencedElement(String elementUUID) {
        if (elementUUID != null) {
            return (referencedElements.contains(elementUUID));
        }
        return false;
    }

    @Override
    public synchronized  Object clone() throws CloneNotSupportedException {
        try {
            Reference r = new Reference(this.name, this.unary);
            for (String ref : getReferencedElements()) {
                r.addReferencedElement(ref);
            }
            return r;
        } catch (InvalidNameException e) {
            e.printStackTrace();
        }
        return super.clone();
    }
}
