package se.addinit.genera.generatree;

import java.util.ArrayList;
import java.util.List;

import com.yworks.yfiles.graph.INode;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Person {
	
	public String id;
	public List<String> children=new ArrayList<String>();
	public List<String> partners=new ArrayList<String>();
	public INode node;
	public Person father=null;
	public Person mother=null;
	public List<Source> sources=new ArrayList<Source>();
	
	  private StringProperty firstName = new SimpleStringProperty(this, "firstName", "");
	  public final StringProperty firstNameProperty(){return firstName;}
	  public final String getFirstName() {return firstName.get(); }
	  public final void setFirstName( String value ) { firstName.set(value); }
	  
	  private StringProperty lastName = new SimpleStringProperty(this, "lastName", "");
	  public final StringProperty lastNameProperty(){return lastName;}
	  public final String getLastName() {return lastName.get(); }
	  public final void setLastName( String value ) { lastName.set(value); }
	  
	  private StringProperty sex = new SimpleStringProperty(this, "sex", "");
	  public final StringProperty sexProperty(){return sex;}
	  public final String getSex() {return sex.get(); }
	  public final void setSex( String value ) { sex.set(value); }
	  
	  private StringProperty birthDate = new SimpleStringProperty(this, "birthDate", "");
	  public final StringProperty birthDateProperty(){return birthDate;}
	  public final String getBirthDate() {return birthDate.get(); }
	  public final void setBirthDate( String value ) { birthDate.set(value); }
	  
	  private StringProperty deathDate = new SimpleStringProperty(this, "deathDate", "");
	  public final StringProperty deathDateProperty(){return deathDate;}
	  public final String getDeathDate() {return deathDate.get(); }
	  public final void setDeathDate( String value ) { deathDate.set(value); }
	  
	  private StringProperty birthPlace = new SimpleStringProperty(this, "birthPlace", "");
	  public final StringProperty birthPlaceProperty(){return birthPlace;}
	  public final String getBirthPlace() {return birthPlace.get(); }
	  public final void setBirthPlace( String value ) { birthPlace.set(value); }
	  
	  private StringProperty deathPlace = new SimpleStringProperty(this, "deathPlace", "");
	  public final StringProperty deathPlaceProperty(){return deathPlace;}
	  public final String getDeathPlace() {return deathPlace.get(); }
	  public final void setDeathPlace( String value ) { deathPlace.set(value); }
	
	  private StringProperty image = new SimpleStringProperty(this, "image", "");
	  public final StringProperty imageProperty(){return image;}
	  public final String getImage() {return image.get(); }
	  public final void setImage( String value ) { image.set(value); }
	  
	public String toString(){
		String ret=id+":"+getSex()+" - "+getFirstName()+" "+getLastName()+"  "+getBirthDate()+" - "+getDeathDate()+"[";
		for(String child:children)
			ret+=child+",";
		ret+="]";
		return ret;
	}

}
