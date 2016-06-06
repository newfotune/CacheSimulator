package finals;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Runner extends JFrame {

	private static int L1_SIZE_INDEX = 0;
	private static int L2_SIZE_INDEX = 1;
	private static int L3_SIZE_INDEX = 2;
	private static int LM1_SIZE_INDEX = 3;
	private static int LM2_SIZE_INDEX = 4;
	private static int L1_LAT_INDEX = 5;
	private static int L2_LAT_INDEX = 6;
	private static int L3_LAT_INDEX = 7;
	private static int LM1_LAT_INDEX = 8;
	private static int LM2_READ_LAT_INDEX = 9;
	private static int LM2_WRITE_LAT_INDEX = 10;
	private static int ASSOCIATIVITY_INDEX = 11;
	private static int CACHE_LINE_SIZE_INDEX = 12;
	private static int FILE_INDEX = 13;
	
	
	private int[] mySettings;
	private JSpinner[] mySpinners;
	private File myCSVFileInput;
	private int myTotalLatency;
	private JButton myEnterBTN;
	private JToolBar mySpinnerToolBar;
	private JTextArea myTextArea;
	
	private CPU cpu1 = ceateCpu1();
	private CPU cpu2 = ceateCpu1();
	

	/**
	 * Initializes the variables
	 */
	public Runner() {
		buildComponents();
		initializeVariables();
		
		
		attachListenersToToolBarComponents();
		buildFrame();

	}

	private void attachListenersToToolBarComponents() {
		mySpinners[FILE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myCSVFileInput = new File((String) mySpinners[FILE_INDEX].getValue());

			}
		});
		mySpinners[L1_SIZE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[L1_SIZE_INDEX] = Integer.parseInt((String) mySpinners[L1_SIZE_INDEX].getValue());

			}
		});
		mySpinners[L2_SIZE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[L2_SIZE_INDEX] = Integer.parseInt((String) mySpinners[L2_SIZE_INDEX].getValue());

			}
		});
		mySpinners[L3_SIZE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[L3_SIZE_INDEX] = Integer.parseInt((String) mySpinners[L3_SIZE_INDEX].getValue());

			}
		});
		mySpinners[LM1_SIZE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[LM1_SIZE_INDEX] = Integer.parseInt((String) mySpinners[LM1_SIZE_INDEX].getValue());

			}
		});
		mySpinners[LM2_SIZE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[LM2_SIZE_INDEX] = Integer.parseInt((String) mySpinners[LM2_SIZE_INDEX].getValue());

			}
		});
		mySpinners[L1_LAT_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[L1_LAT_INDEX] = Integer.parseInt((String) mySpinners[L1_LAT_INDEX].getValue());

			}
		});
		mySpinners[L1_LAT_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[L1_LAT_INDEX] = Integer.parseInt((String) mySpinners[L1_LAT_INDEX].getValue());

			}
		});
		mySpinners[L3_LAT_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[L3_LAT_INDEX] = Integer.parseInt((String) mySpinners[L3_LAT_INDEX].getValue());

			}
		});
		mySpinners[LM1_LAT_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[LM1_LAT_INDEX] = Integer.parseInt((String) mySpinners[LM1_LAT_INDEX].getValue());

			}
		});
		mySpinners[LM2_READ_LAT_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[LM2_READ_LAT_INDEX] = Integer.parseInt((String) mySpinners[LM2_READ_LAT_INDEX].getValue());

			}
		});
		mySpinners[LM2_WRITE_LAT_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[LM2_WRITE_LAT_INDEX] = Integer.parseInt((String) mySpinners[LM2_WRITE_LAT_INDEX].getValue());

			}
		});
		mySpinners[ASSOCIATIVITY_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[ASSOCIATIVITY_INDEX] = Integer.parseInt((String) mySpinners[ASSOCIATIVITY_INDEX].getValue());

			}
		});
		mySpinners[CACHE_LINE_SIZE_INDEX].addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySettings[CACHE_LINE_SIZE_INDEX] = Integer.parseInt((String) mySpinners[CACHE_LINE_SIZE_INDEX].getValue());

			}
		});

		myEnterBTN.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				start();

			}
		});
	}

	private void buildFrame() {
		mySpinnerToolBar.add(new JLabel("Select Input File"));
		mySpinnerToolBar.add(mySpinners[FILE_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select L1 Size"));
		mySpinnerToolBar.add(mySpinners[L1_SIZE_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select L2 Size"));
		mySpinnerToolBar.add(mySpinners[L2_SIZE_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select L3 Size"));
		mySpinnerToolBar.add(mySpinners[L3_SIZE_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select LM1 Size"));
		mySpinnerToolBar.add(mySpinners[LM1_SIZE_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select LM2 Size"));
		mySpinnerToolBar.add(mySpinners[LM2_SIZE_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select L1 Latency"));
		mySpinnerToolBar.add(mySpinners[L1_LAT_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select L2 Latency"));
		mySpinnerToolBar.add(mySpinners[L2_LAT_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select L3 Latency"));
		mySpinnerToolBar.add(mySpinners[L3_LAT_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select LM1 Latency"));
		mySpinnerToolBar.add(mySpinners[LM1_LAT_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select LM2 Read Latency"));
		mySpinnerToolBar.add(mySpinners[LM2_READ_LAT_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select LM2 Write Latency"));
		mySpinnerToolBar.add(mySpinners[LM2_WRITE_LAT_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select N-Way Associativity"));
		mySpinnerToolBar.add(mySpinners[ASSOCIATIVITY_INDEX]);
		mySpinnerToolBar.add(new JLabel("Select Cache Line Size"));
		mySpinnerToolBar.add(mySpinners[CACHE_LINE_SIZE_INDEX]);

		mySpinnerToolBar.add(myEnterBTN);
		this.setTitle("Cache Coherent Simulator");
		this.add(mySpinnerToolBar, BorderLayout.WEST);
		this.add(new JScrollPane(myTextArea), BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}

	private void initializeVariables() {
		mySettings[L1_SIZE_INDEX] = Integer.parseInt((String) mySpinners[L1_SIZE_INDEX].getValue());
		mySettings[L2_SIZE_INDEX] = Integer.parseInt((String) mySpinners[L2_SIZE_INDEX].getValue());
		mySettings[L3_SIZE_INDEX] = Integer.parseInt((String) mySpinners[L3_SIZE_INDEX].getValue());
		mySettings[LM1_SIZE_INDEX] = Integer.parseInt((String) mySpinners[LM1_SIZE_INDEX].getValue());
		mySettings[LM2_SIZE_INDEX] = Integer.parseInt((String) mySpinners[LM2_SIZE_INDEX].getValue());
		mySettings[L1_LAT_INDEX] = Integer.parseInt((String) mySpinners[L1_LAT_INDEX].getValue());
		mySettings[L2_LAT_INDEX] = Integer.parseInt((String) mySpinners[L2_LAT_INDEX].getValue());
		mySettings[L3_LAT_INDEX] = Integer.parseInt((String) mySpinners[L3_LAT_INDEX].getValue());
		mySettings[LM1_LAT_INDEX] = Integer.parseInt((String) mySpinners[LM1_LAT_INDEX].getValue());
		mySettings[LM2_READ_LAT_INDEX] = Integer.parseInt((String) mySpinners[LM2_READ_LAT_INDEX].getValue());
		mySettings[LM2_WRITE_LAT_INDEX] = Integer.parseInt((String) mySpinners[LM2_WRITE_LAT_INDEX].getValue());
		mySettings[ASSOCIATIVITY_INDEX] = Integer.parseInt((String) mySpinners[ASSOCIATIVITY_INDEX].getValue());
		mySettings[CACHE_LINE_SIZE_INDEX] = Integer.parseInt((String) mySpinners[CACHE_LINE_SIZE_INDEX].getValue());
		myCSVFileInput = new File((String) mySpinners[FILE_INDEX].getValue());
		myTotalLatency = 0;
	}

	private void buildComponents() {
		mySettings = new int[14];
		mySpinners = new JSpinner[14];
		myTextArea = new JTextArea(20, 50);
		myTextArea.setEditable(false); //make the text Area nonn editable
		
		mySpinnerToolBar = new JToolBar(JToolBar.VERTICAL);
		myEnterBTN = new JButton("Run Simulator");
		String[] values1 = { "32", "128" };
		mySpinners[L1_SIZE_INDEX] = new JSpinner(new SpinnerListModel(values1));
		String[] values2 = { "512", "1024" };
		mySpinners[L2_SIZE_INDEX] = new JSpinner(new SpinnerListModel(values2));
		String[] values3 = { "2048", "4096" };
		mySpinners[L3_SIZE_INDEX] = new JSpinner(new SpinnerListModel(values3));
		String[] values4 = { "16", "32" };
		mySpinners[LM1_SIZE_INDEX] = new JSpinner(new SpinnerListModel(values4));
		String[] values5 = { "1024", "2048" };// KB
		mySpinners[LM2_SIZE_INDEX] = new JSpinner(new SpinnerListModel(values5));
		String[] values6 = { "1", "2" };
		mySpinners[L1_LAT_INDEX] = new JSpinner(new SpinnerListModel(values6));
		String[] values7 = { "10", "12" };
		mySpinners[L2_LAT_INDEX] = new JSpinner(new SpinnerListModel(values7));
		String[] values8 = { "35", "40" };
		mySpinners[L3_LAT_INDEX] = new JSpinner(new SpinnerListModel(values8));
		String[] values9 = { "100" };
		mySpinners[LM1_LAT_INDEX] = new JSpinner(new SpinnerListModel(values9));
		String[] values10 = { "250" };
		mySpinners[LM2_READ_LAT_INDEX] = new JSpinner(new SpinnerListModel(values10));
		String[] values11 = { "400" };
		mySpinners[LM2_WRITE_LAT_INDEX] = new JSpinner(new SpinnerListModel(values11));
		String[] values12 = { "1", "2", "4", "8", "16" };
		mySpinners[ASSOCIATIVITY_INDEX] = new JSpinner(new SpinnerListModel(values12));
		String[] values13 = { "trace-2k.csv", "trace-5k.csv" };
		mySpinners[FILE_INDEX] = new JSpinner(new SpinnerListModel(values13));
		String[] values14 = { "16", "64" };
		mySpinners[CACHE_LINE_SIZE_INDEX] = new JSpinner(new SpinnerListModel(values14));
	}

	public void start() {
		ArrayList<String[]> list = new ArrayList<String[]>();		
		Scanner scanner;
		try {
			scanner = new Scanner(myCSVFileInput);
			while (scanner.hasNextLine()) {

				list.add(scanner.nextLine().split(","));
				
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CPU current = cpu1;
		CPU other = cpu2;
		
		for(int i = 0; i < list.size(); i++)
		{
			if (i % 50 == 0) {
				//cputemp = currentcpu;
				//cpucurrent = oldCPU;
				//oldCPU = cputemp;
			}
			for(int cpu1Counter = 0; cpu1Counter < 100; cpu1Counter++ )
			{
				
			}
			for(int cpu2Counter = 0; cpu2Counter < 100; cpu2Counter++)
			{
				
			}
		}	
	}
	
	private CPU ceateCpu1() {
		Cache L1I = new Cache(mySettings[0],mySettings[L1_LAT_INDEX]);
		Cache L1D = new Cache(mySettings[0],mySettings[L1_LAT_INDEX]);
		
		Cache L2 = new Cache(mySettings[1],mySettings[L2_LAT_INDEX]);
		CPU theCPU = new CPU (L1I, L1D, L2);
		
		return theCPU;
	}
	public static double getCacheLineSize() {
		return 32;
	}
	public static int getNumberOfWays() {
		return 1;
	}
}
