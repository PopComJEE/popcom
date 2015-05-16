package pages;

public class KeepAlive {

	private static String mString;

	public static void setString(String s){
		mString =s;
	}

	public static String getString(){
		if(mString==null)
			return "NULL";
		else
			return mString;
	}
}
