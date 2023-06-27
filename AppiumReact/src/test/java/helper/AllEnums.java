package helper;

public class AllEnums {

	//Enum to locate an element - AID, XPATH, ID, CLASSNAME
	public enum LocateBy {

		AID {
			@Override
			public String toString() {
				return "accessibilityId";
			}
		},

		XPATH {
			@Override
			public String toString() {
				return "xpath";
			}
		},

		ID {
			@Override
			public String toString() {
				return "id";
			}
		},

		CLASSNAME {
			@Override
			public String toString() {
				return "class";
			}
		}

	}
	
	public enum structureType{
		view{
			@Override
			public String toString()
			{
				return "view.";
			}
		},
		widget{
			@Override
			public String toString()
			{
				return "widget.";
			}
		}
		
		
	}
	
	//Enum to identify the orientation of a list for building its xpath generically - Horizontal, Vertical
	public enum ElementType {
		Horizontal {
			@Override
			public String toString() {
				return "HorizontalScrollView";
			}
		},
		Vertical {
			@Override
			public String toString() {
				return "ScrollView";
			}
		},
		ViewGroup{
			@Override
			public String toString() {
				return "ViewGroup";
			}
		}
	}
	
	
	public enum Tab{
		Home,Portfolio,Invest,Activity,Settings
	}
	
}


