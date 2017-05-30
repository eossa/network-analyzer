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
        System.out.println("Networks Analyzer");
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
        }

        if (action.isEmpty()) {
            System.out.println("Bad command, you can write " + Utilities.HOSTS + " or " + Utilities.INTERFACES);
        } else {
            if (action.equals(Utilities.HOSTS))
                Utilities.listHosts();
            if (action.equals(Utilities.INTERFACES))
                Utilities.listNetInterfaces();
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
