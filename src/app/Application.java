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
        String action = "";
        if (args.length > 0)
            System.out.println("Arguments");
        Utilities.mode = args.length > 0 ? Utilities.TERMINAL : Utilities.GUI;
        for (String arg : args) {
            System.out.println(arg);
            if (arg.equalsIgnoreCase("-f"))
                Utilities.type = Utilities.FAST;
            else if (arg.equalsIgnoreCase("-m"))
                Utilities.type = Utilities.MEDIUM;
            else if (arg.equalsIgnoreCase("-c"))
                Utilities.type = Utilities.COMPLETE;
            else if (arg.equalsIgnoreCase(Utilities.HOSTS))
                action = Utilities.HOSTS;
            else if (arg.equalsIgnoreCase(Utilities.INTERFACES))
                action = Utilities.INTERFACES;
        }
        if (args.length > 0)
            System.out.println("\n\n");

        if (action.isEmpty()) {
            if (Utilities.isTerminal())
                System.out.println("Bad command, you can write " + Utilities.HOSTS + " or " + Utilities.INTERFACES);
        } else {
            if (action.equals(Utilities.HOSTS))
                Utilities.listHosts();
            if (action.equals(Utilities.INTERFACES))
                Utilities.listNetInterfaces();
        }
    }
}
