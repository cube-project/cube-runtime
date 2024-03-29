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


package cube.runtime.extensions.core;

import cube.runtime.extensions.ExtensionFactoryService;

/**
 * Author: debbabi
 * Date: 4/26/13
 * Time: 5:06 PM
 */
public interface CoreExtensionFactory extends ExtensionFactoryService {

    String NAME = "core";
    String PREFIX = "core";
    String NAMESPACE = "fr.liglab.adele.cube.core";

}
