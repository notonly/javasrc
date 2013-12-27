package di.spring;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import di.View;

@Component("myView")
// BEGIN main
public class ConsoleViewer implements View {

	Model messageProvider;
	
	@Override
	public void displayMessage() {
		System.out.println(messageProvider.getMessage());
	}

	@Resource(name="myModel")
	public void setModel(Model messageProvider) {
		this.messageProvider = messageProvider;
	}

}
// END main
