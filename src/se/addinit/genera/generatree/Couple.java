package se.addinit.genera.generatree;

import com.yworks.yfiles.graph.INode;

public class Couple {
    private Person person1;
    private Person person2;
    private INode familyNode;
	public INode getFamilyNode() {
		return familyNode;
	}
	public void setFamilyNode(INode familyNode) {
		this.familyNode = familyNode;
	}
	public Person getPerson1() {
		return person1;
	}
	public void setPerson1(Person person1) {
		this.person1 = person1;
	}
	public Person getPerson2() {
		return person2;
	}
	public void setPerson2(Person person2) {
		this.person2 = person2;
	}

}
