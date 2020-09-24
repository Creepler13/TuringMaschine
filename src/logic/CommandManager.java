package logic;

import java.util.HashMap;
import java.util.Map.Entry;

public class CommandManager {

	public HashMap<String, HashMap<String, Command>> Commands = new HashMap<>();

	public CommandManager() {
	}

	public void add(Command com) {
		if (get(com.state, com.data) == null) {
			HashMap<String, Command> temp;
			if (Commands.containsKey(com.state)) {
				temp = Commands.get(com.state);
			} else {
				temp = new HashMap<>();
			}
			temp.put(com.data, com);
			Commands.put(com.state, temp);
		}
	}

	public void remove(String state, String data) {
		if (get(state, data) != null) {
			Commands.get(state).remove(data);
		}
	}

	public Command get(String state, String data) {
		if (Commands.containsKey(state)) {
			HashMap<String, Command> temp = Commands.get(state);
			if (temp.containsKey(data)) {
				return temp.get(data);
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public String toString() {
		String out = "";
		for (Entry<String, HashMap<String, Command>> e : Commands.entrySet()) {
			for (Entry<String, Command> es : e.getValue().entrySet()) {
				Command temp = es.getValue();
				out = out + "\n" + e.getKey() + "," + es.getKey() + "\n" + temp.nextState + "," + temp.print + ","
						+ temp.dir;
			}
		}
		out = out.replaceFirst("\n", "");
		return out;
	}

}
