package app;

import java.util.List;

/**
 * Class that contains the Host's information.
 *
 * @author Elkin Fabian Ossa Zamudio
 * @since 2015-05-27
 */
public class Host {
    /**
     * The host IP address.
     */
    private String ip;
    /**
     * The list of the ports of the host.
     */
    private List<Short> ports;
    /**
     * The list of the services of the host.
     */
    private List<String> services;

    /**
     * Constructor for create an Host instance.
     *
     * @param ip The host Ip address.
     */
    public Host(String ip) {
        this.ip = ip;
    }

    /**
     * Method for get the IP address.
     *
     * @return The IP address of the Host.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Method for set the IP address of the Host.
     *
     * @param ip The IP address of the Host.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Method for get ports' list of the Host.
     *
     * @return The ports' list of the Host.
     */
    public List<Short> getPorts() {
        return ports;
    }

    /**
     * Method for set the ports' list of the Host.
     *
     * @param ports The ports' list of the Host.
     */
    public void setPorts(List<Short> ports) {
        this.ports = ports;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "Host{ ip='" + ip + "' }";
    }
}
