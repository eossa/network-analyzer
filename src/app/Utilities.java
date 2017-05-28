package app;

import com.sun.jndi.dns.DnsClient;
import sun.net.ftp.FtpClient;
import sun.net.smtp.SmtpClient;
import sun.net.www.http.HttpClient;

import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * Class for utilities and useful methods.
 *
 * @author Elkin Fabian Ossa Zamudio
 * @since 2017-04-20
 */
class Utilities {
    /**
     * Array for cast an CIDR mask to a long value.
     *
     * @see <a href="http://facedroid.blogspot.com.co/2010/06/ip-range-to-cidr.html">IP Range To CIDR</a>
     */
    private static final int[] CIDR2MASK = new int[]{0x00000000, 0x80000000,
            0xC0000000, 0xE0000000, 0xF0000000, 0xF8000000, 0xFC000000,
            0xFE000000, 0xFF000000, 0xFF800000, 0xFFC00000, 0xFFE00000,
            0xFFF00000, 0xFFF80000, 0xFFFC0000, 0xFFFE0000, 0xFFFF0000,
            0xFFFF8000, 0xFFFFC000, 0xFFFFE000, 0xFFFFF000, 0xFFFFF800,
            0xFFFFFC00, 0xFFFFFE00, 0xFFFFFF00, 0xFFFFFF80, 0xFFFFFFC0,
            0xFFFFFFE0, 0xFFFFFFF0, 0xFFFFFFF8, 0xFFFFFFFC, 0xFFFFFFFE,
            0xFFFFFFFF};
    /**
     * Constant for execution type.
     */
    static final Integer FAST = 1;
    /**
     * Constant for execution type.
     */
    static final Integer MEDIUM = 2;
    /**
     * Constant for execution type.
     */
    static final Integer COMPLETE = 3;
    /**
     * Execution type constant.
     */
    static Integer type = FAST;

    /**
     * Constant for execution mode.
     */
    static final Integer TERMINAL = 4;
    /**
     * Constant for execution mode.
     */
    static final Integer GUI = 5;
    /**
     * Execution mode constant.
     */
    static Integer mode = TERMINAL;

    /**
     * Action constant for list hosts.
     */
    static final String HOSTS = "hosts";
    /**
     * Action constant for list interfaces.
     */
    static final String INTERFACES = "interfaces";

