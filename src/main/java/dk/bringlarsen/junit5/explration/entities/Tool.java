package dk.bringlarsen.junit5.explration.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tool {
	
	@Id
	private String id;

	private String name;

	public String getId() {
		return id;
	}

	public Tool setId(String id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public Tool setName(String name) {
		this.name = name;
		return this;
	}
}
