package com.snowman.main_auto_service.controllers;

import com.snowman.common_libs.domain.Offer;
import com.snowman.common_libs.services.OfferService;
import com.snowman.main_auto_service.entity.dto.FilterFormDTO;
import com.snowman.main_auto_service.entity.dto.OfferDTO;
import com.snowman.main_auto_service.entity.paging.Paged;
import com.snowman.main_auto_service.entity.paging.Paging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@Slf4j
@RequestMapping("/offers")
public class OffersController {

    private final OfferService offerService;

    @Autowired
    public OffersController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping
    public String showAllOffers(@RequestParam("make") Optional<String> make,
                                @RequestParam("cModel") Optional<String> cModel,
                                @RequestParam("minYear") Optional<Integer> minYear,
                                @RequestParam("maxYear") Optional<Integer> maxYear,
                                @RequestParam("minPrice") Optional<Integer> minPrice,
                                @RequestParam("maxPrice") Optional<Integer> maxPrice,
                                @RequestParam("minHp") Optional<Integer> minHp,
                                @RequestParam("maxHp") Optional<Integer> maxHp,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                Model model) {

        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        FilterFormDTO filterForm = new FilterFormDTO(make.orElse(""), cModel.orElse(""),
                minYear.orElse(null), maxYear.orElse(null), minPrice.orElse(null),
                maxPrice.orElse(null), minHp.orElse(null), maxHp.orElse(null));

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);
        Page<Offer> offers = findFilteredOffers(filterForm, pageable);
        addParamsToModel(filterForm, offers, model);

        model.addAttribute("posts", getPage(filterForm, pageable, currentPage, pageSize));

        return "offers";
    }

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String showFilteredOffers(@RequestParam("page") Optional<Integer> page,
                                     @RequestParam("size") Optional<Integer> size,
                                     FilterFormDTO filterForm, RedirectAttributes redirectAttributes) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        addParamsToPath(filterForm, currentPage, pageSize, redirectAttributes);

        return "redirect:/offers";
    }

    @GetMapping("/add")
    public String addNewOffer(OfferDTO offerDTO, Model model) {
        return "add-offer";
    }

    @PostMapping("/add")
    public String addNewOffer(@Valid OfferDTO offerDTO, BindingResult bindingResult, Model model,
                              Authentication authentication) {

        if (bindingResult.hasErrors()) {
            return "add-offer";
        }

        Offer result = offerService.createOffer(offerDTO.getTitle(), offerDTO.getAnonsName(), authentication.getName(),
                offerDTO.getCarMake(), offerDTO.getCarModel(), offerDTO.getCarYear(), offerDTO.getCarPower(),
                offerDTO.getCarPrice(), offerDTO.getOfferText());

        offerService.saveOffer(result);

/*        log.info("Added offer: Author - " + result.getAuthorName() + ", Anons name - " + result.getAnonsName()
                + ", Car make - " + result.getCar().getCarMake() + ", Car model - " + result.getCar().getCarModel()
                + ", Car Year - " + result.getCar().getCarYear() + ", Car Power - " + result.getCar().getCarPower()
                + ", Car Price - " + result.getCar().getCarPrice());*/

        return "redirect:/offers";
    }

    @GetMapping("/{offerId}")
    public String offerDetails (@PathVariable Long offerId, Model model) {
        Offer offer = offerService.findOfferById(offerId);
        model.addAttribute("offer", offer);

        return "offer-details";
    }

    @GetMapping("/userOffers/{author}")
    public String myOffers (@PathVariable String author, @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size, Model model) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Pageable pageable = PageRequest.of(currentPage - 1, pageSize);

        Page<Offer> offers = offerService.findOffersByAuthorName(author, pageable);

        model.addAttribute("authorName", author);
        model.addAttribute("offers", offers);
        model.addAttribute("posts", getPage(offers, currentPage, pageSize));

        return "my-offers";
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

    public void addParamsToPath(FilterFormDTO filterForm, int currentPage, int pageSize,
                                RedirectAttributes redirectAttributes) {
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

    public Paged<Offer> getPage(FilterFormDTO filterForm, Pageable pageable, int pageNumber, int size) {
        Page<Offer> offerPage = findFilteredOffers(filterForm, pageable);
        return new Paged<>(offerPage, Paging.of(offerPage.getTotalPages(), pageNumber, size));
    }

    public Paged<Offer> getPage(Page<Offer> offerPage, int pageNumber, int size) {
        return new Paged<>(offerPage, Paging.of(offerPage.getTotalPages(), pageNumber, size));
    }
}
