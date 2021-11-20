package objects;

public class produitsTable {
	
 int id;
 String produit;
 double prix;
 int quantite;
 Double tva;
 int table;
 
public produitsTable(int id, String produit, double prix, int quantite, Double tva, int table) {
	super();
	this.id = id;
	this.produit = produit;
	this.prix = prix;
	this.quantite = quantite;
	this.tva = tva;
	this.table = table;
}

public produitsTable( String produit, double prix, int quantite, Double tva, int table) {
	super();
	this.produit = produit;
	this.prix = prix;
	this.quantite = quantite;
	this.tva = tva;
	this.table = table;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getProduit() {
	return produit;
}

public void setProduit(String produit) {
	this.produit = produit;
}

public double getPrix() {
	return prix;
}

public void setPrix(double prix) {
	this.prix = prix;
}

public int getQuantite() {
	return quantite;
}

public void setQuantite(int quantite) {
	this.quantite = quantite;
}

public Double getTva() {
	return tva;
}

public void setTva(Double tva) {
	this.tva = tva;
}

public int getTable() {
	return table;
}

public void setTable(int table) {
	this.table = table;
}
 
 
 
}
