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


package cube.runtime.extensions.core.resolvers;

import cube.runtime.am.RuntimeModelController;
import cube.runtime.extensions.AbstractResolver;
import cube.runtime.extensions.Extension;
import cube.runtime.extensions.core.model.Component;
import cube.runtime.metamodel.InvalidNameException;
import cube.runtime.metamodel.ManagedElement;
import cube.runtime.metamodel.Reference;
import cube.runtime.util.model.ModelUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: debbabi
 * Date: 4/28/13
 * Time: 8:24 PM
 */
public class HasSourceComponent extends AbstractResolver {


    public HasSourceComponent(Extension extension) {
        super(extension);
    }

    public String getName() {
        return this.getClass().getSimpleName();
    }

    public boolean check(ManagedElement me, String value) {
        if (me != null && value != null) {
            Reference r = me.getReference(Component.CORE_COMPONENT_INPUTS);
            if (r!=null) {
                for (String e : r.getReferencedElements()) {
                    if (e.equalsIgnoreCase(value)) return true;
                }
            }
        }
        return false;
    }

    public boolean perform(ManagedElement me, String value) {
        if (me != null && value != null) {
            Reference r = me.getReference(Component.CORE_COMPONENT_INPUTS);
            if (r == null) {
                try {
                    r = me.addReference(Component.CORE_COMPONENT_INPUTS, false);

                } catch (InvalidNameException e) {
                    e.printStackTrace();
                }
            }
            RuntimeModelController rmc = getExtension().getAutonomicManager().getRuntimeModelController();
            try {
                rmc.addReferencedElement(value, Component.CORE_COMPONENT_OUTPUTS, me.getUUID());
            } catch (InvalidNameException e) {
                e.printStackTrace();
            }
            r.addReferencedElement(value);
            return true;
        }
        return false;
    }

    public List<String> find(ManagedElement me, ManagedElement description) {
        List<String> result = new ArrayList<String>();
        if (me != null) {
            Reference r = me.getReference(Component.CORE_COMPONENT_OUTPUTS);
            if (r != null) {
                for (String tmp : r.getReferencedElements()) {
                    ManagedElement input = getExtension().getAutonomicManager().getRuntimeModelController().getCopyOfManagedElement(tmp);
                    if (ModelUtils.compareTwoManagedElements(description, input) == 0) {
                        result.add(tmp);
                    }
                }
            }
        }
        //System.out.println("\n[OnNode] find method does not take into consideration the description parameter!");
        return result;
    }

}
