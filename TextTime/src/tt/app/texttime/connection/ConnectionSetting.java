package tt.app.texttime.connection;


public class ConnectionSetting {

    /**
     * Protocol.
     */
  //  private final AccountProtocol protocol;

    /**
     * User part of jid.
     */
    private String userName;

    /**
     * Server part of jid.
     */
    private String serverName;

    /**
     * Resource part of jid.
     */
    private String resource;

    /**
     * Use custom connection host and port.
     */
    private boolean custom;

    /**
     * Host for connection.
     */
    private String host;

    /**
     * Port for connection.
     */
    private int port;

    /**
     * Password.
     */
    private String password;

    /**
     * Whether SASL Authentication Enabled.
     */
    private boolean saslEnabled;

    /**
     * TLS mode.
     */
   // private TLSMode tlsMode;

    /**
     * Use compression.
     */
    private boolean compression;

    //private ProxyType proxyType;

    private String proxyHost;

    private int proxyPort;

    private String proxyUser;

    private String proxyPassword;

    public void ConnectionSettings(String userName,
                              String serverName, String resource, boolean custom, String host,
                              int port, String password, boolean saslEnabled,
                              boolean compression, String proxyHost,
                              int proxyPort, String proxyUser, String proxyPassword) {
   //     super();
       // this.protocol = protocol;
        this.userName = userName;
        this.serverName = serverName;
        this.resource = resource;
        this.custom = custom;
        this.host = host;
        this.port = port;
        this.password = password;
        this.saslEnabled = saslEnabled;
       // this.tlsMode = tlsMode;
        this.compression = compression;
      //  this.proxyType = proxyType;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPassword = proxyPassword;
    }

    /*public AccountProtocol getProtocol() {
        return protocol;
    }
*/
    /**
     * @return User part of jid.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return Server part of jid.
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * @return Whether custom host and port must be used.
     */
    public boolean isCustomHostAndPort() {
        return custom;
    }

    /**
     * @return Custom host to connect to.
     */
    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getResource() {
        return resource;
    }

    public String getPassword() {
        return password;
    }

    /**
     * @return Whether SASL Authentication Enabled.
     */
    public boolean isSaslEnabled() {
        return saslEnabled;
    }

    /**
     * @return TLS mode.
     */
   /* public TLSMode getTlsMode() {
        return tlsMode;
    }
*/
    /**
     * @return Whether compression is used.
     */
    public boolean useCompression() {
        return compression;
    }

 /*   public ProxyType getProxyType() {
        return proxyType;
    }
*/
    public String getProxyHost() {
        return proxyHost;
    }

    public int getProxyPort() {
        return proxyPort;
    }

    public String getProxyUser() {
        return proxyUser;
    }

    public String getProxyPassword() {
        return proxyPassword;
    }

    /**
     * Updates options.
     *
     * @param custom
     * @param host
     * @param port
     * @param password
     * @param saslEnabled
     * @param tlsMode
     * @param compression
     */
    public void update(boolean custom, String host, int port, String password,
                       boolean saslEnabled, boolean compression,
                       String proxyHost, int proxyPort,
                       String proxyUser, String proxyPassword) {
        this.custom = custom;
        this.host = host;
        this.port = port;
        this.password = password;
        this.saslEnabled = saslEnabled;
     //   this.tlsMode = tlsMode;
        this.compression = compression;
       // this.proxyType = proxyType;
        this.proxyHost = proxyHost;
        this.proxyPort = proxyPort;
        this.proxyUser = proxyUser;
        this.proxyPassword = proxyPassword;
    }

    /**
     * Sets password.
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
