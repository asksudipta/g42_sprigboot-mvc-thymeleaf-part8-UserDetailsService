package se.lexicon.mvcthymeleaf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.mvcthymeleaf.converter.ProductConverter;
import se.lexicon.mvcthymeleaf.model.dto.ProductForm;
import se.lexicon.mvcthymeleaf.model.dto.ProductView;
import se.lexicon.mvcthymeleaf.model.entity.Category;
import se.lexicon.mvcthymeleaf.model.entity.Product;
import se.lexicon.mvcthymeleaf.repository.CategoryRepository;
import se.lexicon.mvcthymeleaf.repository.ProductRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    ProductConverter productConverter;
    CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<ProductView> findAll() {
        List<Product> productList = productRepository.findAll();
        Collection<ProductView> productViewList = productConverter.toViews(productList);
        return new ArrayList<>(productViewList);
    }

    @Override
    public ProductView create(ProductForm productForm) {
        if (productForm == null) throw new IllegalArgumentException("Product Form object was null");

        Optional<Category> categoryOptional = categoryRepository.findById(productForm.getCategoryId());
        if (!categoryOptional.isPresent()) throw new IllegalArgumentException("Category Id was not valid");

        Product insertedProduct = productRepository.save(new Product(productForm.getName(), productForm.getPrice(), categoryOptional.get(), productForm.getDate()));

        ProductView productView = productConverter.toView(insertedProduct);

        return productView;
    }

    @Override
    public int productListSize() {
        return productRepository.findAll().size();
    }


}
