package objects;

public class CheckMenu {
	
	private int id;
	private int checFacture;
	private int checDevis;
	private int checProduits;
	private int checAnalyse;
	private int checUtilisateur;
	private int checCaisse;
	private int commande;
	private int salaire;
	private String remarque;
	
	public CheckMenu(int id, int checFacture, int checDevis, int checProduits, int checAnalyse, int checUtilisateur,
			int checCaisse, int commande, int salaire, String remarque) {
		super();
		this.id = id;
		this.checFacture = checFacture;
		this.checDevis = checDevis;
		this.checProduits = checProduits;
		this.checAnalyse = checAnalyse;
		this.checUtilisateur = checUtilisateur;
		this.checCaisse = checCaisse;
		this.commande = commande;
		this.salaire = salaire;
		this.remarque = remarque;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getChecFacture() {
		return checFacture;
	}

	public void setChecFacture(int checFacture) {
		this.checFacture = checFacture;
	}

	public int getChecDevis() {
		return checDevis;
	}

	public void setChecDevis(int checDevis) {
		this.checDevis = checDevis;
	}

	public int getChecProduits() {
		return checProduits;
	}

	public void setChecProduits(int checProduits) {
		this.checProduits = checProduits;
	}

	public int getChecAnalyse() {
		return checAnalyse;
	}

	public void setChecAnalyse(int checAnalyse) {
		this.checAnalyse = checAnalyse;
	}

	public int getChecUtilisateur() {
		return checUtilisateur;
	}

	public void setChecUtilisateur(int checUtilisateur) {
		this.checUtilisateur = checUtilisateur;
	}

	public int getChecCaisse() {
		return checCaisse;
	}

	public void setChecCaisse(int checCaisse) {
		this.checCaisse = checCaisse;
	}

	public int getCommande() {
		return commande;
	}

	public void setCommande(int commande) {
		this.commande = commande;
	}

	public int getSalaire() {
		return salaire;
	}

	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}

	public String getRemarque() {
		return remarque;
	}

	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	
	
	


}
