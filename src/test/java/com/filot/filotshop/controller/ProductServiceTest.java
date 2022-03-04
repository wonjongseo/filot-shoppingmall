package com.filot.filotshop.controller;

import com.filot.filotshop.category.entity.CategoryForm;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.product.entity.ProductForm;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.product.entity.Product;
import com.filot.filotshop.category.repository.CategoryRepository;
import com.filot.filotshop.category.service.CategoryService;
import com.filot.filotshop.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application.yml"
)
public class ProductServiceTest {

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    CategoryRepository categoryRepository;

    public static CategoryForm child =
            new CategoryForm("child", "parent");

    public static CategoryForm parent =
            new CategoryForm("parent",null);

    public static ProductForm productForm = ProductForm. // 10
            builder()
            .name("테스트 상품!2!") //
            .price(10000)
            .amount(1000)

            .size("90,95,100")
            .description("테스트 상품입니다")

            .color("red,green,blue")
            .categoryName("BEST")
            .build();

    @Test
    public void 카테고리로_상품_등록한다(){
        Category parentCategory= categoryService.addCategory(parent);
        Product product = productService.addProduct(productForm);
    }
    @Test
    public void 카테고리등록한다(){

        Category parentCategory= categoryService.addCategory(parent);
        Category childCategory = categoryService.addCategory(child);

        Category category = categoryService.findCategoryByName(parent.getName()).get();
        Category c = categoryService.findCategoryByName(child.getName()).get();

        assertThat(parentCategory).isEqualTo(parentCategory);
        assertThat(c).isEqualTo(childCategory);
    }

    @Test
    public void 부모카테고리에서_자식카테고리들_찾는다(){
        Category parentCategory= categoryService.addCategory(parent);

        Category childCategory1 = categoryService.addCategory(child);
        child.setName("child2");
        Category childCategory2 = categoryService.addCategory(child);

        List<Category> childrenCategoryByParentId = categoryService.findChildrenCategoryByParentId(parentCategory.getId());

        assertThat(childrenCategoryByParentId.size()).isEqualTo(2);
        assertThat(childrenCategoryByParentId.get(0)).isEqualTo(childCategory1);
        assertThat(childrenCategoryByParentId.get(0).getName()).isEqualTo(childCategory1.getName());
        assertThat(childrenCategoryByParentId.get(1).getName()).isEqualTo(childCategory2.getName());
    }


    @Test
    public void 카테고리이름으로_상품들_조회한다(){
        List<ProductDTO> best = categoryRepository.findProductByCategoryName("BEST");
        for (ProductDTO productDTO : best) {
            System.out.println("productDTO = " + productDTO);
        }
    }

    @Test
    public void 카테고리없이_모든_상품_조회한다(){
        List<ProductDTO> allProducts = productService.findAllProducts();
        for (ProductDTO allProduct : allProducts) {
            System.out.println(allProduct);
        }
    }





}
