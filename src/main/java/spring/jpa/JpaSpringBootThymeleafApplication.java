package spring.jpa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import spring.jpa.model.Categorie;
import spring.jpa.model.Client;
import spring.jpa.model.Facture;
import spring.jpa.model.Produit;
import spring.jpa.repository.CategorieRepository;
import spring.jpa.repository.ClientRepository;
import spring.jpa.repository.FactureRepository;
import spring.jpa.repository.ProduitRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@SpringBootApplication
public class JpaSpringBootThymeleafApplication {

    static ProduitRepository produitRepos;
    static CategorieRepository categorieRepos;
    static ClientRepository clientRepos;
    static FactureRepository factureRepos;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(JpaSpringBootThymeleafApplication.class, args);

        produitRepos = context.getBean(ProduitRepository.class);
        categorieRepos = context.getBean(CategorieRepository.class);
        clientRepos = context.getBean(ClientRepository.class);
        factureRepos = context.getBean(FactureRepository.class);

        // Créer deux catégories
        Categorie cat1 = new Categorie("AL", "Alimentaire");
        Categorie cat2 = new Categorie("PL", "Plastique");
        categorieRepos.save(cat1);
        categorieRepos.save(cat2);

        // Créer trois produits
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date1 = null;
        java.util.Date date2 = null;
        java.util.Date date3 = null;

        try {
            date1 = sdf.parse("2022-04-15");
            date2 = sdf.parse("2022-02-15");
            date3 = sdf.parse("2022-05-15");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Produit p1 = new Produit("Yahourt", 0.400, 20, date1, cat1);
        Produit p2 = new Produit("Chocolat", 2000.0, 5, date2, cat1);
        Produit p3 = new Produit("Panier", 1.200, 30, date3, cat2);
        produitRepos.save(p1);
        produitRepos.save(p2);
        produitRepos.save(p3);

        // Créer un client
        Client client = new Client("Sami", "Boukthir");
        clientRepos.save(client);

        // Créer une facture associée au client
        Facture facture = new Facture();
        factureRepos.save(facture);

        // Afficher tous les produits
        afficherTousLesProduits();
    }

    static void afficherTousLesProduits() {
        System.out.println("***************************************");
        System.out.println("Afficher tous les produits...");
        System.out.println("***************************************");
        List<Produit> lp = produitRepos.findAll();
        for (Produit p : lp) {
            System.out.println(p);
        }
        System.out.println("***************************************");
    }
}
