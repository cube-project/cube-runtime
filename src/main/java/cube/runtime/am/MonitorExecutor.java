package cube.runtime.am;

import cube.runtime.extensions.MonitorExecutorExtensionPoint;

/**
 * User: debbabi
 * Date: 9/17/13
 * Time: 11:36 PM
 */
public interface MonitorExecutor {

    public void addSpecificMonitorExecutor(MonitorExecutorExtensionPoint sme);

}
