package objects;

public class Table {
	
	private int id;
	private String nomTbale;
	private int eta; 
	private int couverts;
	private String nameMachine;
	
	
	
	public Table(int id, String nomTbale, int eta, int couverts, String nameMachine) {
		super();
		this.id = id;
		this.nomTbale = nomTbale;
		this.eta = eta;
		this.couverts = couverts;
		this.nameMachine = nameMachine;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNomTbale() {
		return nomTbale;
	}
	public void setNomTbale(String nomTbale) {
		this.nomTbale = nomTbale;
	}
	public int getEta() {
		return eta;
	}
	public void setEta(int eta) {
		this.eta = eta;
	}
	public int getCouverts() {
		return couverts;
	}
	public void setCouverts(int couverts) {
		this.couverts = couverts;
	}
	public String getNameMachine() {
		return nameMachine;
	}
	public void setNameMachine(String nameMachine) {
		this.nameMachine = nameMachine;
	}
	
	
	

}
