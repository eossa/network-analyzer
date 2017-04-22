package app;

import java.net.InetAddress;
import java.net.NetworkInterface;

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
    
    static ArrayList<String> listINets() {
        try {
            Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
            ArrayList<String> iNets = new ArrayList<String>();
            while (n.hasMoreElements()) {
                NetworkInterface e = n.nextElement();
                Enumeration<InetAddress> a = e.getInetAddresses();
                while (a.hasMoreElements()) {
                    InetAddress addr = a.nextElement();
                    iNets.add(addr.getHostAddress());
//                    addr.getDisplayName(); TODO mirar si esto es útil.
//                    addr.getName(); TODO mirar si esto es útil.
//                    addr.toString(); TODO mirar si esto es útil.
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return iNets;
    }
}
