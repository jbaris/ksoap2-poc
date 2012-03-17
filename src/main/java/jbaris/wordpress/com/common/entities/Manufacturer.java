package jbaris.wordpress.com.common.entities;

/**
 * @author Juan Ignacio Barisich
 */
public class Manufacturer {

	private Long id;
	private String name;
	private String country;

	public String getCountry() {
		return country;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("Manufacturer[id=%d, name=%s, country=%s]", id,
				name, country);
	}

}
