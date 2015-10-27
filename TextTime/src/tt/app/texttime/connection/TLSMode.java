package tt.app.texttime.connection;

import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
public enum TLSMode {

    /**
     * Security via TLS encryption is used whenever it's available.
     */
    enabled,

    /**
     * Security via TLS encryption is required in order to connect.
     */
    required,

    /**
     * Security via old SSL based encryption is enabled. If the server does not
     * handle legacy-SSL, the connection to the server will fail.
     */
    legacy;

    SecurityMode getSecurityMode() {
        if (this == enabled)
            return SecurityMode.enabled;
        else if (this == required)
            return SecurityMode.required;
        else if (this == legacy)
            return SecurityMode.disabled;
        else
            throw new IllegalStateException();
    }

}
