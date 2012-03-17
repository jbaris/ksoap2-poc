package jbaris.wordpress.com.common.entities;

import java.util.List;

/**
 * @author Juan Ignacio Barisich
 */
public class Car {

	private Long id;
	private String name;
	private Manufacturer manufacturer;
	private List<Owner> owners;

	public Car() {

	}

	public Car(Long id) {
		setId(id);
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) {
			return true;
		}
		if (!(o instanceof Car)) {
			return false;
		} else {
			return ((Car) o).getId().equals(id);
		}
	}

	public Long getId() {
		return id;
	}

	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public String getName() {
		return name;
	}

	public List<Owner> getOwners() {
		return owners;
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwners(List<Owner> owners) {
		this.owners = owners;
	}

	@Override
	public String toString() {
		return String.format(
				"Car[id=%d, name=%s, manufacturer=%s, owners={%s}]", id, name,
				manufacturer, owners);
	}

}
