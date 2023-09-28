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
				if (etals[i].contientProduit(produit)) {
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
				if (etals[i].contientProduit(produit)) {
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
		
		private void afficherMarche() {
			int nbEtalsLibres=0;
			for (int i=0;i<nbEtal;i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				}
				else {
					nbEtalsLibres++;
				}
			}
			System.out.println("Il reste "+nbEtalsLibres+"�tals non utilis�s dans le march�");
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
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}