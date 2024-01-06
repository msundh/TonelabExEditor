package nu.mikaelsundh.tonelabex.editor.model;

import java.util.EventListener;

/**
 * User: Mikael Sundh
 * Date: 2012-11-15
 */
public interface DeviceListener extends EventListener {
    void inMessage(DeviceEvent e);
}
