package com.filot.filotshop.category.service;

import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.category.entity.CategoryForm;
import com.filot.filotshop.product.entity.ProductDTO;
import com.filot.filotshop.category.entity.Category;
import com.filot.filotshop.exception.CustomException;
import com.filot.filotshop.exception.ErrorCode;
import com.filot.filotshop.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public  Category addCategory(CategoryForm form) {
        Category category ;
        if(form.getParentName() == null){
            category = new Category(form.getName(), null,null);
        }else{
            Category parent = categoryRepository.findByName(form.getParentName()).orElseThrow(()-> new CustomException(ErrorCode.INVALID_REQUEST));
            category = new Category(form.getName(), null, parent);
        }
        categoryRepository.save(category);
        return category;
    }

    public List<Category> findAllCategories(){
        return categoryRepository.findChildrenByParentId(null);
    }

    public Optional<Category> findCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<CategoryDTO> findAllMainCategories(){
        return categoryRepository.findAllMainCategoriesToDTO();
    }

    public List<ProductDTO> findProductByName(String name){
        return categoryRepository.findProductByCategoryName(name);
    }

    public List<ProductDTO> findProductByName(String name, Integer page, String sort){
        return categoryRepository.findProductByCategoryName(name ,page,sort);
    }


    public List<Category> findChildrenCategoryByParentId(Long id){
        return categoryRepository.findChildrenByParentId(id);
    }
}
