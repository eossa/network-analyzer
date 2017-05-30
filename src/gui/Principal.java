package gui;

import app.Utilities;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Main class of the GUI application.
 *
 * @author Elkin Fabian Ossa Zamudio
 * @since 2017-05-29
 */
public class Principal {
    /**
     * The panel of the frame.
     */
    public JPanel panel;
    /**
     * The button for scan the interfaces.
     */
    private JButton btnScan;
    /**
     * The text area for show the results.
     */
    public JTextArea txaConsole;
    /**
     * The button for fast scan.
     */
    private JButton btnFast;
    /**
     * The button for medium scan.
     */
    private JButton btnMedium;
    /**
     * The button for complete scan.
     */
    private JButton btnComplete;

    /**
     * Constructor of class Principal that add the Listeners.
     */
    public Principal() {
        btnScan.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e The event object.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.listNetInterfaces();
            }
        });
        btnFast.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e The event object.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.type = Utilities.FAST;
                Utilities.listHosts();
            }
        });
        btnMedium.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e The event object.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.type = Utilities.MEDIUM;
                Utilities.listHosts();
            }
        });
        btnComplete.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e The event object.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilities.type = Utilities.COMPLETE;
                Utilities.listHosts();
            }
        });
    }
}
