package objects;

public class Reglage {

	
	
	
	private int id;
	private String tickets;
	private String imptickets;
	private String factures;
	private String impfactures;
	private String historiques;
	private String imphistoriques;
	private String image;
	private String devise;
	private int droitsuppression;
	private int nbrTable;
	private int id_utilisateur;
	private String nameMachine;
	private String adresseMachine;
	
	public Reglage(int id, String tickets, String imptickets, String factures, String impfactures, String historiques,
			String imphistoriques, String image, String devise, int droitsuppression, int nbrTable, int id_utilisateur,
			String nameMachine, String adresseMachine) {
		super();
		this.id = id;
		this.tickets = tickets;
		this.imptickets = imptickets;
		this.factures = factures;
		this.impfactures = impfactures;
		this.historiques = historiques;
		this.imphistoriques = imphistoriques;
		this.image = image;
		this.devise = devise;
		this.droitsuppression = droitsuppression;
		this.nbrTable = nbrTable;
		this.id_utilisateur = id_utilisateur;
		this.nameMachine = nameMachine;
		this.adresseMachine = adresseMachine;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTickets() {
		return tickets;
	}

	public void setTickets(String tickets) {
		this.tickets = tickets;
	}

	public String getImptickets() {
		return imptickets;
	}

	public void setImptickets(String imptickets) {
		this.imptickets = imptickets;
	}

	public String getFactures() {
		return factures;
	}

	public void setFactures(String factures) {
		this.factures = factures;
	}

	public String getImpfactures() {
		return impfactures;
	}

	public void setImpfactures(String impfactures) {
		this.impfactures = impfactures;
	}

	public String getHistoriques() {
		return historiques;
	}

	public void setHistoriques(String historiques) {
		this.historiques = historiques;
	}

	public String getImphistoriques() {
		return imphistoriques;
	}

	public void setImphistoriques(String imphistoriques) {
		this.imphistoriques = imphistoriques;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public int getDroitsuppression() {
		return droitsuppression;
	}

	public void setDroitsuppression(int droitsuppression) {
		this.droitsuppression = droitsuppression;
	}

	public int getNbrTable() {
		return nbrTable;
	}

	public void setNbrTable(int nbrTable) {
		this.nbrTable = nbrTable;
	}

	public int getId_utilisateur() {
		return id_utilisateur;
	}

	public void setId_utilisateur(int id_utilisateur) {
		this.id_utilisateur = id_utilisateur;
	}

	public String getNameMachine() {
		return nameMachine;
	}

	public void setNameMachine(String nameMachine) {
		this.nameMachine = nameMachine;
	}

	public String getAdresseMachine() {
		return adresseMachine;
	}

	public void setAdresseMachine(String adresseMachine) {
		this.adresseMachine = adresseMachine;
	}
	
	
	
	
	
}
