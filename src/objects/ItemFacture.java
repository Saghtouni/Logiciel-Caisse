package objects;

public class ItemFacture {
	
	
	private int id;
	private  String nom;
	private  Integer quantite;
	private Double prixunitaire;
	private  Double prixtotal;
	private Double tva;
	private String unit;
	private int numeroFacture;
	
	
	public ItemFacture(String nom, Integer quantite, Double prixunitaire, Double prixtotal, Double tva, String unit) {
		super();
		this.nom = nom;
		this.quantite = quantite;
		this.prixunitaire = prixunitaire;
		this.prixtotal = prixtotal;
		this.tva = tva;
		this.unit = unit;
	}


	public ItemFacture() {
		
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


	public Integer getQuantite() {
		return quantite;
	}


	public void setQuantite(Integer quantite) {
		this.quantite = quantite;
	}


	public Double getPrixunitaire() {
		return prixunitaire;
	}


	public void setPrixunitaire(Double prixunitaire) {
		this.prixunitaire = prixunitaire;
	}


	public Double getPrixtotal() {
		return prixtotal;
	}


	public void setPrixtotal(Double prixtotal) {
		this.prixtotal = prixtotal;
	}


	public Double getTva() {
		return tva;
	}


	public void setTva(Double tva) {
		this.tva = tva;
	}


	public String getUnit() {
		return unit;
	}


	public void setUnit(String unit) {
		this.unit = unit;
	}


	public int getNumeroFacture() {
		return numeroFacture;
	}


	public void setNumeroFacture(int numeroFacture) {
		this.numeroFacture = numeroFacture;
	}
	


}
