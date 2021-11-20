package objects;

public class UtilisateurTabl {
	private int id;
	private String nom;
	private String Prenom;
	private String pseudo;
	private String email;
	private Integer telephone;
	private String ville;
	
	public UtilisateurTabl(int id, String nom, String prenom, String pseudo, String email, Integer telephone,
			String ville) {
		super();
		this.id = id;
		this.nom = nom;
		Prenom = prenom;
		this.pseudo = pseudo;
		this.email = email;
		this.telephone = telephone;
		this.ville = ville;
	}

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

	public String getPrenom() {
		return Prenom;
	}

	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getTelephone() {
		return telephone;
	}

	public void setTelephone(Integer telephone) {
		this.telephone = telephone;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}
	
	
}
