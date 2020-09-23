package InterFace;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

import logic.Command;
import logic.TuringMaschine;

public class Frame extends JFrame {

	private JPanel contentPane;
	private JTextField newStateInput;
	private JTextField newSymbolInput;
	private TuringMaschine turing = new TuringMaschine();

	private JButton addStateBtn;
	private JComboBox<String> printSet;
	private JComboBox<String> nextStateSet;
	private JComboBox<String> dataSet;
	private JComboBox<String> stateSet;
	private JButton AddComBtn;
	private JTextArea output;
	private JComboBox<String> DirectSet;
	private JButton addSymbolBtn;
	private JTree tree;
	private JButton removeSymbolBtn;
	private JButton btnRemoveState;
	private JComboBox<String> removeSymbolSet;
	private JComboBox<String> removeStateSet;
	private JButton removeCommandBtn;
	private JComboBox<String> removeCommandSet;
	private JComboBox<String> startSet;
	private JComboBox<String> endSet;
	private JButton startBtn;
	private JTextField startInput;
	private JComboBox<String> countSet;
	private JButton countSymbolBtn;
	private JButton saveBtn;
	private JTextField saveInput;
	private JButton loadBtn;
	private JComboBox<String> loadSet;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 914, 506);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		AddComBtn = new JButton("Add Command");
		AddComBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!treeContains(getUnder(2), stateSet.getSelectedItem() + ", " + dataSet.getSelectedItem())) {
					turing.CM.add(new Command((String) stateSet.getSelectedItem(), (String) dataSet.getSelectedItem(),
							(String) nextStateSet.getSelectedItem(), (String) printSet.getSelectedItem(),
							(String) DirectSet.getSelectedItem()));

					addCommand((String) stateSet.getSelectedItem(), (String) dataSet.getSelectedItem(),
							(String) nextStateSet.getSelectedItem(), (String) printSet.getSelectedItem(),
							(String) DirectSet.getSelectedItem());

				} else {
					turing.CM.remove((String) stateSet.getSelectedItem(), (String) dataSet.getSelectedItem());

					turing.CM.add(new Command((String) stateSet.getSelectedItem(), (String) dataSet.getSelectedItem(),
							(String) nextStateSet.getSelectedItem(), (String) printSet.getSelectedItem(),
							(String) DirectSet.getSelectedItem()));

					updateCommand((String) stateSet.getSelectedItem(), (String) dataSet.getSelectedItem(),
							(String) nextStateSet.getSelectedItem(), (String) printSet.getSelectedItem(),
							(String) DirectSet.getSelectedItem());
				}
			}
		});

		DirectSet = new JComboBox<String>();
		DirectSet.setModel(new DefaultComboBoxModel(new String[] { "Right", "Left", "Stay" }));

		newStateInput = new JTextField();
		newStateInput.setToolTipText("State");
		newStateInput.setColumns(10);

		addStateBtn = new JButton("Add state");
		addStateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = newStateInput.getText();
				if (!s.equals("")) {
					if (!treeContains(getUnder(1), s)) {
						addState(s);
					}
				}
				newStateInput.setText("");
			}
		});

		newSymbolInput = new JTextField();
		newSymbolInput.setToolTipText("State");
		newSymbolInput.setColumns(10);

		addSymbolBtn = new JButton("Add Symbol");
		addSymbolBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = newSymbolInput.getText();
				if (!s.equals("") && s.length() == 1) {
					if (!treeContains(getUnder(0), s)) {
						addSymbol(s);
					}
				}
				newSymbolInput.setText("");
			}
		});

		tree = new JTree();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("JTree") {
			{
				DefaultMutableTreeNode node_1;
				node_1 = new DefaultMutableTreeNode("Symbols");
				node_1.add(new DefaultMutableTreeNode(" "));
				this.add(node_1);
				node_1 = new DefaultMutableTreeNode("States");
				node_1.add(new DefaultMutableTreeNode("A"));
				node_1.add(new DefaultMutableTreeNode("END"));
				this.add(node_1);
				node_1 = new DefaultMutableTreeNode("Commands");
				this.add(node_1);
			}
		}));
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);

		printSet = new JComboBox<String>();
		nextStateSet = new JComboBox<String>();
		dataSet = new JComboBox<String>();
		stateSet = new JComboBox<String>();
		removeSymbolSet = new JComboBox<String>();
		removeStateSet = new JComboBox<String>();

		removeSymbolBtn = new JButton("Remove Symbol");
		removeSymbolBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) removeSymbolSet.getSelectedItem();
				MutableTreeNode node = getUnder(0);
				if (treeContains(node, s) && getTreeCount(node) > 1) {
					removeNode(node, s);
				}
			}
		});

		btnRemoveState = new JButton("Remove State");
		btnRemoveState.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) removeStateSet.getSelectedItem();
				MutableTreeNode node = getUnder(1);
				if (treeContains(node, s) && getTreeCount(node) > 2) {
					removeNode(node, s);
				}
			}
		});

		removeCommandBtn = new JButton("Remove Command");
		removeCommandBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String s = (String) removeCommandSet.getSelectedItem();
				MutableTreeNode node = getUnder(2);
				if (treeContains(node, s)) {
					removeNode(node, s);
					turing.CM.remove(s.split(", ")[0], s.split(", ")[1]);
				}
			}
		});

		removeCommandSet = new JComboBox<String>();

		startSet = new JComboBox<String>();

		endSet = new JComboBox<String>();

		startBtn = new JButton("Start");
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String input = startInput.getText();
				turing.setBand(input, getTreeContents(getUnder(0)));
				turing.setStartEnd((String) startSet.getSelectedItem(), (String) endSet.getSelectedItem());
				output.setText("");
				while (!turing.next()) {
					printTuringOut(turing.band.toString());
				}
				print("End: " + turing.band.toString());
			}
		});

		startInput = new JTextField();
		startInput.setToolTipText("State");
		startInput.setColumns(10);

		countSet = new JComboBox<String>();

		countSymbolBtn = new JButton("Count");
		countSymbolBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String s = (String) countSet.getSelectedItem();
					int temp = 0;

					for (String iterable_element : turing.band.Band) {
						if (iterable_element.equals(s))
							temp++;
					}
					print("Count of " + s + ": " + temp);
				} catch (NullPointerException ex) {

				}
			}
		});

		updateSelectors();

		output = new JTextArea();
		output.setEditable(false);

		saveBtn = new JButton("Save");
		saveBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Save();
			}
		});

		saveInput = new JTextField();
		saveInput.setToolTipText("State");
		saveInput.setColumns(10);

		loadBtn = new JButton("Load");

		loadSet = new JComboBox<String>();

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
				.createSequentialGroup()
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPane.createSequentialGroup().addGap(77).addGroup(gl_contentPane
								.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(
										startBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(startInput, Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
										.addComponent(startSet, GroupLayout.PREFERRED_SIZE, 119,
												GroupLayout.PREFERRED_SIZE)
										.addGap(62).addComponent(endSet, GroupLayout.PREFERRED_SIZE, 119,
												GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(countSymbolBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(countSet, Alignment.LEADING, 0, 119, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(loadBtn, GroupLayout.PREFERRED_SIZE, 119,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(loadSet, GroupLayout.PREFERRED_SIZE, 119,
												GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(saveInput)
										.addComponent(saveBtn, GroupLayout.DEFAULT_SIZE, 119, Short.MAX_VALUE)))
						.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
								.addComponent(output, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE)))
				.addGap(18)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(tree, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addGroup(gl_contentPane
								.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(AddComBtn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE,
										GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(DirectSet, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(nextStateSet, Alignment.LEADING, 0, 119, Short.MAX_VALUE)
								.addComponent(printSet, Alignment.LEADING, 0, 119, Short.MAX_VALUE)
								.addComponent(dataSet, 0, 119, Short.MAX_VALUE)
								.addComponent(stateSet, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
								.addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(removeCommandSet, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(newSymbolInput, GroupLayout.PREFERRED_SIZE, 119,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(addSymbolBtn, GroupLayout.PREFERRED_SIZE, 119,
																GroupLayout.PREFERRED_SIZE))
												.addGap(18)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
														.addComponent(newStateInput).addComponent(addStateBtn,
																GroupLayout.PREFERRED_SIZE, 115,
																GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
														.addComponent(removeSymbolSet, 0, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(removeSymbolBtn, GroupLayout.DEFAULT_SIZE, 119,
																Short.MAX_VALUE))
												.addGap(18)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
														.addComponent(btnRemoveState, GroupLayout.PREFERRED_SIZE, 115,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(removeStateSet, GroupLayout.PREFERRED_SIZE, 115,
																GroupLayout.PREFERRED_SIZE)))
										.addComponent(removeCommandBtn, GroupLayout.DEFAULT_SIZE,
												GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
				.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap()
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(output, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
								.addComponent(tree, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
								.createSequentialGroup()
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(stateSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(endSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(startSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(dataSet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addComponent(startInput, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(3))))
								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(addSymbolBtn)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
														.addComponent(newSymbolInput, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(newStateInput, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addComponent(addStateBtn))))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING).addGroup(gl_contentPane
								.createSequentialGroup().addGap(18)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_contentPane.createSequentialGroup()
												.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(nextStateSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(removeSymbolSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(removeStateSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGap(13)
												.addComponent(printSet, GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(DirectSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(removeCommandSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(countSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(saveInput, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
														.addComponent(loadSet, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addPreferredGap(ComponentPlacement.UNRELATED)
												.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
														.addComponent(AddComBtn).addComponent(removeCommandBtn)
														.addComponent(countSymbolBtn).addComponent(saveBtn)
														.addComponent(loadBtn)))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(31)
												.addComponent(removeSymbolBtn))
										.addGroup(gl_contentPane.createSequentialGroup().addGap(31)
												.addComponent(btnRemoveState))))
								.addGroup(gl_contentPane.createSequentialGroup().addGap(8).addComponent(startBtn)))
						.addContainerGap()));
		contentPane.setLayout(gl_contentPane);

		File[] tempmp = getSaveUrls();
		for (File iterable_element : tempmp) {
			System.out.println(iterable_element.getPath());
		}
	}

	private void addCommand(String state, String data, String nextState, String print, String dir) {

		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

		DefaultMutableTreeNode comm = new DefaultMutableTreeNode(state + ", " + data);

		comm.add(new DefaultMutableTreeNode("Symbol: " + data));
		comm.add(new DefaultMutableTreeNode("Next state: " + nextState));
		comm.add(new DefaultMutableTreeNode("Symbol to set: " + print));
		comm.add(new DefaultMutableTreeNode("Direction: " + dir));
		comm.add(new DefaultMutableTreeNode("State: " + state));

		model.insertNodeInto(comm, getUnder(2), getUnder(2).getChildCount());

		model.reload(root);
		tree.scrollPathToVisible(new TreePath(comm.getPath()));

		updateSelectors();
	}

	private void updateCommand(String state, String data, String nextState, String print, String dir) {
		removeNode(getUnder(2), state + ", " + data);
		addCommand(state, data, nextState, print, dir);
	}

	private Boolean treeContains(MutableTreeNode root, String s) {
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = ((DefaultMutableTreeNode) root).children();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (s.equals(node.toString())) {
				return true;
			}
		}
		return false;
	}

	private int getTreeCount(MutableTreeNode root) {
		return root.getChildCount();
	}

	private String[] getTreeContents(MutableTreeNode root) {
		ArrayList<String> temp = new ArrayList<>();
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = ((DefaultMutableTreeNode) root).children();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			temp.add(node.toString());
		}
		return temp.toArray(new String[temp.size()]);
	}

	private void removeNode(MutableTreeNode root, String s) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = ((DefaultMutableTreeNode) root).children();
		while (e.hasMoreElements()) {
			DefaultMutableTreeNode node = e.nextElement();
			if (s.equals(node.toString())) {
				root.remove(node);
				updateSelectors();
				model.reload(root);
				return;
			}
		}
	}

	private void updateSelectors() {
		updateSelNoCha(printSet, 0);
		updateSelNoCha(nextStateSet, 1);
		updateSelNoCha(dataSet, 0);
		updateSelNoCha(stateSet, 1);
		updateSelNoCha(removeSymbolSet, 0);
		updateSelNoCha(removeStateSet, 1);
		updateSelNoCha(removeCommandSet, 2);
		updateSelNoCha(startSet, 1);
		updateSelNoCha(endSet, 1);
		updateSelNoCha(countSet, 0);
	}

	private void updateSelNoCha(JComboBox<String> e, int dataIndex) {
		int temp = e.getSelectedIndex();
		String[] tempArr = getTreeContents(getUnder(dataIndex));
		e.setModel(new DefaultComboBoxModel<String>(tempArr));
		if (tempArr.length <= temp || temp == -1)
			temp = tempArr.length - 1;
		e.setSelectedIndex(temp);

	}

	private void addSymbol(String sym) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode comm = new DefaultMutableTreeNode(sym);
		model.insertNodeInto(comm, getUnder(0), getUnder(0).getChildCount());

		model.reload(root);
		tree.scrollPathToVisible(new TreePath(comm.getPath()));

		updateSelectors();
	}

	private void addState(String sym) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode comm = new DefaultMutableTreeNode(sym);
		model.insertNodeInto(comm, getUnder(1), getUnder(1).getChildCount());

		model.reload(root);
		tree.scrollPathToVisible(new TreePath(comm.getPath()));

		updateSelectors();
	}

	private MutableTreeNode getUnder(int index) {
		DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) root.getChildAt(index);
		return node;
	}

	private void print(String s) {
		String si = output.getText();
		String[] temp = si.split("\n");
		if (temp.length == 12) {
			si = "";
			for (int i = 1; i < temp.length; i++) {
				si = si + temp[i] + "\n";
			}

		}
		output.setText(si + s + "\n");
	}

	private void printTuringOut(String s) {
		print(s);
		String temp = "^";
		String temp1 = turing.state;
		for (int i = 0; i < turing.band.index + 1; i++) {
			temp = " " + temp;
			temp1 = " " + temp1;
		}
		print(temp);
		print(temp1);
	}

	private File[] getSaveUrls() {
		ArrayList<File> temp = new ArrayList<>();
		File f = new File(System.getProperty("user.dir"));
		File[] dirs = f.listFiles(e -> e.isDirectory());
		File[] temp2 = f.listFiles(s -> s.getName().endsWith(".turingSave"));
		for (File file : temp2) {
			temp.add(file);
		}
		for (File dirdir : dirs) {
			temp2 = dirdir.listFiles(s -> s.getName().endsWith(".turingSave"));
			for (File file : temp2) {
				temp.add(file);
			}
		}
		return temp.toArray(new File[temp.size()]);
	}

	private void Save() {
		saveBtn.setText("Save");
		String name = saveInput.getText();
		saveInput.setText("");
		if (name == "") {
			name = new Date().toString() + "- Turing Save";
		}
		String url = System.getProperty("user.dir") + "/" + name + ".turingSave";
		File f = new File(url);
		if (!f.exists()) {
			System.out.println("Exis");
			try {
				f.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			saveBtn.setText("Save / Name already exits");
			return;
		}

		try {
			FileWriter myWriter = new FileWriter(f);
			String Symbols = "";
			String[] symArr = getTreeContents(getUnder(0));
			for (String string : symArr) {
				Symbols = Symbols + "," + string;
			}
			Symbols = Symbols.replaceFirst(",", "");

			myWriter.write(Symbols + "\n");

			String States = "";
			String[] staArr = getTreeContents(getUnder(1));
			for (String string : staArr) {
				States = States + "," + string;
			}
			States = States.replaceFirst(",", "");

			myWriter.write(States + "\n");

			myWriter.write(startSet.getSelectedItem() + "\n");
			myWriter.write(endSet.getSelectedItem() + "\n");

			String Comms = turing.CM.toString();

			myWriter.write(Comms);
			
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
