package nu.mikaelsundh.tonelabex.editor.model;

import java.util.EventObject;

/**
 * User: Mikael Sundh
 * Date: 2012-11-15
 */
public class DeviceEvent extends EventObject {
    public static final int ERROR=1;
    public static final int PRESET_LOAD=2;
    public static final int PRESET_CHANGE=3;
    public static final int GUI_CHANGE=4;
    public static final int DATA_CHANGE_SUCCESS =5;
    private int type;
    private Object data;

    public DeviceEvent(Object source, int type, Object data) {
        super(source);
        this.type = type;
        this.data = data;
    }
    public DeviceEvent(Object source, int type) {
        super(source);
        this.type = type;
        this.data = null;
    }

    public int getType() {
        return type;
    }

    public Object getData() {
        return data;
    }
}
