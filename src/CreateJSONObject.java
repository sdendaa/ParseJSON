import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import org.json.JSONObject;

public class CreateJSONObject implements Serializable{
	/**
	 * used serializationId to serialize each JSon object
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) throws ParseException {
		File inputFile = new File("data_file.txt");
		try{
			//output streem
			FileOutputStream fileOut = new FileOutputStream("out_file.txt");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			Scanner inputScanner = new Scanner(inputFile);
			String line;
			while(inputScanner.hasNextLine()){
				line = inputScanner.nextLine();
				String name = createJsonName(line.substring(0, 20));
				String dob = createDateFormat(line.substring(20,  28));
				String ssn = line.substring(28, 37);
				
				JSONObject json = new JSONObject();
				
				json.put("dob", dob);
				json.put("ssn", ssn);
				json.put("name", name);
				System.out.println(json.toString().replaceAll("\\\\", ""));
				out.writeObject(json.toString().replaceAll("\\\\", ""));
				
			}	
			inputScanner.close();
			out.close();
			fileOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
 
	}
	
	 // convert the date format
	private static String createDateFormat(String input) throws ParseException{
		String formatedDate = null;
		//remove all white space and check if it is 8 length
		String newString =input.replaceAll(" ","");
        if (newString.length() == 8) {
            SimpleDateFormat originalFormat = new SimpleDateFormat("MMddyyyy");
            Date date = originalFormat.parse(newString);
            SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd/yyyy");
            formatedDate = newFormat.format(date);

        } else {
        	//throw error if the date of birth is less than 8 digit
            throw new Error("Date is invalid format");
        }
        return formatedDate;
	}
	
	// split first and lastName by comma and create JSON for names
	private static String createJsonName(String fullName){
		JSONObject JsonName = new JSONObject();
		//check if the name has comma(lastName and firstName)
		if(fullName.contains(",")){
			//split name by comma and trim whitespace in between
			String[] names = fullName.trim().split("\\s*,\\s*");
	        JsonName.put("lastName", names[0]);
	        JsonName.put("firstName", names[1]);

		}else{
			//if the name has no first name; just return lastName and no need to create jSON
			return fullName;
		}
		
        return JsonName.toString();
	}

}
