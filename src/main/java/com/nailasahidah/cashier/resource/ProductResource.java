package com.nailasahidah.cashier.resource;

import com.nailasahidah.cashier.domain.HttpResponse;
import com.nailasahidah.cashier.domain.Product;
import com.nailasahidah.cashier.dto.UserDTO;
import com.nailasahidah.cashier.report.ProductReport;
import com.nailasahidah.cashier.service.ProductService;
import com.nailasahidah.cashier.service.RoleService;
import com.nailasahidah.cashier.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.nailasahidah.cashier.utils.UserUtils.getAuthenticatedUser;
import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;
import static org.springframework.http.MediaType.parseMediaType;

@RestController
@RequestMapping(path = "/product")
@RequiredArgsConstructor
public class ProductResource {
    private final ProductService productService;
    private final UserService userService;
    private final RoleService roleService;
    private final ApplicationEventPublisher publisher;

    @GetMapping("/list")
    public ResponseEntity<HttpResponse> getProducts(@AuthenticationPrincipal UserDTO user,
                                                     @RequestParam Optional<Integer> page,
                                                     @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", productService.getProducts(page.orElse(0), size.orElse(10))))
                        .message("Products retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PostMapping("/create")
    public ResponseEntity<HttpResponse> createProduct(@AuthenticationPrincipal UserDTO user,
                                                       @RequestBody Product product) {
        return ResponseEntity.created(URI.create(""))
                .body(
                        HttpResponse.builder()
                                .timeStamp(now().toString())
                                .data(of("user", userService.getUserByEmail(user.getEmail()),
                                        "product", productService.createProduct(product)))
                                .message("Product created")
                                .status(CREATED)
                                .statusCode(CREATED.value())
                                .build());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<HttpResponse> getProduct(@AuthenticationPrincipal UserDTO user,
                                                    @PathVariable("id") Long id) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "product", productService.getProduct(id)))
                        .message("Product retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @GetMapping("/search")
    public ResponseEntity<HttpResponse> searchProduct(@AuthenticationPrincipal UserDTO user,
                                                       Optional<String> name,
                                                       @RequestParam Optional<Integer> page,
                                                       @RequestParam Optional<Integer> size) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "page", productService.searchProducts(name.orElse(""), page.orElse(0), size.orElse(10))))
                        .message("Products retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @PutMapping("/update")
    public ResponseEntity<HttpResponse> updateProduct(@AuthenticationPrincipal UserDTO user,
                                                       @RequestBody Product product) {
        return ResponseEntity.ok(
                HttpResponse.builder()
                        .timeStamp(now().toString())
                        .data(of("user", userService.getUserByEmail(user.getEmail()),
                                "product", productService.updateProduct(product)))
                        .message("Product updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }

//    @PatchMapping("/update/imageProduct")
//    public ResponseEntity<HttpResponse> updateProfileImage(Authentication authentication, @RequestParam("image") MultipartFile image) throws InterruptedException {
//        ProductDTO product = getAuthenticatedProduct(authentication);
//        productService.updateImage(product, image);
//        return ResponseEntity.ok().body(
//                HttpResponse.builder()
//                        .data(of("product", productService.getProduct(product.getId()),
//                                "roles", roleService.getRoles()))
//                        .timeStamp(now().toString())
//                        .message("Product image updated")
//                        .status(OK)
//                        .statusCode(OK.value())
//                        .build());
//    }
//    @GetMapping(value = "/imageProduct/{fileName}", produces = IMAGE_PNG_VALUE)
//    public byte[] getProfileImage(@PathVariable("fileName") String fileName) throws Exception {
//        return Files.readAllBytes(Paths.get(System.getProperty("product.home") + "/Downloads/images/" + fileName));
//    }

    @PatchMapping("/update/image")
    public ResponseEntity<HttpResponse> updateProductImage(Authentication authentication, @RequestParam("image") MultipartFile image) throws InterruptedException {
        UserDTO user = getAuthenticatedUser(authentication);
        userService.updateImage(user, image);
        return ResponseEntity.ok().body(
                HttpResponse.builder()
                        .data(of("user", userService.getUserById(user.getId()),
                                "roles", roleService.getRoles()))
                        .timeStamp(now().toString())
                        .message("Product image updated")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }
    @GetMapping(value = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getProfileImage(@PathVariable("fileName") String fileName) throws Exception {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home") + "/Downloads/products/" + fileName));
    }


    @GetMapping("/download/report")
    public ResponseEntity<Resource> downloadReport() {
        List<Product> products = new ArrayList<>();
        productService.getProducts().iterator().forEachRemaining(products::add);
        ProductReport report = new ProductReport(products);
        HttpHeaders headers = new HttpHeaders();
        headers.add("File-Name", "product-report.xlsx");
        headers.add(CONTENT_DISPOSITION, "attachment;File-Name=product-report.xlsx");
        return ResponseEntity.ok().contentType(parseMediaType("application/vnd.ms-excel"))
                .headers(headers).body(report.export());
    }
}
