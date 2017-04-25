package app;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Class for utilities and useful methods.
 *
 * @author Elkin Fabian Ossa Zamudio
 * @since 2017-04-20
 */
class Utilities {

    /**
     * Static method for get the host IP.
     *
     * @return The host ip.
     */
    static String getIp() {
        String ip = "0.0.0.0";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * Method for verify if can ping to an specified IP address.
     *
     * @param destinationIp The destionation IP
     * @return True if can ping, false if not.
     */
    static boolean ping(String destinationIp) {
        try {
            return InetAddress.getByName(destinationIp).isReachable(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for list the network interfaces.
     *
     * @param ip The IP
     *
     * @return A list with the networks interfaces.
     */
    static ArrayList<String> listNetInterfaces(String ip) {
        ArrayList<String> netInterfaces = new ArrayList<>();
        try {
            NetworkInterface netInterface = NetworkInterface.getByInetAddress(castIp(ip));
            Enumeration<NetworkInterface> n = netInterface.getSubInterfaces();
            while (n.hasMoreElements()) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements()) {
                    InetAddress addr = a.nextElement();
                    netInterfaces.add(addr.getHostAddress());
                    System.out.println(addr.getHostAddress());
                    System.out.println(addr.getHostName());
                    System.out.println("\n\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netInterfaces;
    }

    static void experiment() {
        try {
            System.out.println(InetAddress.getByName("10.0.1.6").isReachable(1000));
//            for (InetAddress addr : InetAddress.getAllByName("10.0.1.7"))
//                System.out.println(addr.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static InetAddress castIp(String ip) {
        try {
            return InetAddress.getByName(ip);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return InetAddress.getLoopbackAddress();
    }
}
