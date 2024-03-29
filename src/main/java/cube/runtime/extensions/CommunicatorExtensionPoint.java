package cube.runtime.extensions;

import cube.runtime.am.CMessage;
import cube.runtime.am.comm.CommunicationException;
import cube.runtime.am.MessagesListener;

import java.io.IOException;

/**
 * Author: debbabi
 * Date: 4/28/13
 * Time: 12:04 AM
 */
public interface CommunicatorExtensionPoint extends ExtensionPoint {

    public void sendMessage(CMessage msg) throws CommunicationException, IOException;
    public void addMessagesListener(MessagesListener callback);
    public void start();
    public void stop();
    public void destroy();
}
