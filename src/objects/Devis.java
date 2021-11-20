package objects;

public class Devis {
	private int id;
	private String date;
	private Double totalnet;
	private Double tva;
	private Double totalbrut;
	private	String remarque;
	private String unit;
	private int client;
	
	
	
	public Devis(String date, Double totalnet, Double tva, Double totalbrut, String remarque, String unit) {
		super();
		this.date = date;
		this.totalnet = totalnet;
		this.tva = tva;
		this.totalbrut = totalbrut;
		this.remarque = remarque;
		this.unit = unit;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}



	public Double getTotalnet() {
		return totalnet;
	}



	public void setTotalnet(Double totalnet) {
		this.totalnet = totalnet;
	}



	public Double getTva() {
		return tva;
	}



	public void setTva(Double tva) {
		this.tva = tva;
	}



	public Double getTotalbrut() {
		return totalbrut;
	}



	public void setTotalbrut(Double totalbrut) {
		this.totalbrut = totalbrut;
	}



	public String getRemarque() {
		return remarque;
	}



	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}



	public String getUnit() {
		return unit;
	}



	public void setUnit(String unit) {
		this.unit = unit;
	}



	public int getClient() {
		return client;
	}



	public void setClient(int client) {
		this.client = client;
	}
	

	

}
