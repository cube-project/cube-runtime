package cube.runtime.am;

import java.util.List;

/**
 * Created by debbabi on 12/5/13.
 */
public interface ExternalInstancesHandler {

    public void start();

    public void stop();

    public void destroy();

    public void keepAliveReceived(String am);

    public  void addExternalInstance(String uuid, String autonomicManagerUri);

    public String getAutonomicManagerOfExternalInstance(String uuid) ;

    public void removeExternalAutonomicManagerInstances(String agent_uri);

    public void removeExternalInstance(String uuid);

    public List<String> getExternalAutonomicManagers();
}
