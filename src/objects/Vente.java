package objects;

import java.sql.Date;

public class Vente {
	private Integer id;
	private String nomProduits;
	private Double prixVente;
	private Integer qunatite;
	private Double TVA;
	private Double TVAC;
	private Integer numeroVente;
	private Integer table;
	
	public Vente(Integer id, String nomProduits, Double prixVente, Integer qunatite, Double tVA,Integer numeroVente, Integer table) {
		super();
		this.id = id;
		this.nomProduits = nomProduits;
		this.prixVente = prixVente;
		this.qunatite = qunatite;
		TVA = tVA;
		//TVAC = tVAC;
		this.numeroVente = numeroVente;
		this.table = table;
	}
	
	public Vente(int i, String nom, Double prixvente2, int j, Double tva2, double d, int k, Integer table) {
		super();
		this.id = i;
		this.nomProduits = nom;
		this.prixVente = prixvente2;
		this.qunatite = j;
		TVA = tva2;
		TVAC = d;
		this.numeroVente = k;
		this.table = table;
		
	}
	public Vente() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomProduits() {
		return nomProduits;
	}

	public void setNomProduits(String nomProduits) {
		this.nomProduits = nomProduits;
	}

	public Double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(Double prixVente) {
		this.prixVente = prixVente;
	}

	public Integer getQunatite() {
		return qunatite;
	}

	public void setQunatite(Integer qunatite) {
		this.qunatite = qunatite;
	}

	public Double getTVA() {
		return TVA;
	}

	public void setTVA(Double tVA) {
		TVA = tVA;
	}

	public Double getTVAC() {
		return TVAC;
	}

	public void setTVAC(Double tVAC) {
		TVAC = tVAC;
	}

	public Integer getNumeroVente() {
		return numeroVente;
	}

	public void setNumeroVente(Integer numeroVente) {
		this.numeroVente = numeroVente;
	}

	public Integer getTable() {
		return table;
	}

	public void setTable(Integer table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return "Vente [id=" + id + ", nomProduits=" + nomProduits + ", prixVente=" + prixVente + ", qunatite="
				+ qunatite + ", TVA=" + TVA + ", TVAC=" + TVAC + ", numeroVente=" + numeroVente + ", table=" + table
				+ ", getId()=" + getId() + ", getNomProduits()=" + getNomProduits() + ", getPrixVente()="
				+ getPrixVente() + ", getQunatite()=" + getQunatite() + ", getTVA()=" + getTVA() + ", getTVAC()="
				+ getTVAC() + ", getNumeroVente()=" + getNumeroVente() + ", getTable()=" + getTable() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

	

	
}
	