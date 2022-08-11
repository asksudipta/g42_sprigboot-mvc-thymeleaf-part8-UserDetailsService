package se.lexicon.mvcthymeleaf.service;

import se.lexicon.mvcthymeleaf.model.dto.ProductForm;
import se.lexicon.mvcthymeleaf.model.dto.ProductView;

import java.util.List;

public interface ProductService {

    List<ProductView> findAll();

    ProductView create(ProductForm productForm);

    int productListSize();
}
