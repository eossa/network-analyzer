package app;

import java.net.InetAddress;

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
//            System.out.println("Your Host addr: " + InetAddress.getLocalHost().getHostAddress());
//            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
//            while (n.hasMoreElements()) {
//                NetworkInterface e = n.nextElement();
//
//                Enumeration<InetAddress> a = e.getInetAddresses();
//                while (a.hasMoreElements()) {
//                    InetAddress addr = a.nextElement();
//                    System.out.println("  " + addr.getHostAddress());
//                }
//            }
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
}
