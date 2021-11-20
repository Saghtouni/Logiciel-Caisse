package objects;

public class Client {
	private int id;
	private String nom;
	private String adresse ;
	private long telephone;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public Long getTelephone() {
		return telephone;
	}
	public void setTelephone(Long telephone) {
		this.telephone = telephone;
	}
	public Client(String nom, long telephone, String adresse) {
		super();
		this.nom = nom;
		this.adresse = adresse;
		this.telephone = telephone;
	}
	public Client(int id, String nom, String adresse, long telephone) {
		super();
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.telephone = telephone;
	}
	public Client() {
		// TODO Auto-generated constructor stub
	}
	
	

}
