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


package cube.runtime.extensions.core.impl;

import cube.runtime.extensions.core.CoreExtensionFactory;
import cube.runtime.AutonomicManager;
import cube.runtime.extensions.Extension;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;

import java.util.Properties;

/**
 * Author: debbabi
 * Date: 4/26/13
 * Time: 5:11 PM
 */
@Component
@Provides
@Instantiate
public class CoreExtensionFactoryImpl implements CoreExtensionFactory {

    public String getName() {
        return NAME;
    }

    public String getPrefix() {
        return PREFIX;
    }

    public String getNamespace() {
        return NAMESPACE;
    }

    public String getFullName() {
        return getNamespace()+":"+getName();
    }

    public Extension newExtensionInstance(AutonomicManager agent, Properties properties) {
        return new CoreExtension(agent, this, properties);
    }


}
