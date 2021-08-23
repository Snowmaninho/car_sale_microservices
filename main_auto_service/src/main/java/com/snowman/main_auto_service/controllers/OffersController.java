package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/offers")
public class OffersController {

    private OfferService offerService;

    @Autowired
    public OffersController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public String showAllOffers(Model model) {
        Iterable<Offer> offers = offerService.findAll();
        model.addAttribute("offers", offers);
        return "offers";
    }

    @GetMapping("/add")
    public String addNewOffer() {
        return "add-offer";
    }

    @PostMapping("/add")
    public String addNewOffer(@RequestParam String title, @RequestParam String anonsName, @RequestParam String carMake,
                              @RequestParam String carModel, @RequestParam int carYear, @RequestParam int carPower,
                              @RequestParam int carPrice, @RequestParam String offerText, Model model) {
        Offer offer = offerService.createOffer(title, anonsName,"RandomAuthor", carMake, carModel, carYear, carPower, carPrice, offerText);
        offerService.saveOffer(offer);
        return "redirect:/offers";
    }

}
