package eecs2030.project;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import eecs2030.project.Models.Score;
import eecs2030.project.Models.TableModel;
import eecs2030.project.Utilities.Constants;
import eecs2030.project.Utilities.Database;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;


/**
 * Created by Haider on 7/4/2017.
 */
public class Window extends JFrame implements ActionListener {

    private Box Leftbox;
    private JPanel Rightbox;
    private Database database;
    private TableModel tableModel;
    private Box mainMenuBox;

    public Window() throws Exception {
        super(Constants.GAME_TITLE);

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        this.setResizable(false);

        //Set the Main Window in the center of the Screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        // <------ INITIAL SETUP FUNCTIONS ---->
        screenRightSide();
        screenLeftSide();
        databaseSetUp();

        // <------ END SETUP FUNCTIONS --->


        this.pack();
        this.setLocationRelativeTo(null);
    }

    private void databaseSetUp() throws Exception {
        this.database = new Database();
        DatabaseReference databaseRef = database.getDatabaseRef();
        databaseRef.orderByChild("points").limitToFirst(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                tableModel.addScore(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {
                tableModel.removeScore(snapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void screenRightSide() {
        //Box Holder for the game
        Rightbox = new JPanel();
        Rightbox.setBackground(Constants.BACKGROUND_COLOR);
        Rightbox.setMinimumSize(new Dimension(((int) (Constants.WIDTH / 1.5)), Constants.HEIGHT));
        addMainMenu();
        this.add(Rightbox,BorderLayout.CENTER);
    }

    private void screenLeftSide() {
        //TODO: Initiate Left side of the screen which will have a table from the database of the top 20 HighScores.
        Leftbox = Box.createVerticalBox();
        //Object[][] data = Utils.listToArray(Score.getInstances());

        tableModel = new TableModel();

        JTable table = new JTable();
        table.setModel(tableModel);

        Leftbox.add(new JLabel(Constants.HIGHSCORES_LABEL));
        Leftbox.add(table.getTableHeader());
        Leftbox.add(table);
        this.add(Leftbox,BorderLayout.WEST);
    }

    private void addMainMenu() {
        JButton btnStart = new JButton(Constants.START_GAME_BUTTON);
        JButton btnQuit = new JButton(Constants.EXIT_GAME_BUTTON);

        btnStart.setActionCommand(Constants.START_COMMAND);
        btnQuit.setActionCommand(Constants.EXIT_COMMAND);

        btnStart.addActionListener(this);
        btnQuit.addActionListener(this);

        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainMenuBox = Box.createVerticalBox();
        mainMenuBox.add(Box.createVerticalStrut(Constants.HEIGHT / 2 - 50));
        mainMenuBox.add(btnStart);
        mainMenuBox.add(Box.createVerticalStrut(Constants.VERTICAL_PADDING));
        mainMenuBox.add(btnQuit);

        Rightbox.add(mainMenuBox);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case Constants.START_COMMAND:
                mainMenuBox.setVisible(false);
                Rightbox.add(new Game());
                this.revalidate();
                break;
            case Constants.EXIT_COMMAND:
                System.exit(0);
                break;
        }
    }
}
