package view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import model.CachingRunner;

/**
 * This is the primary class for our project. Handles adding entries in cache,
 * checking if entries exist, and displays the GUI.
 * 
 * @author Rowan and Fortune
 *
 */
@SuppressWarnings("serial")
public class GUIRunner extends JFrame
{
	/** Array index for L1 Size. */
	private static int		L1_SIZE_INDEX			= 0;
	/** Array index for L2 Size. */
	private static int		L2_SIZE_INDEX			= 1;
	/** Array index for L3 Size. */
	private static int		L3_SIZE_INDEX			= 2;
	/** Array index for LM1 Size. */
	private static int		LM1_SIZE_INDEX			= 3;
	/** Array index for LM2 Size. */
	private static int		LM2_SIZE_INDEX			= 4;
	/** Array index for L1 Latency. */
	private static int		L1_LAT_INDEX			= 5;
	/** Array index for L2 Latency. */
	private static int		L2_LAT_INDEX			= 6;
	/** Array index for L3 Latency. */
	private static int		L3_LAT_INDEX			= 7;
	/** Array index for LM1 Latency. */
	private static int		LM1_LAT_INDEX			= 8;
	/** Array index for LM2 Read Latency. */
	private static int		LM2_READ_LAT_INDEX		= 9;
	/** Array index for LM2 Write Latency. */
	private static int		LM2_WRITE_LAT_INDEX		= 10;
	/** Array index for Associativity. */
	private static int		ASSOCIATIVITY_INDEX		= 11;
	/** Array index for Cache Line Size. */
	private static int		CACHE_LINE_SIZE_INDEX	= 12;
	/** Array index for Selected Input File. */
	private static int		FILE_INDEX				= 13;
	/** An array of user selected values for caching. */
	private int[]			mySettings;
	/** An array of Spinners used to get user input. */
	private JSpinner[]		mySpinners;
	/** The CSV file for input. */
	private File			myCSVFileInput;
	/** Enter button used to start caching process. */
	private JButton			myEnterBTN;
	/** Toolbar that contains all of the spinners for user input. */
	private JToolBar		mySpinnerToolBar;
	
	private CachingRunner	myCachingRunner;

	/**
	 * Initializes the variables and kicks off our GUI through methods
	 */
	public GUIRunner()
	{
		buildComponents ();
		initializeVariables ();
		attachListenersToToolBarComponents ();
		buildFrame ();
	}

