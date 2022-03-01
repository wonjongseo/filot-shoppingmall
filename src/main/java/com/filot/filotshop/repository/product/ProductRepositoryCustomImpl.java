package com.filot.filotshop.repository.product;

import com.filot.filotshop.dto.product.ProductDTO;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final EntityManager em;


    //TODO RE
    @Override
    public List findAllProductsJsonType() {
        return em.createQuery("select  new com.filot.filotshop.dto.product.ProductDTO (p.id, p.name, p.price, p.size,p. imageUrl ,p.amount) FROM Product p   ").getResultList();
    }

    @Override
    public List<ProductDTO> findProductsSortedByName(String categoryName, Integer page, Integer size) {
        return em.createQuery("select new com.filot.filotshop.dto.product.ProductDTO (p.id, p.name, p.price, p.size, p.imageUrl,p.amount) " +
                        "from Category c join c.products p where c.name = :name order by p.name")
                .setParameter("name", categoryName)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<ProductDTO> findProductsSortedByPriceASC(String categoryName, Integer page, Integer size) {
        return em.createQuery("select new com.filot.filotshop.dto.product.ProductDTO (p.id, p.name, p.price, p.size, p.imageUrl,p.amount) " +
                        "from Category c join c.products p where c.name = :name order by p.price asc ")
                .setParameter("name", categoryName)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<ProductDTO> findProductsSortedByPriceDESC(String categoryName, Integer page, Integer size) {
        return em.createQuery("select new com.filot.filotshop.dto.product.ProductDTO (p.id, p.name, p.price, p.size, p.imageUrl,p.amount) " +
                "from Category c join c.products p where c.name = :name order by p.price desc")
                .setParameter("name", categoryName)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }

    @Override
    public List<ProductDTO> findProductsSortedByNewest(String categoryName, Integer page, Integer size) {
        return em.createQuery("select new com.filot.filotshop.dto.product.ProductDTO (p.id, p.name, p.price, p.size, p.imageUrl,p.amount) " +
                        "from Category c join c.products p where c.name = :name order by p.createAt desc")
                .setParameter("name", categoryName)
                .setFirstResult(page)
                .setMaxResults(size)
                .getResultList();
    }


}
