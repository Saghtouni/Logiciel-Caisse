package objects;

public class Salle {
	
	private int id ;
	private String name;
	private String nameMachine;
	
	public Salle(int id, String name, String nameMachine) {
		super();
		this.id = id;
		this.name = name;
		this.nameMachine = nameMachine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameMachine() {
		return nameMachine;
	}

	public void setNameMachine(String nameMachine) {
		this.nameMachine = nameMachine;
	}
	
	

}
