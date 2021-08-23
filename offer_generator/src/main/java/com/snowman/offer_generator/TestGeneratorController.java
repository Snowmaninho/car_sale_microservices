package com.snowman.offer_generator;


import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferServiceImpl;
import com.snowman.offer_generator.generators.OfferGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestGeneratorController {

    private OfferGenerator offerGenerator;
    private OfferServiceImpl offerService;

    @Autowired
    public TestGeneratorController(OfferGenerator offerGenerator, OfferServiceImpl offerService) {
        this.offerGenerator = offerGenerator;
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String showAllOffers(Model model) {
        Iterable<Offer> offers = offerService.findAll();
        model.addAttribute("offers", offers);
        return "test-offers";
    }

    @GetMapping("/addrandom")
    public String addRandomOffer() {
        offerService.saveOffer(offerGenerator.getRandomOffer());
        return "redirect:/";
    }
}
