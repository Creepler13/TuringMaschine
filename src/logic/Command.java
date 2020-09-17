package logic;

public class Command {

	public String data, state, nextState, print, dir;

	public Command(String state, String data, String nextState, String print, String dir) {
		this.data = data;
		this.state = state;
		this.nextState = nextState;
		this.dir = dir;
		this.print = print;
	}

}
