package com.presentation.demo.controller.admin;

import com.presentation.demo.model.User;
import com.presentation.demo.model.card.product_offering.ProductOffering;
import com.presentation.demo.pojo.MapEntryImpl;
import com.presentation.demo.service.card.product_offerings.ProductOfferingService;
import com.presentation.demo.service.user.UserService;
import com.presentation.demo.service.user.security.SecurityService;
import com.presentation.demo.service.validation.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/admin/offerings")
public class AdminProductOfferingsController {

    private static Logger ADMIN_PRODUCT_OFFERINGS_LOGGER = LoggerFactory.getLogger(AdminUsersController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ProductOfferingService productOfferingService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserValidator userValidator;

    @GetMapping("")
    public String getAdminProductOfferings(@AuthenticationPrincipal User user, Model model){
        model.addAttribute("user", user);

        List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
        namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
        namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
        namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
        model.addAttribute("namesLinksList",namesLinksList);

        List<ProductOffering> productOfferings = productOfferingService.findAll();

        model.addAttribute("offerings",productOfferings);

        return "/admin/productOfferings";
    }

    @GetMapping("/create")
    public String createOfferingGet(Model model){
        model.addAttribute("offering",new ProductOffering());
        return "/admin/createProductOffering";
    }

    @PostMapping("/create")
    public String createOfferingPost(Model model, @ModelAttribute("offering") ProductOffering offering,@AuthenticationPrincipal User user){
        if ((productOfferingService.findProductOfferingsByName(offering.getName()) == null) && (productOfferingService.findProductOfferingByCashbackPercentageAndLimitsAndPercentage(offering.getCashbackPercentage(),offering.getLimits(),offering.getPercentage()) == null)){
            productOfferingService.save(offering);
            return "redirect:/admin/offerings";
        }
        else{
            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<ProductOffering> productOfferings = productOfferingService.findAll();

            model.addAttribute("offerings",productOfferings);

            if (productOfferingService.findProductOfferingsByName(offering.getName()) != null){
                model.addAttribute("name","Offering with this name already exists!");
            }
            else{
                model.addAttribute("name","Such offering already exists!");
            }
            return "/admin/createProductOffering";
        }

    }

    @GetMapping("/delete/{id}")
    public String deleteOffering(@PathVariable("id") Integer id){
        productOfferingService.delete(productOfferingService.findProductOfferingsById(id));
        return "redirect:/admin/offerings";
    }

    @GetMapping("/change/name/{id}")
    public String changeName(@PathVariable("id") Integer id, @RequestParam("name") String newName, Model model,@AuthenticationPrincipal User user){
        ADMIN_PRODUCT_OFFERINGS_LOGGER.info(newName);
        if (productOfferingService.findProductOfferingsByName(newName) == null){
            ProductOffering productOffering = productOfferingService.findProductOfferingsById(id);
            productOffering.setName(newName);
            productOfferingService.save(productOffering);
            return "redirect:/admin/offerings";
       }
       else{
           model.addAttribute("message","Offering with this name already exists!");
           model.addAttribute("user", user);

           List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
           namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
           namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
           namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
           model.addAttribute("namesLinksList",namesLinksList);

           List<ProductOffering> productOfferings = productOfferingService.findAll();

           model.addAttribute("offerings",productOfferings);
           return "/admin/productOfferings";
       }
    }

    @GetMapping("/change/cash/{id}")
    public String changeCash(@PathVariable("id") Integer id, @RequestParam("cash") Double cashBack, Model model, @AuthenticationPrincipal User user){
        ProductOffering productOffering = productOfferingService.findProductOfferingsById(id);
        if (productOfferingService.findProductOfferingByCashbackPercentageAndLimitsAndPercentage(cashBack,productOffering.getLimits(),productOffering.getPercentage()) == null){
            productOffering.setCashbackPercentage(cashBack);
            productOfferingService.save(productOffering);
            return "redirect:/admin/offerings";
        }
        else{
            model.addAttribute("message","Resulting in maintaining equal offers!");
            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<ProductOffering> productOfferings = productOfferingService.findAll();

            model.addAttribute("offerings",productOfferings);
            return "/admin/productOfferings";
        }
    }

    @GetMapping("/change/limits/{id}")
    public String changeLimits(@PathVariable("id") Integer id, @RequestParam("limits") Double limits, Model model, @AuthenticationPrincipal User user){
        ProductOffering productOffering = productOfferingService.findProductOfferingsById(id);
        if (productOfferingService.findProductOfferingByCashbackPercentageAndLimitsAndPercentage(productOffering.getCashbackPercentage(),limits,productOffering.getPercentage()) == null){
            productOffering.setLimits(limits);
            productOfferingService.save(productOffering);
            return "redirect:/admin/offerings";
        }
        else{
            model.addAttribute("message","Resulting in maintaining equal offers!");
            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<ProductOffering> productOfferings = productOfferingService.findAll();

            model.addAttribute("offerings",productOfferings);
            return "/admin/productOfferings";
        }
    }

    @GetMapping("/change/percentage/{id}")
    public String changePercentage(@PathVariable("id") Integer id, @RequestParam("percentage") Double percentage, Model model, @AuthenticationPrincipal User user){
        ProductOffering productOffering = productOfferingService.findProductOfferingsById(id);
        if (productOfferingService.findProductOfferingByCashbackPercentageAndLimitsAndPercentage(productOffering.getCashbackPercentage(),productOffering.getLimits(),percentage) == null){
            productOffering.setPercentage(percentage);
            productOfferingService.save(productOffering);
            return "redirect:/admin/offerings";
        }
        else{
            model.addAttribute("message","Resulting in maintaining equal offers!");
            model.addAttribute("user", user);

            List<MapEntryImpl<String,String>> namesLinksList = new LinkedList<>();
            namesLinksList.add(new MapEntryImpl<String,String>("Admin","/admin/"));
            namesLinksList.add(new MapEntryImpl<String, String>("Home","/index"));
            namesLinksList.add(new MapEntryImpl<String,String>("About","/about"));
            model.addAttribute("namesLinksList",namesLinksList);

            List<ProductOffering> productOfferings = productOfferingService.findAll();

            model.addAttribute("offerings",productOfferings);
            return "/admin/productOfferings";
        }
    }
}
