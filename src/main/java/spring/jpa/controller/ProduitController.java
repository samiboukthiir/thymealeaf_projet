package spring.jpa.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import spring.jpa.model.Client;
import spring.jpa.model.Devise;
import spring.jpa.model.Facture;
import spring.jpa.model.Produit;
import spring.jpa.repository.ClientRepository;
import spring.jpa.repository.DeviseRepository;
import spring.jpa.repository.FactureRepository;
import spring.jpa.repository.ProduitRepository;

@Controller // pour déclarer un contrôleur
@RequestMapping (value = "/gestion") // nom logique dans l'URL pour accéder au contrôleur
public class ProduitController
{
	
@Autowired // pour l'injection de dépendances
private ProduitRepository produitRepos;

@Autowired
private ClientRepository clientRepos;

@Autowired
private DeviseRepository deviseRepos;

@Autowired
private FactureRepository factureRepos;






// controlleur pour les produits

//nom logique pour accéder à l'action "index" ou méthode "index
@RequestMapping (value = "/index") // page home produit
public String index (Model model,
//paramètre pour le numéro de la page (0 par défaut)
@RequestParam (name="page" , defaultValue ="0") int p,
//paramètre "motCle"
@RequestParam (name="motCle", defaultValue="") String mc) 
{
	
//récupérer la page numero "p" (de taille 6)
Page <Produit> pg = 
produitRepos.findByDesignationLike("%"+mc+"%", PageRequest.of(p, 6));

//nombre total des pages
int nbrePages =pg.getTotalPages();

//créer un tableau d'entier qui contient les numéros des pages
int [] pages = new int[nbrePages];
for(int i= 0 ; i< nbrePages; i++)
{
pages[i]=i;
}

//placer le tableau dans le "Model"
model.addAttribute("pages", pages);

//placer la page des produits dans le "Model" comme un attribut"
model.addAttribute("pageProduits", pg); 

//retourner le numéro de la page courante
model.addAttribute("pageCourante",p);

//retourner la valeur du mot clé
model.addAttribute("motCle", mc);

//retourner le nom de la vue WEB à afficher
return "produits"; 
}




@RequestMapping(value="/form",method=RequestMethod.GET) // pour la formulaire  de produit
public String formProduit (Model model)
{
//placer un objet de type "Produit" dans le modèle
model.addAttribute("produit", new Produit());
//retourner le nom de la vue WEB à afficher (le formulaire)
return "formProduit";   // retourne la page html
}

@RequestMapping(value="/save",method=RequestMethod.POST)
public String save (Model model, @Valid Produit produit , BindingResult bindingResult)
{
if (bindingResult.hasErrors())
// en cas d'erreurs de validation
return "formProduit";
//sinon
//enregistrer le produit dans la BD 
produitRepos.save(produit);
//Afficher une page pour confirmer l'enregistrement
return "confirmation";
}

@RequestMapping(value="/delete",method=RequestMethod.GET) // suppression du produit
public String delete (Long id, int page, String motCle)
 {
produitRepos.deleteById(id);
 return "redirect:index?page="+page+"&motCle="+motCle;
 }

@RequestMapping(value="/edit",method=RequestMethod.GET) // modification du produit
public String edit (Model model, 
@RequestParam (name="id")Long id)

{
//récupérer l'objet ayant l'id spécifié
Produit p =(Produit) 
produitRepos.findById(id).orElse(null);
// placer le produit trouvé dans le modèle
model.addAttribute("produit", p);
// rediriger l'affichage vers la vue "editProduit"
return "editProduit";
}

@RequestMapping(value="/update",method=RequestMethod.POST)
public String update (Model model, @Valid Produit produit , 
BindingResult bindingResult) {
if (bindingResult.hasErrors())
//en cas d'erreurs de validation
return "editProduit";
//enregistrer le produit dans la BD 
produitRepos.save(produit);
//Afficher une page pour confirmer l'enregistrement
return "confirmation"; // pour la confirmation de modification
}






// controlleurs pour les clients

@RequestMapping(value = "/clients")
public String gestionClients(Model model,
		@RequestParam (name="page", defaultValue ="0") int c,
		// paramètre "motCle"
		@RequestParam (name="motCle", defaultValue="") String mc) {
	Page <Client> pg = clientRepos.findByNomLike("%"+mc+"%", PageRequest.of(c, 6));
	
	int nbrePages =pg.getTotalPages();
	// créer un tableau d'entier pour contenir les numéros des pages
	int [] pages = new int[nbrePages];
	for(int i= 0 ; i< nbrePages; i++)
	{
	pages[i]=i;
	}
	// placer le tableau dans le "Model"
	model.addAttribute("pages", pages);
	model.addAttribute("pageCourante",c);
	model.addAttribute("motCle", mc);

    model.addAttribute("pageClients", pg);
    return "gestionClients";
}


@RequestMapping(value="/form1",method=RequestMethod.GET)
public String formClient (Model model)
{
//placer un objet de type "Produit" dans le modèle
model.addAttribute("client", new Client());
//retourner le nom de la vue WEB à afficher (le formulaire)
return "formClient";
}

@RequestMapping(value="/saveClient",method=RequestMethod.POST)
public String save (Model model, @Valid Client client , BindingResult bindingResult)
{
if (bindingResult.hasErrors())
// en cas d'erreurs de validation
return "formClient";
//sinon
//enregistrer le client dans la BD 
clientRepos.save(client);
//Afficher une page pour confirmer l'enregistrement
return "confirmationClient";
}

@RequestMapping(value = "/delete1", method = RequestMethod.GET)
public String delete1(Long id, int page, String motCle) {
    clientRepos.deleteById(id);
    return "redirect:clients?page=" + page + "&motCle=" + motCle;
}


@RequestMapping(value="/edit1",method=RequestMethod.GET)
public String edit1 (Model model, 
@RequestParam (name="id")Long id)

{
//récupérer l'objet ayant l'id spécifié
Client c =(Client) 
clientRepos.findById(id).orElse(null);
// placer le produit trouvé dans le modèle
model.addAttribute("client", c);
// rediriger l'affichage vers la vue "editProduit"
return "editClient";
}

@RequestMapping(value="/update1",method=RequestMethod.POST)
public String update1 (Model model, @Valid Client client , 
BindingResult bindingResult) {
if (bindingResult.hasErrors())
//en cas d'erreurs de validation
return "editClient";
//enregistrer le produit dans la BD 
clientRepos.save(client);
//Afficher une page pour confirmer l'enregistrement
return "confirmationClient"; 
}










// pour les devises

@RequestMapping(value = "/devises")
public String gestionDevises(Model model,
		@RequestParam (name="page", defaultValue ="0") int d,
		// paramètre "motCle"
		@RequestParam (name="motCle", defaultValue="") String mc) {
	Page <Devise> pg = deviseRepos.findByNomLike("%"+mc+"%", PageRequest.of(d, 6));
	
	int nbrePages =pg.getTotalPages();
	// créer un tableau d'entier pour contenir les numéros des pages
	int [] pages = new int[nbrePages];
	for(int i= 0 ; i< nbrePages; i++)
	{
	pages[i]=i;
	}
	// placer le tableau dans le "Model"
	model.addAttribute("pages", pages);
	model.addAttribute("pageCourante",d);
	model.addAttribute("motCle", mc);

    model.addAttribute("pageDevises", pg);
    return "gestionDevises";
}


@RequestMapping(value="/form2",method=RequestMethod.GET)
public String formDevise (Model model)
{
//placer un objet de type "Produit" dans le modèle
model.addAttribute("devise", new Client());
//retourner le nom de la vue WEB à afficher (le formulaire)
return "formDevise";
}

@RequestMapping(value="/saveDevise",method=RequestMethod.POST)
public String save (Model model, @Valid Devise devise , BindingResult bindingResult)
{
if (bindingResult.hasErrors())
// en cas d'erreurs de validation
return "formDevise";
//sinon
//enregistrer le client dans la BD 
deviseRepos.save(devise);
//Afficher une page pour confirmer l'enregistrement
return "confirmationDevise";
}

@RequestMapping(value = "/delete2", method = RequestMethod.GET)
public String delete2(Long id, int page, String motCle) {
    deviseRepos.deleteById(id);
    return "redirect:devises?page=" + page + "&motCle=" + motCle;
}


@RequestMapping(value="/edit2",method=RequestMethod.GET)
public String edit2 (Model model, 
@RequestParam (name="id")Long id)

{
//récupérer l'objet ayant l'id spécifié
Devise d =(Devise) 
deviseRepos.findById(id).orElse(null);
// placer le produit trouvé dans le modèle
model.addAttribute("devise", d);
// rediriger l'affichage vers la vue "editProduit"
return "editDevise";
}

@RequestMapping(value="/update2",method=RequestMethod.POST)
public String update2 (Model model, @Valid Devise devise , 
BindingResult bindingResult) {
if (bindingResult.hasErrors())
//en cas d'erreurs de validation
return "editDevise";
//enregistrer le produit dans la BD 
deviseRepos.save(devise);
//Afficher une page pour confirmer l'enregistrement
return "confirmationDevise"; 
}










// finalement pour les factures

@RequestMapping(value = "/factures")
public String gestionFactures(Model model,
		@RequestParam (name="page", defaultValue ="0") int f,
		// paramètre "motCle"
		@RequestParam (name="motCle", defaultValue="") Long mc) {
	Page <Facture> pg = factureRepos.findByClientIdLike(mc, PageRequest.of(f, 6));
	
	int nbrePages =pg.getTotalPages();
	// créer un tableau d'entier pour contenir les numéros des pages
	int [] pages = new int[nbrePages];
	for(int i= 0 ; i< nbrePages; i++)
	{
	pages[i]=i;
	}
	// placer le tableau dans le "Model"
	model.addAttribute("pages", pages);
	model.addAttribute("pageCourante",f);
	model.addAttribute("motCle", mc);

    model.addAttribute("pageFactures", pg);
    return "gestionFactures";
}


@RequestMapping(value="/form3",method=RequestMethod.GET)
public String formFacture (Model model)
{
//placer un objet de type "Produit" dans le modèle
model.addAttribute("facture", new Facture());
//retourner le nom de la vue WEB à afficher (le formulaire)
return "formFacture";
}

@RequestMapping(value="/saveFacture",method=RequestMethod.POST)
public String save (Model model, @Valid Facture facture , BindingResult bindingResult)
{
if (bindingResult.hasErrors())
// en cas d'erreurs de validation
return "formFacture";
//sinon
//enregistrer le client dans la BD 
factureRepos.save(facture);
//Afficher une page pour confirmer l'enregistrement
return "confirmationFacture";
}

@RequestMapping(value = "/delete3", method = RequestMethod.GET)
public String delete3(Long id, int page, String motCle) {
    factureRepos.deleteById(id);
    return "redirect:devises?page=" + page + "&motCle=" + motCle;
}


@RequestMapping(value="/edit3",method=RequestMethod.GET)
public String edit3 (Model model, 
@RequestParam (name="id")Long id)

{
//récupérer l'objet ayant l'id spécifié
Facture f =(Facture)
factureRepos.findById(id).orElse(null);
// placer le produit trouvé dans le modèle
model.addAttribute("facture", f);
// rediriger l'affichage vers la vue "editProduit"
return "editFacture";
}

@RequestMapping(value="/update3",method=RequestMethod.POST)
public String update3 (Model model, @Valid Facture facture , 
BindingResult bindingResult) {
if (bindingResult.hasErrors())
//en cas d'erreurs de validation
return "editFacture";
//enregistrer le produit dans la BD 
factureRepos.save(facture);
//Afficher une page pour confirmer l'enregistrement
return "confirmationFacture"; 
}




}