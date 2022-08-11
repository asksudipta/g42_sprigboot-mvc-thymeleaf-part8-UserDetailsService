package se.lexicon.mvcthymeleaf.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import se.lexicon.mvcthymeleaf.model.dto.ProductForm;
import se.lexicon.mvcthymeleaf.model.dto.ProductView;
import se.lexicon.mvcthymeleaf.service.CategoryService;
import se.lexicon.mvcthymeleaf.service.ProductService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    ProductService productService;
    CategoryService categoryService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    // http://localhost:8080/product/list
    @GetMapping("/list")
    public String showAllProductList(Model model) {
        System.out.println("showAllProductList method has been executed!");
        List<ProductView> productViews = productService.findAll();
        model.addAttribute("productViews", productViews);

        model.addAttribute("productListSize", productService.productListSize());
        return "product/products-view";
    }


    // http://localhost:8080/product/form
    @GetMapping("/form")
    public String displayProductForm(Model model) {
        System.out.println("displayProductForm method has been executed!");
        ProductForm productForm = new ProductForm();
        model.addAttribute("productForm", productForm);

        model.addAttribute("categoryViews", categoryService.findAll() );
        return "product/product-form";
    }


    @PostMapping("/add")
    public String add(@ModelAttribute("productForm") @Valid ProductForm productForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){
        System.out.println("productForm = " + productForm);

        if (bindingResult.hasErrors()){
            model.addAttribute("categoryViews", categoryService.findAll());
            return "product/product-form";
        }

        productService.create(productForm);

        redirectAttributes.addFlashAttribute("message", " Product name " + productForm.getName() + " is added successfully!" );
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/product/list";

    }

}
