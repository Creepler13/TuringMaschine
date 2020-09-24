package logic;

import java.util.ArrayList;

public class TuringMaschine {

	public Band band;
	public String state = "";
	public CommandManager CM = new CommandManager();
	public String endState = "";

	public TuringMaschine() {
	}

	public void setBand(String s, String[] sy) {
		band = new Band(CleanInput(s, sy));
	}

	public void setBand(ArrayList<String> s) {
		band = new Band(s);
	}

	public void setStartEnd(String start, String end) {
		this.state = start;
		this.endState = end;
	}

	private Boolean nextStep() {
		try {
			if (state.equals(endState)) {
				return true;
			}
			state = band.apply(CM.get(state, band.getData()));
		} catch (Exception e) {
			System.out.println(state);
			System.err.println("Command not Set  " + state + ", " + band.getData());
			return true;
		}
		return false;
	}

	public Boolean next(int wait) {
		try {
			Thread.sleep(wait);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nextStep();
	}

	public Boolean next() {
		return nextStep();
	}

	public ArrayList<String> CleanInput(String input, String[] symbols) {
		ArrayList<String> tempt = new ArrayList<String>();
		String[] temp = input.split("");
		for (String string : temp) {
			for (String string2 : symbols) {
				if (string2.equals(string)) {
					tempt.add(string);
					break;
				}
			}
		}
		return tempt;
	}

}
