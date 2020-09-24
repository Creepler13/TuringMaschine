package logic;

import java.util.ArrayList;

public class Band {

	public ArrayList<String> Band;
	public int index = 0;

	public Band(ArrayList<String> start) {
		Band = new ArrayList<>();
		Band.addAll(start);
	}

	public ArrayList<String> getBand() {
		return Band;
	}

	public String toString() {
		String temp = "";
		for (String string : Band) {
			temp = temp + string;
		}
		return temp;
	}

	public String apply(Command com) {

		Band.set(index, com.print);

		int move = 0;
		switch (com.dir) {
		case "Right":
			move = 1;
			break;
		case "Left":
			move = -1;
			break;
		case "Stay":
			move = 0;
			break;
		}

		index = index + move;
		if (index < 0) {
			index = 0;
			ArrayList<String> t = new ArrayList<>();
			t.add(" ");
			t.addAll(Band);
			Band = t;
		}
		if (index > Band.size() - 1) {
			Band.add(" ");
		}

		return com.nextState;
	}

	public String getData() {
		return Band.get(index);
	}

}
