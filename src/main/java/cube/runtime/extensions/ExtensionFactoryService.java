package cube.runtime.extensions;


import cube.runtime.AutonomicManager;

import java.util.Properties;

/**
 * Author: debbabi
 * Date: 4/26/13
 * Time: 5:06 PM
 */
public interface ExtensionFactoryService {

    String getName();
    String getPrefix();
    String getNamespace();
    String getFullName(); // namespace:name

    Extension newExtensionInstance(AutonomicManager agent, Properties properties);


}
