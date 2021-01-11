package cl.example.dashboard.services;

import cl.example.entities.domain.entities.ClientEntity;
import cl.example.entities.domain.entities.ProductEntity;
import cl.example.entities.domain.entities.TransactionProductEntity;
import cl.example.entities.domain.repositories.ProductRepository;
import cl.example.entities.domain.repositories.TransactionProductRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cl.example.entities.domain.entities.vo.ProductVO;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionProductRepository transactionProductRepository;

    public ProductVO getProductById(int productId, int clientId) {
        ProductEntity entity = productRepository.findByIdAndClient_Id(productId, clientId);
        if (entity == null) {
            throw new RuntimeException("The selected product does not exist.");
        }

        return entity.toVO();
    }

    public ProductVO saveProduct(ProductVO vo) {
        ProductEntity entity = new ProductEntity();
        if (vo == null) {
            return null;
        }

        if (vo.getId() != null) {
            Optional<ProductEntity> opt = productRepository.findById(vo.getId());
            if (opt.isPresent()) {
                entity = opt.get();
            } else {
                throw new RuntimeException("The product to edit does not exist.");
            }
        }

        if (StringUtils.isEmpty(vo.getName())) {
            throw new RuntimeException("Name cannot be null.");
        }

        BeanUtils.copyProperties(vo, entity);
        entity.setClient(entityManager.getReference(ClientEntity.class, vo.getClientId()));
        entity = productRepository.saveAndFlush(entity);

        return entity.toVO();
    }

    public void deleteProduct(int productId, int clientId) {
        List<TransactionProductEntity> tps = transactionProductRepository.findById_Product_Id(productId);
        if (!tps.isEmpty()) {
            throw new RuntimeException("Unable to delete product if it already has transactions.");
        }

        productRepository.deleteByIdAndClient_Id(productId, clientId);
    }

}
