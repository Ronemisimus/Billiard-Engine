package runner;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
/**
 * not used yet. should save all data in a file to load later
 * @author Ronen
 */
public class Saver {

	private String savedata;
	public Saver() {

	}
	public void save(int i){
		Path path=Paths.get(System.getProperty("user.home"),"\\save",i+"",".txt");
		try (OutputStream out = new BufferedOutputStream(
			Files.newOutputStream(path, CREATE, APPEND))) {
			out.write(savedata.getBytes(), 0, savedata.length());
		} catch (IOException x) {
			System.err.println(x);
		}
	}
	public void load(int i){
		try (InputStream in = Files.newInputStream(Paths.get(System.getProperty("user.home"),"\\save",i+"",".txt"))){
  	    	BufferedReader reader =new BufferedReader(new InputStreamReader(in));
  	    	String line = null;
  	    	while ((line = reader.readLine()) != null) {
  	    		System.out.println(line);
  	    	}
		}catch (IOException x) {
  	    	System.err.println(x);
		}
	}
}
