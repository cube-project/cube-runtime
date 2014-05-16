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
public class MasterFactory extends AbstractManagedElement {

    public MasterFactory(Extension extension) {
        super(extension);
    }

    public ManagedElement newInstance(Properties properties) {
        Master master= null;
        try {
            master = new Master(getExtension().getAutonomicManager().getUri(), properties);
        } catch (PropertyExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return master;
    }

    public String getName() {
        return Master.NAME;
    }
}
