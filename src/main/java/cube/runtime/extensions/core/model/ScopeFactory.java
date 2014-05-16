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
public class ScopeFactory extends AbstractManagedElement {

    public ScopeFactory(Extension extension) {
        super(extension);
    }

    public ManagedElement newInstance(Properties properties) {
        Scope scope= null;
        try {
            scope = new Scope(getExtension().getAutonomicManager().getUri(), properties);
        } catch (PropertyExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return scope;
    }

    public String getName() {
        return Scope.NAME;
    }
}
