package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.DeviceListener;

import java.util.ArrayList;

/**
 * User: Mikael Sundh
 * Date: 2012-11-15
 */
public class GuiListenerHandler {

    private ArrayList<DeviceListener> listeners;
    private static GuiListenerHandler instance = null;
    private GuiListenerHandler() {
        super();
    }
    public static GuiListenerHandler getInstance() {
        if (instance == null) {
            instance = new GuiListenerHandler();
        }
        return instance;
    }
    public synchronized void addDeviceListener(DeviceListener l) {
        if (listeners == null)
            listeners = new ArrayList<DeviceListener>();
        listeners.add(l);
    }

    public synchronized void removeDeviceListener(DeviceListener l) {
        listeners.remove(l);
    }

    public synchronized void fireEvent( DeviceEvent event)	{
        for (DeviceListener listener : listeners) {
            listener.inMessage(event);
        }
    }
}
