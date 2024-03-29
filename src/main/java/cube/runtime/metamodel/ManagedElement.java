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

import cube.runtime.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Properties;

/**
 * Author: debbabi
 * Date: 4/27/13
 * Time: 7:40 PM
 */
public class ManagedElement extends Observable implements Cloneable, Serializable, Comparable {

    public static final int UNMANAGED = -1;
    public static final int INVALID = 0;
    public static final int VALID= 1;

    int priority = 0;

    /**
     * Current state of the Managed ElementDescription.
     */
    private int state = UNMANAGED;

    /**
     * Concept name.
     */
    private String name = "";

    /**
     * Concept namespace.
     */
    private String namespace = "";

    /**
     * Instance uuid.
     */
    private String uuid = null;

    /**
     * Hosted Cube Agent URI.
     */
    private String am = null;

    /**
     * Instance attributes.
     */
    private List<Attribute> attributes = new ArrayList<Attribute>();

    /**
     * Instance references.
     */
    private List<Reference> references = new ArrayList<Reference>();

    /**
     * Index used to create the URIs.
     */
    private static int index=0;

    /**
     * Is the current element instance in a currently holding resolution process?
     */
    private boolean inResolution = false;

    /**
     * Gets the instance current state.
     * @return
     */
    public synchronized int getState() {
        return this.state;
    }

    /**
     * Gets the instance current state as String.
     *
     * @return
     */
    public synchronized String getStateAsString() {
        switch (this.state) {
            case UNMANAGED:
                return "UNMANAGED";
            case INVALID:
                return "INVALID";
            case VALID:
                return "VALID";
        }
        return "";
    }

    public ManagedElement() {
        this.uuid = Utils.GenerateUUID();
    }

    public ManagedElement(String autonomicManagerUri) {
        setAutonomicManager(autonomicManagerUri);
        this.uuid = Utils.GenerateUUID();
    }

    public ManagedElement(String autonomicManagerUri, Properties attributes) throws PropertyExistException, InvalidNameException{
        this.uuid = Utils.GenerateUUID();
        if (attributes != null) {
            for (Object key : attributes.keySet()) {
                addAttribute(key.toString(), attributes.get(key).toString());
            }
        }
        if (this.am == null)
            setAutonomicManager(autonomicManagerUri);
    }

