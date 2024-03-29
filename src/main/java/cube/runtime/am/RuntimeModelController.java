package cube.runtime.am;

import cube.runtime.AutonomicManager;
import cube.runtime.extensions.ManagedElementExtensionPoint;
import cube.runtime.metamodel.*;

import java.util.List;
import java.util.Properties;

/**
 * Author: debbabi
 * Date: 4/27/13
 * Time: 7:15 PM
 */
public interface RuntimeModelController {

    // Attributes

    int getState(String managed_element_uuid);
    String getAttributeValue(String managed_element_uuid, String name) ;
    boolean addAttribute(String managed_element_uuid, String name, String value) throws PropertyExistException, InvalidNameException;
    String updateAttribute(String managed_element_uuid, String name, String newValue) throws PropertyNotExistException;
    String getAutonomicManagerOf(String r);

    // References

    List<String> getReferencedElements(String managed_element_uuid, String reference_name);
    boolean hasReferencedElement(String managed_element_uuid, String reference_name, String referenced_element_uuri);
    boolean addReferencedElement(String managed_element_uuid, String reference_name, String referenced_element_uuid) throws InvalidNameException;
    boolean addReferencedElement(String managed_element_uuid, String reference_name, boolean onlyone, String referenced_element_uuid) throws InvalidNameException;
    boolean removeReferencedElement(String managed_element_uuid, String reference_name, String referenced_element_uuid);

    // Runtime Model Instances
    RuntimeModel getRuntimeModel();
    ManagedElement newManagedElement(String namespace, String name, Properties properties) throws NotFoundManagedElementException, InvalidNameException, PropertyExistException;
    ManagedElement newManagedElement(String namespace, String name, Properties properties, boolean isUnmanaged) throws NotFoundManagedElementException, InvalidNameException, PropertyExistException;
    boolean isLocalInstance(String uuid);
    boolean isRemoteInstance(String uuid);
    public ManagedElement getLocalElement(String managed_element_uuid);
    ManagedElement getCopyOfManagedElement(String uuid);
    void addManagedElement(ManagedElement managedElement);
    boolean destroyElement(String managed_element_uuid);
    //boolean move(ManagedElement me, String amUri);

    // Utils
    void addManagedElementFactory(ManagedElementExtensionPoint exp);
    void receiveMessage(CMessage msg);
    AutonomicManager getAutonomicManager();



    boolean removeManagedElement(String uuid);
}
