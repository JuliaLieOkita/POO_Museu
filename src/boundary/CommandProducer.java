package boundary;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ObservableValue;

public class CommandProducer {
	
	private List<CommandExecution> executionList = new ArrayList<>();

    public void addExecution(CommandExecution exe) {
        executionList.add(exe);
    }

    public void removeExecution(CommandExecution exe) {
        executionList.remove(exe);
    }

    public void executeCommand(String command) {
        for (CommandExecution exe : executionList) {
            exe.execute(command);
        }
    }


	
}