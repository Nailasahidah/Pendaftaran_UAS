//package com.nailasahidah.pendaftaran.dtomapper;
//
//import com.nailasahidah.pendaftaran.domain.Product;
//import com.nailasahidah.pendaftaran.domain.Role;
//import com.nailasahidah.pendaftaran.domain.User;
//import com.nailasahidah.pendaftaran.dto.ProductDTO;
//import com.nailasahidah.pendaftaran.dto.UserDTO;
//import org.springframework.beans.BeanUtils;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ProductDTOMapper {
//    public static ProductDTO fromProduct (Product product) {
//        ProductDTO productDTO = new ProductDTO();
//        BeanUtils.copyProperties(product, productDTO);
//        return productDTO;
//    }
//    public static ProductDTO fromProduct (Product product, Role role) {
//        ProductDTO productDTO = new ProductDTO();
//        BeanUtils.copyProperties(product, productDTO);
//        productDTO.setRoleName(role.getName());
//        productDTO.setPermissions(role.getPermission());
//        return productDTO;
//    }
//
//    public static Product toProduct (ProductDTO productDTO) {
//        Product product = new Product();
//        BeanUtils.copyProperties(productDTO, product);
//        return product;
//    }
//}
