package app;

/**
 * Main class of the application.
 *
 * @author Elkin Fabian Ossa Zamudio
 * @since 2017-04-20
 */
public class Application {

    /**
     * Main method for run the java application.
     *
     * @param args The args send by CLI.
     */
    public static void main(String[] args) {
        System.out.println("Arguments");
        for (String arg : args) {
            System.out.println(arg);
        }
        System.out.println("\n\n");
//        System.out.println(Utilities.ping(args[1]));
        Utilities.listHosts();
    }
}
