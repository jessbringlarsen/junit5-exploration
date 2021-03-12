package dk.bringlarsen.junit5.explration.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Tool {
	
	@Id 
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;

	private String name;

	public Tool setName(String name) {
		this.name = name;
		return this;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
