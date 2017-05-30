package app;

import gui.Principal;

import javax.swing.JFrame;

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
        if (args.length > 0)
            initTerminal(args);
        else
            initGui();
    }

    /**
     * Method for run the terminal mode application.
     *
     * @param args The arguments given by console.
     */
    static void initTerminal(String[] args) {
        String action = "";
        Utilities.mode = Utilities.TERMINAL;
        System.out.println("+-------------------+");
        System.out.println("| Networks Analyzer |");
        System.out.println("+-------------------+\n");
        for (String arg : args) {
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
            else if (arg.equalsIgnoreCase("-h")
                    || arg.equalsIgnoreCase("--h")
                    || arg.equalsIgnoreCase("help")
                    || arg.equalsIgnoreCase("-help")
                    || arg.equalsIgnoreCase("--help"))
                action = "help";
        }

        String help = "Help\n";
        help += "\t" + Utilities.HOSTS + " [-f|-m|-c]\tList the hosts in the network.\n";
        help += "\t" + Utilities.INTERFACES + "      \tList the current host network interfaces.";
        if (action.isEmpty())
            System.out.println((System.getProperty("os.name").toLowerCase().contains("win") ? "" : "\u001B[31m") + "Unknown command\n\n" + help);
        else {
            if (action.equals(Utilities.HOSTS))
                Utilities.listHosts();
            else if (action.equals(Utilities.INTERFACES))
                Utilities.listNetInterfaces();
            else if (action.equalsIgnoreCase("help"))
                System.out.println(help);
        }
    }

    /**
     * Method for run the GUI mode application.
     */
    static void initGui() {
        Principal principal = new Principal();
        Utilities.mode = Utilities.GUI;
        Utilities.TXA_CONSOLE = principal.txaConsole;
        JFrame jFrame = new JFrame("Networks Analyzer");
        jFrame.setContentPane(principal.panel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
