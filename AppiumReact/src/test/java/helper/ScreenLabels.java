package helper;

public class ScreenLabels {

	public enum WelcomeScreen {
	    Description("Description"),
	    Keyboard_Keys("Keyboard key:"),
	    Keyboard_Delete("Keyboard key: delete"),
	    WrongPin_Error("Error"),
		WrongPin_Description("Wrong MPIN"),
		WrongPin_Ok("Button: Ok"),
		ForgetPass_Description("Forget password"),
		ForgetPass_Ok("Button: Ok"),
		ForgetPass_Recover("Button: Recover"),
		ForgetPass_EmailInput("Input: Enter your email address"),
		ForgetPass_Button("Button: Forgotten your email?"),
		Support_Label("Support"),
		Support_LaterButton("Button: Do this later"),
		Support_BackButton("Button: Back"),
		;
		
	    private final String value;

	    WelcomeScreen(String value) {
	        this.value = value;
	    }

	    public String getValue() {
	        return value;
	    }
	}
}
