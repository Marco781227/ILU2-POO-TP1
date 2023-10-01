package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}

	private static class Marche {
		private Etal[] etals;
		private int nbEtal;

		private Marche(int nbEtal) {
			etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				etals[i] = new Etal();
			}
			this.nbEtal = nbEtal;
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			int indiceEtal = -1;
			for (int i = 0; i < nbEtal && indiceEtal == -1; i++) {
				if (!etals[i].isEtalOccupe()) {
					indiceEtal = i;
				}
			}
			return indiceEtal;
		}

		private int trouverNbEtalProduit(String produit) {
			int nbEtalProduitMax = 0;
			for (int i = 0; i < nbEtal; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					nbEtalProduitMax++;
				}
			}
			return nbEtalProduitMax;
		}

		private Etal[] trouverEtals(String produit) {
			int nbEtalProduitMax = trouverNbEtalProduit(produit);
			if (nbEtalProduitMax == 0) {
				return null;
			}
			Etal[] etalsProduit = new Etal[nbEtalProduitMax];
			int indiceEtalProduit = 0;
			for (int i = 0; i < nbEtal; i++) {
				if (etals[i].isEtalOccupe() && etals[i].contientProduit(produit)) {
					etalsProduit[indiceEtalProduit] = etals[i];
					indiceEtalProduit++;
				}
			}
			return etalsProduit;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalVendeur = null;
			for (int i=0;i<nbEtal && etalVendeur==null;i++) {
				if (etals[i].getVendeur()==gaulois) {
					etalVendeur=etals[i];
				}
			}
			return etalVendeur;
		}
		
		private String afficherMarche() {
			int nbEtalsLibres=0;
			StringBuilder chaine = new StringBuilder();
			for (int i=0;i<nbEtal;i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}
				else {
					nbEtalsLibres++;
				}
			}
			chaine.append("Il reste "+nbEtalsLibres+" étals non utilisés dans le marché");
			return chaine.toString();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les lÃ©gendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		int indiceEtal=marche.trouverEtalLibre();
		if (indiceEtal==-1) {
			chaine.append(vendeur.getNom()+" n'a pas trouvé d'endroit pour vendre ses "+produit+"\n");
		}else {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			indiceEtal++;
			chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" à l'étal n°"+indiceEtal+".\n");
		}
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder chaine = new StringBuilder();
		Etal[] etalsVendeurProduit = marche.trouverEtals(produit);
		if (etalsVendeurProduit==null) {
			chaine.append("Aucun vendeur ne propose de vendre des "+produit+".\n");
		}
		else{
			chaine.append("Les vendeurs qui proposent des "+produit+" sont : \n");
			for (int i=0;i<etalsVendeurProduit.length;i++) {
				chaine.append("- "+etalsVendeurProduit[i].getVendeur().getNom()+"\n");
			}
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		Etal etalVendeur = rechercherEtal(vendeur);
		return etalVendeur.libererEtal();
	}
	
	public String afficherMarche() {
		System.out.println("Le marché du village "+ "\""+nom+"\" possède plusieurs étals : \n");
		return marche.afficherMarche();
	}
}