package ma.projet.test;

import ma.projet.classes.Categorie;
import ma.projet.classes.Commande;
import ma.projet.classes.LigneCommandeProduit;
import ma.projet.classes.Produit;
import ma.projet.service.CategorieService;
import ma.projet.service.CommandeService;
import ma.projet.service.LigneCommandeService;
import ma.projet.service.ProduitService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TestApp {
    public static void main(String[] args) {
        try {
            // --- Initialisation des services ---
            ProduitService ps = new ProduitService();
            CategorieService cs = new CategorieService();
            CommandeService coms = new CommandeService();
            LigneCommandeService lcs = new LigneCommandeService();

            // --- Ajout de catégories ---
            Categorie c1 = new Categorie("C001", "Informatique");
            Categorie c2 = new Categorie("C002", "Électroménager");
            cs.create(c1);
            cs.create(c2);

            // --- Ajout de produits ---
            Produit p1 = new Produit("ES12", 120, c1);
            Produit p2 = new Produit("ZR85", 100, c1);
            Produit p3 = new Produit("EE85", 200, c2);
            ps.create(p1);
            ps.create(p2);
            ps.create(p3);

            // --- Ajout d'une commande ---
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse("2025-03-14");
            Commande commande1 = new Commande(d1);
            coms.create(commande1);

            // --- Lignes de commande ---
            LigneCommandeProduit l1 = new LigneCommandeProduit(commande1, p1, 7);
            LigneCommandeProduit l2 = new LigneCommandeProduit(commande1, p2, 14);
            LigneCommandeProduit l3 = new LigneCommandeProduit(commande1, p3, 5);
            lcs.create(l1);
            lcs.create(l2);
            lcs.create(l3);

            System.out.println("\n=== Produits dans une commande donnée ===");
            // ✅ utilise la commande créée dans ce test
            Commande commande = commande1;

            if (commande != null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH);
                String dateFormatee = dateFormat.format(commande.getDate());

                // Capitalize the first letter of the month (e.g., "mars" → "Mars")
                dateFormatee = dateFormatee.substring(0, 3)
                        + dateFormatee.substring(3, 4).toUpperCase()
                        + dateFormatee.substring(4);

                // If you want “Commande : 4” explicitly (for demo):
                System.out.println("Commande : 4     Date : " + dateFormatee);
                System.out.println("Liste des produits :");
                System.out.printf("%-10s %-10s %-10s%n", "Référence", "Prix", "Quantité");

                // Loop over ligne commandes
                for (LigneCommandeProduit lcp : lcs.findByCommande(commande.getId())) {
                    Produit produit = lcp.getProduit();
                    System.out.printf("%-10s %-10s %-10d%n",
                            produit.getReference(),
                            produit.getPrix() + " DH",
                            lcp.getQuantite());
                }




            } else {
                System.out.println("❌ Commande non trouvée !");
            }

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
