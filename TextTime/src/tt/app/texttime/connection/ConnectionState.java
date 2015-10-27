package tt.app.texttime.connection;

import tt.app.texttime.R;

public enum ConnectionState {

    /**
     * Connection is not active.
     */
    offline,

    /**
     * Waiting for connection before first connection or before reconnection.
     */
    waiting,

    /**
     * Connection is in progress.
     */
    connecting,

    /**
     * Connection was established, registration is in progress.
     */
    registration,

    /**
     * Connection was established, authentication is in progress.
     */
    authentication,

    /**
     * Authorized connection has been established.
     */
    connected;

    /**
     * @return whether authorized connection has been established.
     */
    public boolean isConnected() {
        return this == ConnectionState.connected;
    }

    /**
     * @return whether connection has already been established or will be
     * established later.
     */
    public boolean isConnectable() {
        return this != ConnectionState.offline;
    }

    /**
     * @return Resource id with associated string.
     */
    public int getStringId() {
        if (this == ConnectionState.offline)
            return R.string.account_state_offline;
        else if (this == ConnectionState.waiting)
            return R.string.account_state_waiting;
        else if (this == ConnectionState.connecting)
            return R.string.account_state_connecting;
        else if (this == ConnectionState.registration)
            return R.string.account_state_registration;
        else if (this == ConnectionState.authentication)
            return R.string.account_state_authentication;
        else if (this == ConnectionState.connected)
            return R.string.account_state_connected;
        else
            throw new IllegalStateException();
    }

}

