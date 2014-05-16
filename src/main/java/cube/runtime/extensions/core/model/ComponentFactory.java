package cube.runtime.extensions.core.model;

import cube.runtime.extensions.AbstractManagedElement;
import cube.runtime.extensions.Extension;
import cube.runtime.metamodel.InvalidNameException;
import cube.runtime.metamodel.ManagedElement;
import cube.runtime.metamodel.PropertyExistException;

import java.util.Properties;

/**
 * User: debbabi
 * Date: 9/18/13
 * Time: 12:04 AM
 */
public class ComponentFactory extends AbstractManagedElement {

    public ComponentFactory(Extension extension) {
        super(extension);
    }

    public ManagedElement newInstance(Properties properties) {
        Component c = null;
        try {
            c = new Component(getExtension().getAutonomicManager().getUri(), properties);
        } catch (PropertyExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return c;
    }

    public String getName() {
        return Component.NAME;
    }
}
