package objects;

import java.sql.Date;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;

//Object Utilisateurs

public class Utilisateurs {
	
     	private int id ;
	    private String nom; 
	    private String prenom;
	    private String pseudo;
	    private String email;
	    private String mot_passe;
	    private int telephone; 
	    private String date_naissance; 
	    private String pays; 
	    private String adresse; 
	    private int code_postal; 
	    private String ville;
	    private boolean selected = true;
	    private DatePicker dateNaissance;
	    
		public Utilisateurs( String nom, String prenom, String pseudo, String email, String mot_passe,
				int telephone, String date_naissance, String pays, String adresse, int code_postal, String ville) {
			super();

			this.nom = nom;
			this.prenom = prenom;
			this.pseudo = pseudo;
			this.email = email;
			this.mot_passe = mot_passe;
			this.telephone = telephone;
			this.date_naissance = date_naissance;
			this.pays = pays;
			this.adresse = adresse;
			this.code_postal = code_postal;
			this.ville = ville;
			
		}
		public Utilisateurs( int id,String nom, String prenom, String pseudo, String email, String mot_passe,
				int telephone, String date_naissance, String pays, String adresse, int code_postal, String ville) {
			super();
			this.id=id;
			this.nom = nom;
			this.prenom = prenom;
			this.pseudo = pseudo;
			this.email = email;
			this.mot_passe = mot_passe;
			this.telephone = telephone;
			this.date_naissance = date_naissance;
			this.pays = pays;
			this.adresse = adresse;
			this.code_postal = code_postal;
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
			return prenom;
		}

		public void setPrenom(String prenom) {
			this.prenom = prenom;
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

		public String getMot_passe() {
			return mot_passe;
		}

		public void setMot_passe(String mot_passe) {
			this.mot_passe = mot_passe;
		}

		public int getTelephone() {
			return telephone;
		}

		public void setTelephone(int telephone) {
			this.telephone = telephone;
		}

		public String getDate_naissance() {
			return date_naissance;
		}

		public void setDate_naissance(String date_naissance) {
			this.date_naissance = date_naissance;
		}

		public String getPays() {
			return pays;
		}

		public void setPays(String pays) {
			this.pays = pays;
		}

		public String getAdresse() {
			return adresse;
		}

		public void setAdresse(String adresse) {
			this.adresse = adresse;
		}

		public int getCode_postal() {
			return code_postal;
		}

		public void setCode_postal(int code_postal) {
			this.code_postal = code_postal;
		}

		public String getVille() {
			return ville;
		}

		public void setVille(String ville) {
			this.ville = ville;
		}
	  
		public boolean isSelected() {
			return selected;
		}

		public void setSelected(boolean selected) {
			this.selected = selected;
		}
		
		public DatePicker getDateNaissance() {
			return dateNaissance;
		}

		public void setDateNaissance(DatePicker dateNaissance) {
			this.dateNaissance = dateNaissance;
		}
	
	
}