    public synchronized int updateState(int newState) {
        int oldState = this.state;
        this.state = newState;
        if (newState == VALID) {
            //updateProperties();
            //notifyObservers(new Notification(ManagedElementListener.CHANGED_STATE));
        }
        return oldState;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public synchronized void setName(String name) {
        this.name = name;
    }

    public synchronized void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public synchronized String getName() {
        /*
        try {
            throw new Exception("getName() function of ManagedElement should be implemented!");
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        return this.name;
    }

    public synchronized String getNamespace() {
        /*
        try {
            throw new Exception("getName() function of ManagedElement should be implemented!");
        } catch (Exception e) {
            e.printStackTrace();
        } */
        return this.namespace;
    }


    public synchronized String getFullname() {
        return this.getNamespace()+":"+getName();
    }

    public synchronized String getAutonomicManager() {
        return this.am;
    }

    public synchronized void setAutonomicManager(String uri) {
        this.am = uri;
    }

    public synchronized String getUUID() {
        return this.uuid;
    }

    /**
     * Gets the URI of the current Managed ElementDescription.
     * @return
     */
    public synchronized String getUri() {
        return getAutonomicManager() + "/" + getUUID();
    }

    /**
     * Get Managed ElementDescription Properties
     * @return
     */
    public List<Attribute> getAttributes() {
        synchronized (this.attributes) { return this.attributes; }
    }

    /**
     * Get Attribute
     * @param name
     * @return NULL if name is null or no property found with the name 'name'; the found property other else.
     */
    private Attribute _getAttribute(String name) {

        if (name != null && name.length() > 0) {
            synchronized (this.attributes) {
                for (Attribute p : this.attributes) {
                    if (p.getName() != null && p.getName().equalsIgnoreCase(name.toLowerCase())) {
                        return p;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get Attribute value
     * @param name
     * @return NULL if 'name' is null or no property found with 'name' name; the value of the found property other else
     */
    public String getAttribute(String name) {
        if (name != null && name.length() > 0) {
            Attribute p = _getAttribute(name);
            if (p != null) {
                return p.getValue();
            }
        }
        return null;
    }

    /**
     * Checks if the Managed ElementDescription has the given property.
     *
     * @param name Attribute name
     * @return TRUE if it has the provided property.
     */
    public   boolean hasAttribute(String name) {
        if (name != null) {
            synchronized (this.attributes) {
                for(Attribute p : this.attributes) {
                    if (p.getName().equalsIgnoreCase(name)) {
                        return true;
                    }
                }
            }
        }
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Adding property
     * @param name
     * @param value
     * @return TRUE if the property added; FALSE other else.
     * @throws PropertyExistException
     */
    public boolean addAttribute(String name, String value) throws PropertyExistException, InvalidNameException {
        if (name == null || name.length() == 0) {
            return false;
        }
        if (_getAttribute(name.toLowerCase()) != null) {
            throw new PropertyExistException("You are trying to add an existing property '"+name+"'!");
        }
        synchronized (this.attributes) {
            return this.attributes.add(new Attribute(name.toLowerCase(), value));
        }
    }

    public boolean addAttribute(Attribute a) {
        synchronized (this.attributes) {
            return this.attributes.add(a);
        }
    }

    /**
     * Update property
     * @param name
     * @param newValue
     * @return NULL is name is null; or Old value if a property exist with 'name' name.
     * @throws PropertyNotExistException
     */
    public synchronized String updateAttribute(String name, String newValue) throws PropertyNotExistException {
        //System.out.println("ME.updateAttribute..");
        if (name != null && name.length() > 0) {
            if (_getAttribute(name) == null)
                throw new PropertyNotExistException("You are trying to update unexistant property '" + name + "'!");
            Attribute p = _getAttribute(name);
            String oldValue = p.getValue();
            p.setValue(newValue);
            return oldValue;
        }
        return null;
    }

    public synchronized int validate() {
        int old = updateState(VALID);
        setChanged();
        notifyObservers();
        return old;
    }

    /**
     * Get Managed ElementDescription References
     * @return
     */
    public List<Reference> getReferences() {
        synchronized (this.references) {
            return this.references;
        }
    }

    /**
     * Get Regerence
     * @param name
     * @return
     */
    public Reference getReference(String name) {
        if (name != null && name.length() > 0) {
            synchronized (this.references) {
                for (Reference r : this.references) {
                    synchronized (r) {
                        if (r.getName() != null && r.getName().toLowerCase().equalsIgnoreCase(name.toLowerCase())) {
                            return r;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Checks if the Managed ElementDescription has the given reference.
     *
     * @param name Reference name
     * @return TRUE if it has the provided reference name.
     */
    public boolean hasReference(String name) {
        if (name != null) {
            synchronized (this.references) {
                for (Reference r : this.references) {
                    synchronized (r) {
                        if (r.getName().equalsIgnoreCase(name)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public Reference addReference(String name, boolean onlyOne) throws InvalidNameException {
        Reference r = getReference(name);
        if (r == null) {
            r = new Reference(name, onlyOne);
            synchronized (this.references) {
                this.references.add(r);
            }
        }
        return r;
    }

    public void addReference(Reference r) {
        synchronized (this.references) {
            if (getReference(r.getName()) == null) {
                this.references.add(r);
            }
        }
    }

    public synchronized void setState(int state) {
        this.state = state;
    }


    public synchronized void setUUID(String uuid) {
        this.uuid = uuid;
    }

    /**
     * Gets a textual description of the element.
     *
     * @return
     */
    public synchronized String getDocumentation() {
        String msg = "";
        msg += "\n + " + this.getUri();
        msg += "\n    | namespace: " + getNamespace();
        msg += "\n    | name: " + getName();
        synchronized (this.attributes) {
            if (this.attributes.size() > 0) {
                msg += "\n    | ATTRIBUTES";
                for (Attribute p : this.getAttributes()) {
                    synchronized (p) {
                        msg += "\n    |   " + p.getName() + "=" + p.getValue();
                    }
                }
            }
        }
        synchronized (this.references) {
            if (this.references.size() > 0) {
                msg += "\n    | REFERENCES";
                for (Reference r : this.getReferences()) {
                    synchronized (r) {
                        msg += "\n    |   " + r.getName() + ":";
                        for (String s : r.getReferencedElements()) {
                            msg += "\n    |     " + s;
                        }
                    }
                }
            }
        }
        return msg;
    }

    public synchronized String getHTMLDocumentation() {
        String msg = "<html>";
        msg += "<br/> <b>" + this.getUUID() + "</b><br/><hr/>";
        msg += "<p><b>MANAGED ELEMENT</b><br/><ul>";
        msg += "<li> " + this.getName() + "</li>";
        msg += "</ul>";
        synchronized (this.attributes) {
            if (this.attributes.size() > 0) {
                msg += "<p><b>ATTRIBUTES</b><br/><ul>";
                for (Attribute p : this.attributes) {
                    synchronized (p) {
                        msg += "<li> " + p.getName() + "=" + p.getValue() + "</li>";
                    }
                }
                msg += "</ul></p>";
            }
        }
        synchronized (this.references) {
            if (this.references.size() > 0) {
                msg += "<p><b>REFERENCES</b><ul>";
                for (Reference r : this.references) {
                    synchronized (r) {
                        msg += "<li>" + r.getName() + ":<br/>";
                        msg += "<ul>";
                        for (String s : r.getReferencedElements()) {
                            msg += "<li>" + s + "</li>";
                        }
                    }
                    msg += "</ul>";
                    msg += "</li>";
                }
                msg += "</ul></p>";
            }
        }
        return msg + "</html>";
    }

    public boolean removeReferencedElement(String ref) {
        boolean changed = false;
        if (ref != null) {
            List<Reference> toBeRemoved = new ArrayList<Reference>();
            synchronized (this.references) {
                for (Reference r : this.references) {
                    synchronized (r) {
                        if (r.removeReferencedElement(ref) == true) {
                            changed = true;
                            if (r.getReferencedElements().size() == 0) {
                                toBeRemoved.add(r);
                            }
                        }
                    }
                }
            }
            for (Reference r : toBeRemoved) {
                this.references.remove(r);
            }
            if (changed) {
                if (getState() == VALID)
                    updateState(ManagedElement.INVALID);
            }
        }
        return changed;
    }

    public boolean removeEmptyAttributes() {
        List<Attribute> toBeRemoved = new ArrayList<Attribute>();
        synchronized (this.attributes) {
            for (Attribute p : this.attributes) {
                synchronized (p) {
                    if (p.getValue() == null) {
                        toBeRemoved.add(p);
                    }
                }
            }
        }
        boolean changed = false;
        for (Attribute p : toBeRemoved) {
            synchronized (this.attributes) {
                this.attributes.remove(p);
            }
            changed = true;
        }
        return changed;
    }

    public boolean removeEmptyReferences() {
        List<Reference> toBeRemoved = new ArrayList<Reference>();
        synchronized (this.references) {
            for (Reference r : this.references) {
                synchronized (r) {
                    if (r.getReferencedElements().size() == 0) {
                        toBeRemoved.add(r);
                    }
                }
            }
        }
        boolean changed = false;
        for (Reference r : toBeRemoved) {
            synchronized (this.references) {
                this.references.remove(r);
            }
            changed = true;
        }
        return changed;
    }

    public synchronized boolean isInResolution() {
        return inResolution;
    }

    public synchronized void setInResolution(boolean inResolution) {
        this.inResolution = inResolution;
    }


    @Override
    public synchronized Object clone() throws CloneNotSupportedException {
        ManagedElement me = new ManagedElement();
        me.setNamespace(getNamespace().toLowerCase());
        me.setName(getName().toLowerCase());
        me.setAutonomicManager(getAutonomicManager());
        me.setInResolution(isInResolution());
        me.setState(getState());
        me.setUUID(getUUID());
        synchronized (this.attributes) {
            for (Attribute a : this.attributes) {
                me.addAttribute((Attribute)a.clone());
            }
        }
        synchronized (this.references) {
            for (Reference r : this.references) {
                me.addReference((Reference) r.clone());
            }
        }
        return me;
    }

    @Override
    public String toString() {
        return this.getDocumentation();
    }

    public int compareTo(Object o) {
        if (o instanceof ManagedElement) {
            return this.getPriority() - ((ManagedElement) o).getPriority();
        }
        return 0;
    }
}
