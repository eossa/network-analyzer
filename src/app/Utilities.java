package app;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
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
            return castIp(destinationIp).isReachable(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Method for list the network interfaces.
     *
     * @return A list with the networks interfaces.
     */
    static ArrayList<String> listNetInterfaces() {
        byte b = 126 + 1;
        ArrayList<String> netInterfaces = new ArrayList<>();
        try {
            Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets)) {
                System.out.printf("Display name: %s\n", netint.getDisplayName());
                System.out.printf("Name: %s\n", netint.getName());
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    System.out.printf("InetAddress: %s\n", inetAddress);
                    netInterfaces.add(netint.getDisplayName()+" - "+inetAddress.toString());
                }
                System.out.printf("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return netInterfaces;
    }

    static void experiment() {
        try {
            NetworkInterface.getNetworkInterfaces();
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
