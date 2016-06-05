package finals;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Runner extends JFrame {
	private static final String MY_CSV_FILES[] = { "trace-2k.csv", "trace-5k.csv" };
	private static final String MY_CONFIG_FILE = "config.txt";
	private File myCSVFileInput;
	private File myConfigFile;

	private JSpinner mySelectInputFileSpinner;
	private JSpinner myL1SizeSpinner;
	private JSpinner myL2SizeSpinner;
	private JSpinner myL3SizeSpinner;
	private JSpinner myLM1SizeSpinner;
	private JSpinner myLM2SizeSpinner;

	private JSpinner myL1LatencySpinner;
	private JSpinner myL2LatencySpinner;
	private JSpinner myL3LatencySpinner;
	private JSpinner myLM1LatencySpinner;
	private JSpinner myLM2ReadLatencySpinner;
	private JSpinner myLM2WriteLatencySpinner;
	private JSpinner myAssociativitySpinner;
	private JSpinner myCacheLineSizeSpinner;

	private JButton myEnterBTN;
	private JToolBar mySpinnerToolBar;
	private JTextArea myTextArea;

	private String mySelectedFileName;
	private int myL1Size;
	private int myL2Size;
	private int myL3Size;
	private int myLM1Size;
	private int myLM2Size;

	private int myL1Latency;
	private int myL2Latency;
	private int myL3Latency;
	private int myLM1Latency;
	private int myLM2ReadLatency;
	private int myLM2WriteLatency;
	private int myAssociativity;
	private int myCacheLineSize;
	private int myTotalLatency;
	
	/*public static int CACHE_LINE_SIZE = 32;
	public static int NUMBER_OF_WAYS = 1;*/

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
		mySelectInputFileSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				mySelectedFileName = (String) mySelectInputFileSpinner.getValue();

			}
		});
		myL1SizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myL1Size = Integer.parseInt((String) myL1SizeSpinner.getValue());

			}
		});
		myL2SizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myL2Size = Integer.parseInt((String) myL2SizeSpinner.getValue());

			}
		});
		myL3SizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myL3Size = Integer.parseInt((String) myL3SizeSpinner.getValue());

			}
		});
		myLM1SizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myLM1Size = Integer.parseInt((String) myLM1SizeSpinner.getValue());

			}
		});
		myLM2SizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myLM2Size = Integer.parseInt((String) myLM2SizeSpinner.getValue());

			}
		});
		myL1LatencySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myL1Latency = Integer.parseInt((String) myL1LatencySpinner.getValue());

			}
		});
		myL2LatencySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myL2Latency = Integer.parseInt((String) myL2LatencySpinner.getValue());

			}
		});
		myL3LatencySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myL3Latency = Integer.parseInt((String) myL3LatencySpinner.getValue());

			}
		});
		myLM1LatencySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myLM1Latency = Integer.parseInt((String) myLM1LatencySpinner.getValue());

			}
		});
		myLM2ReadLatencySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myLM2ReadLatency = Integer.parseInt((String) myLM2ReadLatencySpinner.getValue());

			}
		});
		myLM2WriteLatencySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myLM2WriteLatency = Integer.parseInt((String) myLM2WriteLatencySpinner.getValue());

			}
		});
		myAssociativitySpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myAssociativity = Integer.parseInt((String) myAssociativitySpinner.getValue());

			}
		});
		myCacheLineSizeSpinner.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				myCacheLineSize = Integer.parseInt((String) myCacheLineSizeSpinner.getValue());

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
		mySpinnerToolBar.add(mySelectInputFileSpinner);
		mySpinnerToolBar.add(new JLabel("Select L1 Size"));
		mySpinnerToolBar.add(myL1SizeSpinner);
		mySpinnerToolBar.add(new JLabel("Select L2 Size"));
		mySpinnerToolBar.add(myL2SizeSpinner);
		mySpinnerToolBar.add(new JLabel("Select L3 Size"));
		mySpinnerToolBar.add(myL3SizeSpinner);
		mySpinnerToolBar.add(new JLabel("Select LM1 Size"));
		mySpinnerToolBar.add(myLM1SizeSpinner);
		mySpinnerToolBar.add(new JLabel("Select LM2 Size"));
		mySpinnerToolBar.add(myLM2SizeSpinner);
		mySpinnerToolBar.add(new JLabel("Select L1 Latency"));
		mySpinnerToolBar.add(myL1LatencySpinner);
		mySpinnerToolBar.add(new JLabel("Select L2 Latency"));
		mySpinnerToolBar.add(myL2LatencySpinner);
		mySpinnerToolBar.add(new JLabel("Select L3 Latency"));
		mySpinnerToolBar.add(myL3LatencySpinner);
		mySpinnerToolBar.add(new JLabel("Select LM1 Latency"));
		mySpinnerToolBar.add(myLM1LatencySpinner);
		mySpinnerToolBar.add(new JLabel("Select LM2 Read Latency"));
		mySpinnerToolBar.add(myLM2ReadLatencySpinner);
		mySpinnerToolBar.add(new JLabel("Select LM2 Write Latency"));
		mySpinnerToolBar.add(myLM2WriteLatencySpinner);
		mySpinnerToolBar.add(new JLabel("Select N-Way Associativity"));
		mySpinnerToolBar.add(myAssociativitySpinner);
		mySpinnerToolBar.add(new JLabel("Select Cache Line Size"));
		mySpinnerToolBar.add(myCacheLineSizeSpinner);

		mySpinnerToolBar.add(myEnterBTN);
		this.setTitle("Cache Coherent Simulator");
		this.add(mySpinnerToolBar, BorderLayout.WEST);
		this.add(new JScrollPane(myTextArea), BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}

	private void initializeVariables() {
		myL1Size = Integer.parseInt((String) myL1SizeSpinner.getValue());
		myL2Size = Integer.parseInt((String) myL2SizeSpinner.getValue());
		myL3Size = Integer.parseInt((String) myL3SizeSpinner.getValue());
		myLM1Size = Integer.parseInt((String) myLM1SizeSpinner.getValue());
		myLM2Size = Integer.parseInt((String) myLM2SizeSpinner.getValue());
		myL1Latency = Integer.parseInt((String) myL1LatencySpinner.getValue());
		myL2Latency = Integer.parseInt((String) myL2LatencySpinner.getValue());
		myL3Latency = Integer.parseInt((String) myL3LatencySpinner.getValue());
		myLM1Latency = Integer.parseInt((String) myLM1LatencySpinner.getValue());
		myLM2ReadLatency = Integer.parseInt((String) myLM2ReadLatencySpinner.getValue());
		myLM2WriteLatency = Integer.parseInt((String) myLM2WriteLatencySpinner.getValue());
		myAssociativity = Integer.parseInt((String) myAssociativitySpinner.getValue());
		myCacheLineSize = Integer.parseInt((String) myCacheLineSizeSpinner.getValue());
		mySelectedFileName = (String) mySelectInputFileSpinner.getValue();
		myTotalLatency = 0;
	}

	private void buildComponents() {
		myTextArea = new JTextArea(20, 50);
		myTextArea.setEditable(false); //Uneditable text Area
		mySpinnerToolBar = new JToolBar(JToolBar.VERTICAL);
		myEnterBTN = new JButton("Run Simulator");
		String[] values1 = { "32", "128" };
		myL1SizeSpinner = new JSpinner(new SpinnerListModel(values1));
		String[] values2 = { "512", "1024" };
		myL2SizeSpinner = new JSpinner(new SpinnerListModel(values2));
		String[] values3 = { "2048", "4096" };
		myL3SizeSpinner = new JSpinner(new SpinnerListModel(values3));
		String[] values4 = { "16", "32" };
		myLM1SizeSpinner = new JSpinner(new SpinnerListModel(values4));
		String[] values5 = { "1024", "2048" };// KB
		myLM2SizeSpinner = new JSpinner(new SpinnerListModel(values5));
		String[] values6 = { "1", "2" };
		myL1LatencySpinner = new JSpinner(new SpinnerListModel(values6));
		String[] values7 = { "10", "12" };
		myL2LatencySpinner = new JSpinner(new SpinnerListModel(values7));
		String[] values8 = { "35", "40" };
		myL3LatencySpinner = new JSpinner(new SpinnerListModel(values8));
		String[] values9 = { "100" };
		myLM1LatencySpinner = new JSpinner(new SpinnerListModel(values9));
		String[] values10 = { "250" };
		myLM2ReadLatencySpinner = new JSpinner(new SpinnerListModel(values10));
		String[] values11 = { "400" };
		myLM2WriteLatencySpinner = new JSpinner(new SpinnerListModel(values11));
		String[] values12 = { "1", "2", "4", "8", "16" };
		myAssociativitySpinner = new JSpinner(new SpinnerListModel(values12));
		String[] values13 = { "trace-2k.csv", "trace-5k.csv" };
		mySelectInputFileSpinner = new JSpinner(new SpinnerListModel(values13));
		String[] values14 = { "16", "64" };
		myCacheLineSizeSpinner = new JSpinner(new SpinnerListModel(values14));
	}

	public static int getCacheLineSize() {
		return 32;
	}
	public static int getNumberOfWays() {
		return 1;
	}
	
	public void start() {
		Cache L1DCache = new Cache(4, 32, 1); //4 word, 32 entries, 1ns latency
		Cache L1ICache = new Cache(4, 32, 1); //4 word, 32 entries, 1ns latency
		
		
	}
}
