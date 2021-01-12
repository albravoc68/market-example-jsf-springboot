package cl.example.market.services;

import cl.example.entities.domain.entities.ProductEntity;
import cl.example.entities.domain.entities.vo.ProductVO;
import cl.example.entities.domain.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<ProductVO> getProducts(int clientId) {
        List<ProductEntity> entities = productRepository.findByClient_IdAndEnable(clientId, true);
        return entities.stream().map(ProductEntity::toVO).collect(Collectors.toList());
    }

}
