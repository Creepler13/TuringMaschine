package logic;

import java.util.HashMap;

public class CommandManager {

	public HashMap<String, HashMap<String, Command>> Commands = new HashMap<>();

	public CommandManager() {
	}

	public void add(Command com) {
		if (get(com.state, com.data) == null) {
			HashMap<String, Command> temp = new HashMap<>();
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
			}
		} else {
			return null;
		}
		return null;
	}
}
