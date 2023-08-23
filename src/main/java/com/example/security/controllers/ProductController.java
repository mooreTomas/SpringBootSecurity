package com.example.security.controllers;

import com.example.security.exceptions.ForbiddenException;
import com.example.security.model.Product;
import com.example.security.services.ProductService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.client.HttpServerErrorException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class ProductController {

    @Autowired
    private ProductService productService;


    @GetMapping("")
    public String index() {
        return "index";
    }

    @GetMapping("/products")
    public String allProducts(Model model){
        model.addAttribute("productList", productService.getAllProducts());
        return "allProducts";
    }


    // New GET mapping for the addProduct form
    @GetMapping("/addProduct")
    public String addProductForm(Model model, HttpServletRequest request) {
        if (!request.isUserInRole("SUPER_ADMIN") && !request.isUserInRole("ADMIN")) {
            throw new ForbiddenException();
        } else {
            model.addAttribute("aProduct", new Product());

            return "addProduct";
        }
    }
    // POST mapping for adding a product
    @PostMapping("/addProduct")
    public ModelAndView addAProduct(@Valid @ModelAttribute("aProduct") Product p, BindingResult result, HttpServletRequest request) {


        if (!request.isUserInRole("SUPER_ADMIN") && !request.isUserInRole("ADMIN")) {
            throw new ForbiddenException();
        } else {

            if (result.hasErrors()) {
                return new ModelAndView("addProduct");
            }
            productService.addAProduct(p);
            return new ModelAndView("redirect:/products");
        }
    }

    @PostMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("code") String code) {
        productService.deleteAProduct(code);
        return "redirect:/products";
    }



}
