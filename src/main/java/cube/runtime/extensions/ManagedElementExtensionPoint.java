package cube.runtime.extensions;

import cube.runtime.metamodel.ManagedElement;

import java.util.Properties;

/**
 * User: debbabi
 * Date: 9/18/13
 * Time: 12:11 AM
 */
public interface ManagedElementExtensionPoint extends ExtensionPoint {

    public ManagedElement newInstance(Properties properties);

}
