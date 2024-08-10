//package com.nailasahidah.pendaftaran.repository.implementation;
//
//import com.nailasahidah.pendaftaran.domain.Product;
//import com.nailasahidah.pendaftaran.dto.ProductDTO;
//import com.nailasahidah.pendaftaran.exception.ApiException;
//import com.nailasahidah.pendaftaran.repository.ProductRepository;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//import java.util.Optional;
//
//import static com.nailasahidah.pendaftaran.query.ProductQuery.UPDATE_PRODUCT_IMAGE_QUERY;
//import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
//import static java.util.Map.of;
//import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentContextPath;
//
//@Repository
//@RequiredArgsConstructor
//@Slf4j
//public class ProductRepositoryImpl implements ProductRepository {
//    private final NamedParameterJdbcTemplate jdbc;
//    @Override
//    public Page<Product> findByNameContaining(String name, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public void updateImage(ProductDTO product, MultipartFile image) {
//        String productImageUrl = setProductImageUrl(product.getName());
//        product.setImageUrl(productImageUrl);
//        saveImage(product.getName(), image);
//        jdbc.update(UPDATE_PRODUCT_IMAGE_QUERY, of("imageUrl", productImageUrl, "id", product.getId()));
//    }
//    private String setProductImageUrl(String name) {
//        return fromCurrentContextPath().path("/product/image/" + name + ".png").toUriString();
//    }
//    private void saveImage(String name, MultipartFile image) {
//        Path fileStorageLocation = Paths.get(System.getProperty("product.home") + "/Downloads/images/").toAbsolutePath().normalize();
//        if (!Files.exists(fileStorageLocation)) {
//            try {
//                Files.createDirectories(fileStorageLocation);
//            } catch (Exception exception) {
//                log.error(exception.getMessage());
//                throw new ApiException("Unable to create directories to save image");
//            }
//            log.info("Created directories: {}", fileStorageLocation);
//        }
//        try {
//            Files.copy(image.getInputStream(), fileStorageLocation.resolve(name + ".png"), REPLACE_EXISTING);
//        } catch (IOException exception) {
//            log.error(exception.getMessage());
//            throw new ApiException(exception.getMessage());
//        }
//        log.info("File saved in: {} folder", fileStorageLocation);
//    }
//
//    @Override
//    public <S extends Product> S save(S entity) {
//        return null;
//    }
//
//    @Override
//    public <S extends Product> List<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Product> findById(Long aLong) {
//        return Optional.empty();
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public List<Product> findAll() {
//        return null;
//    }
//
//    @Override
//    public List<Product> findAllById(Iterable<Long> longs) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Product entity) {
//
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> longs) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Product> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//
//    }
//
//    @Override
//    public Iterable<Product> findAll(Sort sort) {
//        return null;
//    }
//
//    @Override
//    public Page<Product> findAll(Pageable pageable) {
//        return null;
//    }
//}
