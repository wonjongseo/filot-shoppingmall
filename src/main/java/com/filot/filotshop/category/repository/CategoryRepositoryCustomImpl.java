package com.filot.filotshop.category.repository;

import com.filot.filotshop.category.entity.CategoryDTO;
import com.filot.filotshop.product.entity.ProductDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static com.filot.filotshop.product.service.ProductService.SHOW_PRODUCT_COUNT;

@RequiredArgsConstructor
public class CategoryRepositoryCustomImpl implements CategoryRepositoryCustom{

    private final EntityManager em;
    private final String SELECT_CATEGORYDTO = "select c.id, c.name FROM Category c";

    private List<CategoryDTO> createCategoryDTO(List<Object[]> categoryDTOs) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Object[] categoryDTO : categoryDTOs) {
            CategoryDTO newCategoryDTO = new CategoryDTO();
            newCategoryDTO.setId((Long) categoryDTO[0]);
            newCategoryDTO.setName((String) categoryDTO[1]);
            categoryDTOList.add(newCategoryDTO);
        }
        return categoryDTOList;
    }




    @Override
    public List<CategoryDTO> findAllParentCategory() {
        List<Object[]> categoryDTOs = em.createQuery(SELECT_CATEGORYDTO + " where c.parent is null").getResultList();

        return createCategoryDTO(categoryDTOs);
    }






}
