package main;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

public class ResourceHelper{
	public static final ClassLoader classLoader = ResourceHelper.class.getClassLoader();
	
	private static String append(String path){ return "assets/"+path; }
	
	public static URL getResource(String path){
		return classLoader.getResource(append(path));
	}
	
	public static File getResourceAsFile(String path){
		return new File(getResource(append(path)).getFile());
	}
	
	public static InputStream getResourceAsStream(String path){
		return classLoader.getResourceAsStream(append(path));
	}
}
