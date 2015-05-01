package nu.mikaelsundh.tonelabex.editor.gui;

import nu.mikaelsundh.tonelabex.editor.model.DeviceEvent;
import nu.mikaelsundh.tonelabex.editor.model.DeviceListener;

import java.util.ArrayList;
import java.util.Iterator;

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
        if (listeners.contains(l))
            listeners.remove(listeners.indexOf(l));
    }

    public synchronized void fireEvent( DeviceEvent event)	{
        Iterator i = listeners.iterator();
        while(i.hasNext())	{
            ((DeviceListener) i.next()).inMessage(event);
        }
    }
}