    /**
     * Method for list the hosts of the network.
     *
     * @return The hosts list.
     * @author Elkin Fabian Ossa Zamudio
     */
    static List<Host> listHosts() {
        List<Host> hostsList = new ArrayList<>();
        try {
            InetAddress ip = getIp();
            NetworkInterface networkInterface = NetworkInterface.getByInetAddress(ip);
            short mask = 32;

            // Find the IPv4 mask.
            for (InterfaceAddress address : networkInterface.getInterfaceAddresses()) {
                // Validate IPv4
                if (address.getAddress().getHostAddress().split("\\.").length == 4)
                    mask = address.getNetworkPrefixLength();
            }

            // The network IP address in long format.
            long netIp = CIDR2MASK[mask] & ipToLong(ip.getHostAddress());

            // The number of hosts in the network (2^n)
            double numHosts = Math.pow(2, 32 - mask);

            // Verify the conected hosts in the network.
            for (short i = 1; i <= numHosts; i++) {
                String otherHost = longToIP(netIp + i);

                // Ping to know if the host is connected.
                boolean ping = ping(otherHost);

                // If can ping, added to array.
                if (ping) {
                    if (isTerminal())
                        System.out.println("Host found: " + otherHost);
                    Host host = new Host(otherHost);

                    if (mode.equals(MEDIUM))
                        host.setPorts(listPorts(otherHost));

                    if (mode.equals(COMPLETE))
                        host.setServices(listServices(otherHost));

                    hostsList.add(host);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hostsList;
    }

    /**
     * Static method for get the host IP.
     *
     * @return The host ip.
     * @author <a href="https://stackoverflow.com/users/635098/roylaurie">roylaurie</a>
     * @see <a href="https://stackoverflow.com/questions/8083479/java-getting-my-ip-address">Java getting my IP address</a>
     */
    private static InetAddress getIp() {
        InetAddress ip = InetAddress.getLoopbackAddress();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // Filters out 127.0.0.1 and inactive interfaces
                if (iface.isLoopback() || !iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = addresses.nextElement();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * Method for verify if can ping to an specified IP address.
     *
     * @param destinationIp The destination IP
     * @return True if can ping, false if not.
     * @author Elkin Fabian Ossa Zamudio
     */
    private static boolean ping(String destinationIp) {
        try {
            return castIp(destinationIp).isReachable(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for list the opened ports of a given host IP.
     *
     * @param ip The host IP.
     * @return The opened ports list of the given host IP.
     * @author Elkin Fabian Ossa Zamudio
     */
    private static List<Short> listPorts(String ip) {
        List<Short> ports = new ArrayList<>();
        for (short port = 0; port <= 1024; port++) {
            try {
                Socket echo = new Socket(ip, port);

                if (isTerminal())
                    System.out.println("Port " + port + " enabled");
                ports.add(port);

                echo.close();
            } catch (ConnectException ce) {
                continue;
            } catch (NoRouteToHostException nrthe) {
                continue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ports;
    }

    /**
     * Method for list the available services of a given host IP.
     *
     * @param ip The host IP.
     * @return The available services list of the given host IP.
     * @author Elkin Fabian Ossa Zamudio
     */
    private static List<String> listServices(String ip) {
        FtpClient ftpClient;
        HttpClient httpClient;
        HttpClient httpsClient;
        SmtpClient smtpClient;
        DnsClient dnsClient;
        Boolean available;
        List<String> services = new ArrayList<>();
        for (String service : new String[]{"FTP", "HTTP", "HTTPS", "SMTP", "DNS"}) {
            available = false;
            try {
                switch (service) {
                    case "FTP":
                        ftpClient = FtpClient.create(ip);
                        available = ftpClient.isConnected();
                        ftpClient.close();
                        break;
                    case "HTTP":
                        httpClient = HttpClient.New(new URL("http://" + ip));
                        available = httpClient.serverIsOpen();
                        httpClient.closeServer();
                        break;
                    case "HTTPS":
                        httpsClient = HttpClient.New(new URL("https://" + ip));
                        available = httpsClient.serverIsOpen();
                        httpsClient.closeServer();
                        break;
                    case "SMTP":
                        smtpClient = new SmtpClient(ip);
                        available = smtpClient.serverIsOpen();
                        smtpClient.closeServer();
                        break;
                    case "DNS":
                        dnsClient = new DnsClient(new String[]{ip}, 1000, 5);
                        available = true;
                        dnsClient.close();
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (available) {
                if (isTerminal())
                    System.out.println("Service " + service + " available.");
                services.add(service);
            }
        }
        return services;
    }

    /**
     * Method for list the network interfaces.
     *
     * @return A list with the networks interfaces.
     * @author <a href="https://docs.oracle.com">docs.oracle.com</a>
     * @see <a href="https://docs.oracle.com/javase/tutorial/networking/nifs/listing.html">Listing Network Interface Addresses</a>
     */
    static ArrayList<String> listNetInterfaces() {
        ArrayList<String> netInterfaces = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                if (isTerminal()) {
                    System.out.printf("Display name: %s\n", netint.getDisplayName());
                    System.out.printf("Name: %s\n", netint.getName());
                }
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    if (isTerminal())
                        System.out.printf("InetAddress: %s\n", inetAddress);
                    netInterfaces.add(netint.getDisplayName() + " - " + inetAddress.toString());
                }
                if (isTerminal())
                    System.out.printf("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netInterfaces;
    }

    /**
     * Method for cast a String format IP to an InetAddress format.
     *
     * @param ip The String format IP.
     * @return The InetAddress format IP.
     * @author Elkin Fabian Ossa Zamudio
     */
    private static InetAddress castIp(String ip) {
        try {
            return InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InetAddress.getLoopbackAddress();
    }

    /**
     * Cast a String format IP to long.
     *
     * @param strIP The String format IP.
     * @return The long format IP.
     * @author <a href="http://www.dantearaujo.net/">Dante Araujo</a>
     * @see <a href="http://facedroid.blogspot.com.co/2010/06/ip-range-to-cidr.html">IP Range To CIDR</a>
     */
    private static long ipToLong(String strIP) {
        long[] ip = new long[4];
        String[] ipSec = strIP.split("\\.");
        for (int k = 0; k < 4; k++) {
            ip[k] = Long.valueOf(ipSec[k]);
        }

        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
    }

    /**
     * Cast a long type IP to String.
     *
     * @param longIP The long format IP.
     * @return The String format IP.
     * @author <a href="http://www.dantearaujo.net/">Dante Araujo</a>
     * @see <a href="http://facedroid.blogspot.com.co/2010/06/ip-range-to-cidr.html">IP Range To CIDR</a>
     */
    private static String longToIP(long longIP) {
        return String.valueOf(longIP >>> 24) +
                "." +
                String.valueOf((longIP & 0x00FFFFFF) >>> 16) +
                "." +
                String.valueOf((longIP & 0x0000FFFF) >>> 8) +
                "." +
                String.valueOf(longIP & 0x000000FF);
    }

    static boolean isTerminal() {
        return mode.equals(TERMINAL);
    }

    static void experiment() {
        try {
            String var = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
