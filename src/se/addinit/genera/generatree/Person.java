package se.addinit.genera.generatree;

import java.util.ArrayList;
import java.util.List;

import com.yworks.yfiles.graph.INode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Person {
	
	public String id;
	//public String firstName="Unknown";
	public String lastName="Unknown";
	public List<String> children=new ArrayList<String>();
	public INode node;
	public Person father=null;
	public Person mother=null;
	public String sex="MALE"; //or Female
	
	  private StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
	  public final StringProperty firstNameProperty(){return firstName;}
	  public final String getFirstName() {return firstName.get(); }
	  public final void setFirstName( String value ) { firstName.set(value); }
	
	public String toString(){
		String ret=id+":"+sex+" - "+getFirstName()+" "+lastName+"[";
		for(String child:children)
			ret+=child+",";
		ret+="]";
		return ret;
	}

}
