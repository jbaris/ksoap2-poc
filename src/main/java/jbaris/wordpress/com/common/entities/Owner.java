package jbaris.wordpress.com.common.entities;

/**
 * @author Juan Ignacio Barisich
 */
public class Owner {

	private Long id;
	private String name;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("Owner[id=%d, name=%s]", id, name);
	}

}
