package cube.runtime.am;

import cube.runtime.AutonomicManager;
import cube.runtime.extensions.ResolverExtensionPoint;
import cube.runtime.metamodel.ManagedElement;

import java.util.List;

/**
 * User: debbabi
 * Date: 9/19/13
 * Time: 10:08 AM
 */
public interface ArchetypeResolver extends RuntimeModelListener {

    // functioning

    void resolveUncheckedInstance(ManagedElement instance);

    /// helpers
    public boolean moveManagedElement(ManagedElement me, String am_uri);
    List<String> findFromRuntimeModel(ManagedElement description);
    List<String> findUsingArchetypeProperty(String archetypePropertyName, String uuid, ManagedElement description);
    boolean checkProperty(String archetypePropertyName, ManagedElement managedElement, String value);
    boolean performProperty(String archetypePropertyName, ManagedElement managedElement, String value);
    boolean performProperty(String archetypePropertyName, String uuid, String value);
    String createUsingDescription(ManagedElement description);
    boolean verifyProperty(String archetypePropertyName, String uuid, String value);

    // Utils

    AutonomicManager getAutonomicManager();
    ResolverExtensionPoint getResolver(String fullname);
    void addSpecificResolver(ResolverExtensionPoint specificResolver);
    void receiveMessage(CMessage msg);
    public void refreshRemoteAM(String am_uri);

}
