package cube.runtime.am.me;

import cube.runtime.AutonomicManager;
import cube.runtime.am.MonitorExecutor;
import cube.runtime.extensions.MonitorExecutorExtensionPoint;

import java.util.HashMap;
import java.util.Map;

/**
 * User: debbabi
 * Date: 9/19/13
 * Time: 10:07 AM
 */
public class MonitorExecutorImpl implements MonitorExecutor {

    private AutonomicManager am;
    private Map<String, MonitorExecutorExtensionPoint> monitorExecutors;

    private boolean working = false;

    public MonitorExecutorImpl(AutonomicManager am) {
        this.am = am;
        this.monitorExecutors = new HashMap<String, MonitorExecutorExtensionPoint>();
    }

    public void addSpecificMonitorExecutor(MonitorExecutorExtensionPoint sme) {
        this.monitorExecutors.put(sme.getExtension().getExtensionFactory().getNamespace() + ":" + sme.getName(), sme);

    }

}
