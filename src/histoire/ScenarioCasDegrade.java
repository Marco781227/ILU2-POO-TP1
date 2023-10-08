package histoire;
import personnages.*;
import villagegaulois.*;
import exceptions.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Gaulois vendeur = new Gaulois("vendeur",8);
		Gaulois acheteur = new Gaulois("acheteur",6);
		etal.libererEtal();
		
		etal.occuperEtal(vendeur, "Cerises", 9);
		
		etal.acheterProduit(10, null);
		
		try {
			etal.acheterProduit(-7, acheteur);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		etal.libererEtal();
		try {
			etal.acheterProduit(10, acheteur);
		} catch (IllegalStateException f) {
			f.printStackTrace();
		}
		
		Village village = new Village("VillageTest", 5, 2);
		try {
			village.afficherVillageois();
		}catch (VillageSansChefException e) {
			e.printStackTrace();
		}
		System.out.println("Fin du test"); 
	}

}
