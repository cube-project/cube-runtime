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
public class NodeFactory extends AbstractManagedElement {

    public NodeFactory(Extension extension) {
        super(extension);
    }

    public ManagedElement newInstance(Properties properties) {
        Node node = null;
        try {
            node = new Node(getExtension().getAutonomicManager().getUri(), properties);
        } catch (PropertyExistException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvalidNameException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return node;
    }

    public String getName() {
        return Node.NAME;
    }
}
