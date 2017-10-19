package se.addinit.genera.generatree;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonObject;

public class JSONParser {
	
	public static List<Person>getPersons(){
		ArrayList<Person> persons=new ArrayList<Person>();
		
		String url = "http://localhost:8080/api/genealogy";
		
		try {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		//add request header
		con.setRequestProperty("User-Agent", "Mozilla/5.0");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);

//		BufferedReader in = new BufferedReader(
//		        new InputStreamReader(con.getInputStream()));
//		
//		InputStream fis=null;
//		
//			fis = new FileInputStream("c:/temp/file.json");

		JsonReader reader=Json.createReader(con.getInputStream());
		//JsonReader reader=Json.createReader(new StringReader(json));
		JsonArray jsonArray= reader.readArray();
		reader.close();
	
		for(JsonValue jsonValue:jsonArray){
			Person person=new Person();
			person.id=((JsonObject) jsonValue).getString("personId");
			person.setFirstName(((JsonObject) jsonValue).getString("firstName"));
			person.lastName=((JsonObject) jsonValue).getString("lastName");
			person.sex=((JsonObject) jsonValue).getString("sex");
			JsonArray children=((JsonObject) jsonValue).getJsonArray("childConnections");
			for(JsonValue jsonValueChild:children)
				person.children.add(jsonValueChild.toString().replaceAll("\"", ""));
			persons.add(person);
			System.out.println(person.toString());
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return persons;
	}
	
	

	private static String json="[{\"id\":63,\"personId\":\"@I428@\",\"firstName\":\"Johan August\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":57,\"personId\":\"@I2863@\",\"firstName\":\"Johanna Sofia\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":70,\"personId\":\"@I554@\",\"firstName\":\"Nils Gustaf\",\"lastName\":\"Jansson\",\"childConnections\":[]},{\"id\":1,\"personId\":\"@I523@\",\"firstName\":\"Stina Maria\",\"lastName\":\"Johansdotter\",\"childConnections\":[]},{\"id\":66,\"personId\":\"@I885@\",\"firstName\":\"Karl Ludvig\",\"lastName\":\"\u00D6sterlind\",\"childConnections\":[]},{\"id\":8,\"personId\":\"@I563@\",\"firstName\":\"Hildur Sofia\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":37,\"personId\":\"@I2719@\",\"firstName\":\"Ok\u00E4nd\",\"lastName\":\"\",\"childConnections\":[]},{\"id\":21,\"personId\":\"@I6@\",\"firstName\":\"M\u00E4rta Sofia\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":17,\"personId\":\"@I2@\",\"firstName\":\"Per Johan\",\"lastName\":\"Persson\",\"childConnections\":[\"@I753@\",\"@I851@\",\"@I206@\",\"@I466@\",\"@I755@\",\"@I721@\",\"@I2864@\",\"@I210@\",\"@I2411@\",\"@I381@\"]},{\"id\":7,\"personId\":\"@I333@\",\"firstName\":\"M\u00E4rta Katarina\",\"lastName\":\"Samuelsdotter\",\"childConnections\":[\"@I753@\",\"@I851@\",\"@I206@\",\"@I466@\",\"@I755@\",\"@I721@\",\"@I2864@\",\"@I210@\",\"@I2411@\",\"@I381@\"]},{\"id\":44,\"personId\":\"@I347@\",\"firstName\":\"Bengt Viktor\",\"lastName\":\"Klaesson\",\"childConnections\":[]},{\"id\":21,\"personId\":\"@I6@\",\"firstName\":\"M\u00E4rta Sofia\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":58,\"personId\":\"@I11@\",\"firstName\":\"Karl Johan\",\"lastName\":\"Nilsson\",\"childConnections\":[\"@I1462@\",\"@I388@\"]},{\"id\":81,\"personId\":\"@I615@\",\"firstName\":\"Johanna Sofia\",\"lastName\":\"Svensdotter\",\"childConnections\":[\"@I1462@\",\"@I388@\"]},{\"id\":73,\"personId\":\"@I2745@\",\"firstName\":\"Ok\u00E4nd\",\"lastName\":\"\",\"childConnections\":[]},{\"id\":30,\"personId\":\"@I241@\",\"firstName\":\"Albertina Josefina Lovisa\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":15,\"personId\":\"@I688@\",\"firstName\":\"Helge Efraim\",\"lastName\":\"Bergwall\",\"childConnections\":[]},{\"id\":39,\"personId\":\"@I936@\",\"firstName\":\"Sara Ingeborg\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":80,\"personId\":\"@I902@\",\"firstName\":\"Klas Emil\",\"lastName\":\"Johansson\",\"childConnections\":[\"@I478@\",\"@I391@\"]},{\"id\":43,\"personId\":\"@I424@\",\"firstName\":\"Anna Gustava\",\"lastName\":\"Danielsdotter\",\"childConnections\":[\"@I478@\",\"@I391@\"]},{\"id\":25,\"personId\":\"@I903@\",\"firstName\":\"Karl Johan Albert\",\"lastName\":\"Svensson\",\"childConnections\":[]},{\"id\":50,\"personId\":\"@I755@\",\"firstName\":\"Selma Julia Elisabeth\",\"lastName\":\"Persson\",\"childConnections\":[]},{\"id\":62,\"personId\":\"@I727@\",\"firstName\":\"Karl Leonard\",\"lastName\":\"L\u00F6fgren\",\"childConnections\":[]},{\"id\":23,\"personId\":\"@I381@\",\"firstName\":\"Emilia Augusta Leontina\",\"lastName\":\"Persson\",\"childConnections\":[]},{\"id\":22,\"personId\":\"@I781@\",\"firstName\":\"Klas Yngve\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":6,\"personId\":\"@I305@\",\"firstName\":\"Ingrid Hilda Maria\",\"lastName\":\"Lindberg\",\"childConnections\":[]},{\"id\":71,\"personId\":\"@I324@\",\"firstName\":\"G\u00F6te Allan\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":84,\"personId\":\"@I54@\",\"firstName\":\"Gunbritt Valborg Marianne\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":40,\"personId\":\"@I835@\",\"firstName\":\"Lars Peter\",\"lastName\":\"Andersson\",\"childConnections\":[]},{\"id\":60,\"personId\":\"@I91@\",\"firstName\":\"Hedvig Karolina\",\"lastName\":\"Danielsdotter\",\"childConnections\":[]},{\"id\":48,\"personId\":\"@I597@\",\"firstName\":\"Johan\",\"lastName\":\"Danielsson\",\"childConnections\":[]},{\"id\":2,\"personId\":\"@I753@\",\"firstName\":\"Tilia Josefina\",\"lastName\":\"Persson\",\"childConnections\":[]},{\"id\":11,\"personId\":\"@I422@\",\"firstName\":\"Emil Anshelm\",\"lastName\":\"Alp\",\"childConnections\":[]},{\"id\":36,\"personId\":\"@I661@\",\"firstName\":\"M\u00E4rta\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":79,\"personId\":\"@I934@\",\"firstName\":\"Axel Elis\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":41,\"personId\":\"@I60@\",\"firstName\":\"Stina Maria\",\"lastName\":\"Haglund\",\"childConnections\":[]},{\"id\":20,\"personId\":\"@I490@\",\"firstName\":\"Karl Uno\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":16,\"personId\":\"@I2853@\",\"firstName\":\"Emelie Kristina\",\"lastName\":\"Gustafsdotter\",\"childConnections\":[]},{\"id\":68,\"personId\":\"@I332@\",\"firstName\":\"Johan August Julius\",\"lastName\":\"Gustafsson\",\"childConnections\":[]},{\"id\":30,\"personId\":\"@I241@\",\"firstName\":\"Albertina Josefina Lovisa\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":46,\"personId\":\"@I206@\",\"firstName\":\"Per August Alfred\",\"lastName\":\"Petersson\",\"childConnections\":[]},{\"id\":65,\"personId\":\"@I546@\",\"firstName\":\"Emma Emilia\",\"lastName\":\"Karlsson\",\"childConnections\":[]},{\"id\":19,\"personId\":\"@I276@\",\"firstName\":\"Otto Ture\",\"lastName\":\"Karlsson\",\"childConnections\":[]},{\"id\":36,\"personId\":\"@I661@\",\"firstName\":\"M\u00E4rta\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":82,\"personId\":\"@I308@\",\"firstName\":\"Karl Viktor\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":5,\"personId\":\"@I882@\",\"firstName\":\"Elsa Tullia Valborg\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":67,\"personId\":\"@I388@\",\"firstName\":\"Nils Johan Viktor\",\"lastName\":\"Karlsson\",\"childConnections\":[\"@I1472@\",\"@I1473@\",\"@I563@\",\"@I148@\",\"@I488@\",\"@I587@\",\"@I560@\",\"@I440@\",\"@I661@\",\"@I308@\"]},{\"id\":53,\"personId\":\"@I210@\",\"firstName\":\"Hilda Sofia\",\"lastName\":\"Persson\",\"childConnections\":[\"@I1472@\",\"@I1473@\",\"@I563@\",\"@I148@\",\"@I488@\",\"@I587@\",\"@I560@\",\"@I440@\",\"@I661@\",\"@I308@\"]},{\"id\":14,\"personId\":\"@I721@\",\"firstName\":\"Karl Albert Nidian\",\"lastName\":\"Petersson\",\"childConnections\":[]},{\"id\":34,\"personId\":\"@I120@\",\"firstName\":\"Syster Mina Matilda\",\"lastName\":\"Karlsson\",\"childConnections\":[]},{\"id\":78,\"personId\":\"@I58@\",\"firstName\":\"Karl Josef\",\"lastName\":\"Karlsson Beijer\",\"childConnections\":[]},{\"id\":32,\"personId\":\"@I560@\",\"firstName\":\"Ida Teolonika\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":55,\"personId\":\"@I391@\",\"firstName\":\"Axel Emanuel Ansgarius\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[\"@I676@\",\"@I67@\",\"@I324@\",\"@I356@\",\"@I804@\",\"@I936@\",\"@I934@\",\"@I380@\",\"@I6@\",\"@I781@\"]},{\"id\":49,\"personId\":\"@I488@\",\"firstName\":\"Judit Maria\",\"lastName\":\"Nilsson\",\"childConnections\":[\"@I676@\",\"@I67@\",\"@I324@\",\"@I356@\",\"@I804@\",\"@I936@\",\"@I934@\",\"@I380@\",\"@I6@\",\"@I781@\"]},{\"id\":61,\"personId\":\"@I804@\",\"firstName\":\"Karl Joel Tryggve\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":35,\"personId\":\"@I253@\",\"firstName\":\"Magda Lillian\",\"lastName\":\"J\u00E4derbrink\",\"childConnections\":[]},{\"id\":42,\"personId\":\"@I516@\",\"firstName\":\"\",\"lastName\":\"Thunborg\",\"childConnections\":[]},{\"id\":9,\"personId\":\"@I466@\",\"firstName\":\"Ida Kristina\",\"lastName\":\"Persson\",\"childConnections\":[]},{\"id\":69,\"personId\":\"@I700@\",\"firstName\":\"Johan Peter\",\"lastName\":\"Danielsson\",\"childConnections\":[\"@I750@\",\"@I399@\",\"@I241@\",\"@I561@\",\"@I164@\",\"@I902@\",\"@I428@\",\"@I490@\",\"@I43@\"]},{\"id\":45,\"personId\":\"@I674@\",\"firstName\":\"Lovisa\",\"lastName\":\"Nilsdotter\",\"childConnections\":[\"@I750@\",\"@I399@\",\"@I241@\",\"@I561@\",\"@I164@\",\"@I902@\",\"@I428@\",\"@I490@\",\"@I43@\"]},{\"id\":18,\"personId\":\"@I2405@\",\"firstName\":\"Karl Einar\",\"lastName\":\"Ekstr\u00F6m\",\"childConnections\":[]},{\"id\":24,\"personId\":\"@I1473@\",\"firstName\":\"Ingeborg\",\"lastName\":\"Nilsson\",\"childConnections\":[]},{\"id\":12,\"personId\":\"@I656@\",\"firstName\":\"Allan Georg Sigfrid\",\"lastName\":\"B\u00E4ckstr\u00F6m\",\"childConnections\":[]},{\"id\":72,\"personId\":\"@I356@\",\"firstName\":\"Maria\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":4,\"personId\":\"@I325@\",\"firstName\":\"Daniel\",\"lastName\":\"Israelsson Wirs\u00E9n\",\"childConnections\":[\"@I424@\",\"@I91@\"]},{\"id\":1,\"personId\":\"@I523@\",\"firstName\":\"Stina Maria\",\"lastName\":\"Johansdotter\",\"childConnections\":[\"@I424@\",\"@I91@\"]},{\"id\":74,\"personId\":\"@I380@\",\"firstName\":\"Nils Ingvar\",\"lastName\":\"Wirs\u00E9n\",\"childConnections\":[]},{\"id\":75,\"personId\":\"@I780@\",\"firstName\":\"Irma Gun Marie\",\"lastName\":\"Ribman\",\"childConnections\":[]},{\"id\":80,\"personId\":\"@I902@\",\"firstName\":\"Klas Emil\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":29,\"personId\":\"@I378@\",\"firstName\":\"Johanna Gustafva\",\"lastName\":\"Larsdotter\",\"childConnections\":[]},{\"id\":76,\"personId\":\"@I290@\",\"firstName\":\"Sven Peter\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":64,\"personId\":\"@I43@\",\"firstName\":\"Alida Sofia\",\"lastName\":\"Johansdotter\",\"childConnections\":[]},{\"id\":83,\"personId\":\"@I429@\",\"firstName\":\"Johan August\",\"lastName\":\"Karlsson\",\"childConnections\":[]},{\"id\":26,\"personId\":\"@I851@\",\"firstName\":\"Alma Emilia\",\"lastName\":\"Persson\",\"childConnections\":[]},{\"id\":51,\"personId\":\"@I876@\",\"firstName\":\"Johan August\",\"lastName\":\"Johansson\",\"childConnections\":[]},{\"id\":52,\"personId\":\"@I561@\",\"firstName\":\"Emilia Gustafva\",\"lastName\":\"Johansdotter\",\"childConnections\":[]}]";	
	
	//private static String json= "@I325@:Daniel Israelsson Wirs\u00E9n[\"@I424@\",\"@I91@\",]\r\n@I523@:Stina Maria Johansdotter[\"@I424@\",\"@I91@\",]\r\n@I424@:Anna Gustava Danielsdotter[]\r\n@I91@:Hedvig Karolina Danielsdotter[]";
	public static void main(String[] args){
		getPersons();
	}
	
	
}
