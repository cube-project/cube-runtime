package cube.runtime.extensions;

import cube.runtime.am.RuntimeModelListener;

/**
 * User: debbabi
 * Date: 9/17/13
 * Time: 9:57 PM
 */
public interface MonitorExecutorExtensionPoint extends ExtensionPoint, RuntimeModelListener {

    public void start();
    public void stop();
    public void destroy();

}
