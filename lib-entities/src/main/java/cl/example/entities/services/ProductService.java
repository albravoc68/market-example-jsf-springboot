package cl.example.entities.services;

import cl.example.entities.entities.ClientEntity;
import cl.example.entities.entities.ProductEntity;
import cl.example.entities.entities.TransactionProductEntity;
import cl.example.entities.repositories.ProductRepository;
import cl.example.entities.repositories.TransactionProductRepository;
import cl.example.entities.entities.vo.ProductVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<ProductVO> getProducts(int clientId) {
        List<ProductEntity> entities = productRepository.findByClient_IdAndEnable(clientId, true);
        return entities.stream().map(ProductEntity::toVO).collect(Collectors.toList());
    }

}