	/**
	 * Attaches Listeners to Button and Spinners
	 */
	private void attachListenersToToolBarComponents()
	{
		mySpinners[FILE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				myCSVFileInput = new File ((String) mySpinners[FILE_INDEX].getValue ());

			}
		});
		mySpinners[L1_SIZE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[L1_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[L1_SIZE_INDEX].getValue ());

			}
		});
		mySpinners[L2_SIZE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[L2_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[L2_SIZE_INDEX].getValue ());

			}
		});
		mySpinners[L3_SIZE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[L3_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[L3_SIZE_INDEX].getValue ());

			}
		});
		mySpinners[LM1_SIZE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[LM1_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[LM1_SIZE_INDEX].getValue ());

			}
		});
		mySpinners[LM2_SIZE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[LM2_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[LM2_SIZE_INDEX].getValue ());

			}
		});
		mySpinners[L1_LAT_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[L1_LAT_INDEX] = Integer.parseInt ((String) mySpinners[L1_LAT_INDEX].getValue ());

			}
		});
		mySpinners[L1_LAT_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[L1_LAT_INDEX] = Integer.parseInt ((String) mySpinners[L1_LAT_INDEX].getValue ());

			}
		});
		mySpinners[L3_LAT_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[L3_LAT_INDEX] = Integer.parseInt ((String) mySpinners[L3_LAT_INDEX].getValue ());

			}
		});
		mySpinners[LM1_LAT_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[LM1_LAT_INDEX] = Integer.parseInt ((String) mySpinners[LM1_LAT_INDEX].getValue ());

			}
		});
		mySpinners[LM2_READ_LAT_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[LM2_READ_LAT_INDEX] = Integer.parseInt ((String) mySpinners[LM2_READ_LAT_INDEX].getValue ());

			}
		});
		mySpinners[LM2_WRITE_LAT_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[LM2_WRITE_LAT_INDEX] = Integer
						.parseInt ((String) mySpinners[LM2_WRITE_LAT_INDEX].getValue ());

			}
		});
		mySpinners[ASSOCIATIVITY_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[ASSOCIATIVITY_INDEX] = Integer
						.parseInt ((String) mySpinners[ASSOCIATIVITY_INDEX].getValue ());

			}
		});
		mySpinners[CACHE_LINE_SIZE_INDEX].addChangeListener (new ChangeListener ()
		{

			@Override
			public void stateChanged(ChangeEvent e)
			{
				mySettings[CACHE_LINE_SIZE_INDEX] = Integer
						.parseInt ((String) mySpinners[CACHE_LINE_SIZE_INDEX].getValue ());

			}
		});

		myEnterBTN.addActionListener (new ActionListener ()
		{

			@Override
			public void actionPerformed(ActionEvent e)
			{
				ArrayList<String[]> csvInput = new ArrayList<String[]> ();
				Scanner scanner;
				try
				{
					scanner = new Scanner (myCSVFileInput);
					while (scanner.hasNextLine ())
					{

						csvInput.add (scanner.nextLine ().split (","));

					}
					scanner.close ();
				}
				catch (FileNotFoundException c)
				{
					c.printStackTrace ();
				}
				
				myCachingRunner = new CachingRunner(csvInput, mySettings);
				myCachingRunner.start ();
				//myTextArea.setText (myCachingRunner.printStats().toString ());
				showpopup(myCachingRunner.printStats().toString ());
			}
		});
		
	}
	
	private void showpopup(String msg) {
		JTextArea textArea = new JTextArea(msg);
		JScrollPane scrollPane = new JScrollPane(textArea);  
		textArea.setLineWrap(true);  
		textArea.setWrapStyleWord(true); 
		scrollPane.setPreferredSize( new Dimension(400, 500 ) );
		JOptionPane.showMessageDialog(this, scrollPane);
	}

	/**
	 * Builds our JFrame for GUI display
	 */
	private void buildFrame()
	{
		mySpinnerToolBar.add (new JLabel ("Select Input File"));
		mySpinnerToolBar.add (mySpinners[FILE_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select L1 Size"));
		mySpinnerToolBar.add (mySpinners[L1_SIZE_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select L2 Size"));
		mySpinnerToolBar.add (mySpinners[L2_SIZE_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select L3 Size"));
		mySpinnerToolBar.add (mySpinners[L3_SIZE_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select LM1 Size"));
		mySpinnerToolBar.add (mySpinners[LM1_SIZE_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select LM2 Size"));
		mySpinnerToolBar.add (mySpinners[LM2_SIZE_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select L1 Latency"));
		mySpinnerToolBar.add (mySpinners[L1_LAT_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select L2 Latency"));
		mySpinnerToolBar.add (mySpinners[L2_LAT_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select L3 Latency"));
		mySpinnerToolBar.add (mySpinners[L3_LAT_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select LM1 Latency"));
		mySpinnerToolBar.add (mySpinners[LM1_LAT_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select LM2 Read Latency"));
		mySpinnerToolBar.add (mySpinners[LM2_READ_LAT_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select LM2 Write Latency"));
		mySpinnerToolBar.add (mySpinners[LM2_WRITE_LAT_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select N-Way Associativity"));
		mySpinnerToolBar.add (mySpinners[ASSOCIATIVITY_INDEX]);
		mySpinnerToolBar.add (new JLabel ("Select Cache Line Size"));
		mySpinnerToolBar.add (mySpinners[CACHE_LINE_SIZE_INDEX]);

		mySpinnerToolBar.add (myEnterBTN);
		mySpinnerToolBar.setFloatable(false);
		this.setTitle ("Cache Coherent Simulator");
		this.add (mySpinnerToolBar, BorderLayout.WEST);
		//this.add (new JScrollPane (myTextArea), BorderLayout.CENTER);
		this.setVisible (true);
		this.pack ();
		this.setDefaultCloseOperation (EXIT_ON_CLOSE);
	}

	/**
	 * Initializes all the Variables used in this class.
	 */
	private void initializeVariables()
	{

		mySettings[L1_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[L1_SIZE_INDEX].getValue ());
		mySettings[L2_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[L2_SIZE_INDEX].getValue ());
		mySettings[L3_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[L3_SIZE_INDEX].getValue ());
		mySettings[LM1_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[LM1_SIZE_INDEX].getValue ());
		mySettings[LM2_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[LM2_SIZE_INDEX].getValue ());
		mySettings[L1_LAT_INDEX] = Integer.parseInt ((String) mySpinners[L1_LAT_INDEX].getValue ());
		mySettings[L2_LAT_INDEX] = Integer.parseInt ((String) mySpinners[L2_LAT_INDEX].getValue ());
		mySettings[L3_LAT_INDEX] = Integer.parseInt ((String) mySpinners[L3_LAT_INDEX].getValue ());
		mySettings[LM1_LAT_INDEX] = Integer.parseInt ((String) mySpinners[LM1_LAT_INDEX].getValue ());
		mySettings[LM2_READ_LAT_INDEX] = Integer.parseInt ((String) mySpinners[LM2_READ_LAT_INDEX].getValue ());
		mySettings[LM2_WRITE_LAT_INDEX] = Integer.parseInt ((String) mySpinners[LM2_WRITE_LAT_INDEX].getValue ());
		mySettings[ASSOCIATIVITY_INDEX] = Integer.parseInt ((String) mySpinners[ASSOCIATIVITY_INDEX].getValue ());
		mySettings[CACHE_LINE_SIZE_INDEX] = Integer.parseInt ((String) mySpinners[CACHE_LINE_SIZE_INDEX].getValue ());
		myCSVFileInput = new File ((String) mySpinners[FILE_INDEX].getValue ());
		
	}

	/**
	 * Builds all the Components this Program needs to run.
	 */
	private void buildComponents()
	{
		mySettings = new int[14];
		mySpinners = new JSpinner[14];
		mySpinnerToolBar = new JToolBar (JToolBar.VERTICAL);
		myEnterBTN = new JButton ("Run Simulator");
		String[] values1 = { "32", "128" };
		mySpinners[L1_SIZE_INDEX] = new JSpinner (new SpinnerListModel (values1));
		String[] values2 = { "512", "1024" };
		mySpinners[L2_SIZE_INDEX] = new JSpinner (new SpinnerListModel (values2));
		String[] values3 = { "2048", "4096" };
		mySpinners[L3_SIZE_INDEX] = new JSpinner (new SpinnerListModel (values3));
		String[] values4 = { "16", "32" };
		mySpinners[LM1_SIZE_INDEX] = new JSpinner (new SpinnerListModel (values4));
		String[] values5 = { "1024", "2048" };// KB
		mySpinners[LM2_SIZE_INDEX] = new JSpinner (new SpinnerListModel (values5));
		String[] values6 = { "1", "2" };
		mySpinners[L1_LAT_INDEX] = new JSpinner (new SpinnerListModel (values6));
		String[] values7 = { "10", "12" };
		mySpinners[L2_LAT_INDEX] = new JSpinner (new SpinnerListModel (values7));
		String[] values8 = { "35", "40" };
		mySpinners[L3_LAT_INDEX] = new JSpinner (new SpinnerListModel (values8));
		String[] values9 = { "100" };
		mySpinners[LM1_LAT_INDEX] = new JSpinner (new SpinnerListModel (values9));
		String[] values10 = { "250" };
		mySpinners[LM2_READ_LAT_INDEX] = new JSpinner (new SpinnerListModel (values10));
		String[] values11 = { "400" };
		mySpinners[LM2_WRITE_LAT_INDEX] = new JSpinner (new SpinnerListModel (values11));
		String[] values12 = { "1", "2", "4", "8", "16" };
		mySpinners[ASSOCIATIVITY_INDEX] = new JSpinner (new SpinnerListModel (values12));
		String[] values13 = { "trace-2k.csv", "trace-5k.csv" };
		mySpinners[FILE_INDEX] = new JSpinner (new SpinnerListModel (values13));
		String[] values14 = { "16", "64" };
		mySpinners[CACHE_LINE_SIZE_INDEX] = new JSpinner (new SpinnerListModel (values14));
	}
}
