package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferService;
import com.snowman.main_auto_service.dto.FilterFormDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/offers")
public class OffersController {

    private OfferService offerService;

    @Autowired
    public OffersController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public String showAllOffers(@RequestParam("make") Optional<String> make, @RequestParam("cModel") Optional<String> cModel, @RequestParam("minYear") Optional<Integer> minYear,
                                @RequestParam("maxYear") Optional<Integer> maxYear, @RequestParam("minPrice") Optional<Integer> minPrice, @RequestParam("maxPrice") Optional<Integer> maxPrice,
                                @RequestParam("minHp") Optional<Integer> minHp, @RequestParam("maxHp") Optional<Integer> maxHp,
                                @RequestParam("page") Optional<Integer> page,@RequestParam("size") Optional<Integer> size,
                                Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        FilterFormDTO filterForm = new FilterFormDTO(make.orElse(""), cModel.orElse(""), minYear.orElse(null), maxYear.orElse(null),
                minPrice.orElse(null), maxPrice.orElse(null), minHp.orElse(null), maxHp.orElse(null));

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Offer> offers = findFilteredOffers(filterForm, pageable);
        addParamsToModel(filterForm, offers, model);

        return "offers";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String showFilteredOffers(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
                                     FilterFormDTO filterForm, RedirectAttributes redirectAttributes) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        addParamsToPath(filterForm, currentPage, pageSize, redirectAttributes);

        return "redirect:/offers";
    }

    @GetMapping("/add")
    public String addNewOffer() {
        return "add-offer";
    }

    @PostMapping("/add")
    public String addNewOffer(@RequestParam String title, @RequestParam String anonsName, @RequestParam String carMake,
                              @RequestParam String carModel, @RequestParam int carYear, @RequestParam int carPower,
                              @RequestParam int carPrice, @RequestParam String offerText, Model model, Authentication auth) {
        Offer offer = offerService.createOffer(title, anonsName, auth.getName(), carMake, carModel, carYear, carPower, carPrice, offerText);
        offerService.saveOffer(offer);
        return "redirect:/offers";
    }


    public Page<Offer> findFilteredOffers(FilterFormDTO filterForm, Pageable pageable) {
        return offerService.findFilteredOffers(filterForm.getCarMake(), filterForm.getCarModel(),
                filterForm.getCarMinYear(), filterForm.getCarMaxYear(), filterForm.getCarMinPrice(),
                filterForm.getCarMaxPrice(), filterForm.getCarMinHp(), filterForm.getCarMaxHp(), pageable);
    }

    public void addParamsToModel(FilterFormDTO filterForm, Page<Offer> offers, Model model) {
        model.addAttribute("offers", offers);
        model.addAllAttributes(addAttributes(filterForm));

        int totalPages = offers.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
    }

    public void addParamsToPath(FilterFormDTO filterForm, int currentPage, int pageSize, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("page", currentPage);
        redirectAttributes.addAttribute("size", pageSize);
        redirectAttributes.addAllAttributes(addAttributes(filterForm));
    }
    
    public Map<String, Object> addAttributes(FilterFormDTO filterForm) {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("make", filterForm.getCarMake());
        attributes.put("cModel", filterForm.getCarModel());
        attributes.put("minYear", filterForm.getCarMinYear());
        attributes.put("maxYear", filterForm.getCarMaxYear());
        attributes.put("minPrice", filterForm.getCarMinPrice());
        attributes.put("maxPrice", filterForm.getCarMaxPrice());
        attributes.put("minHp", filterForm.getCarMinHp());
        attributes.put("maxHp", filterForm.getCarMaxHp());
        return attributes;
    }
}
