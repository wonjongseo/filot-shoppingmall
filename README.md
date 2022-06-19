# Filot Online Shopping mall

## 스프링과 JPA를 이용한 실력 향상 온라인 쇼핑몰 프로젝트 입니다
## スプリングとJPAを利用した実力向上オンラインショッピングモールプロジェクトです。


<h3>
  <a href="https://drive.google.com/file/d/1srV9zWc_Rp6C3dK9q16Y09yRTrjOIJMv/view?usp=sharing ">portfolio PDF <-  </a>
</h3>

<img  src="https://velog.velcdn.com/images/wonjongseo/post/01e43020-9faa-40b7-9cb8-04d8f26d8d14/image.png" />



## 💻 Backend Development Environment

<ul>
    <li>
JAVA  
  </li>
  <li>
MAVEN  
  </li>  
  <li>
Spring boot  
  </li>
    <li>
Spring data Jpa  
  </li>
    <li>
Spring Security  
  </li>
  </ul>
  

## 🗂 Folder Structure

```
📦functions
┣ 📂admin
┃ ┣ 📂controller
┃ ┃ ┣ AdminCategoryController.java
┃ ┃ ┣ AdminController.java
┃ ┃ ┣ AdminProductController.java
┃ ┃ ┣ AdminUserController.java
┃ ┣ 📂service
┃ ┃ ┣ AdminService.java
┃ ┃ ┣ 📂info
┃ ┃ ┣ 📂count
┃ ┃ ┣ 📂lectures
┣ 📂exception
┃ ┣ CustomException.java
┃ ┣ ErrorCode.java
┃ ┣ ErrorResponse.java
┃ ┣ GlobalExceptionHandler.java
┣ 📂config
┃ ┣ 📂mail
┃ ┃ ┣ MailHandler.java
┃ ┃ ┣ MailService.java
┃ ┣ 📂s3
┃ ┃ ┣ S3Config.java
┃ ┃ ┣ S3Service.java
┃ ┣ 📂security
┃ ┃ ┣ JwtAuthenticationFilter.java
┃ ┃ ┣ JwtTokenProvider.java
┃ ┃ ┣ SecurityConfig.java
┣ 📂basket
┃ ┣ 📂controller
┃ ┃ ┣ BasketController.java
┃ ┣ 📂entity
┃ ┃ ┣ Basket.java
┃ ┃ ┣ BasketDTO.java
┃ ┃ ┣ BasketForm.java
┃ ┣ 📂repository
┃ ┃ ┣ BasketRepository.java
┃ ┣ 📂service
┃ ┃ ┣ BasketService.java
┣ 📂category
┃ ┣ 📂controller
┃ ┃ ┣ CategoryController.java
┃ ┣ 📂entity
┃ ┃ ┣ Category.java
┃ ┃ ┣ CategoryDTO.java
┃ ┃ ┣ CategoryForm.java
┃ ┣ 📂repository
┃ ┃ ┣ CategoryRepository.java
┃ ┃ ┣ CategoryRepositoryCustom.java
┃ ┃ ┣ CategoryRepositoryCustomImpl.java
┃ ┣ 📂service
┃ ┃ ┣ CategoryService.java
┣ 📂user
┃ ┣ 📂controller
┃ ┃ ┣ AuthController.java
┃ ┃ ┣ UserController.java
┃ ┣ 📂entity
┃ ┃ ┣ Address.java
┃ ┃ ┣ FindPasswordDTO.java
┃ ┃ ┣ JoinForm.java
┃ ┃ ┣ LoginForm.java
┃ ┃ ┣ UpdateDTO.java
┃ ┃ ┣ User.java
┃ ┃ ┣ UserDTO.java
┃ ┣ 📂repository
┃ ┃ ┣ UserRepository.java
┃ ┣ 📂service
┃ ┃ ┣ UserService.java
┣ 📂review
┃ ┣ 📂controller
┃ ┃ ┣ ReviewController.java
┃ ┣ 📂entity
┃ ┃ ┣ Review.java
┃ ┃ ┣ ReviewDTO.java
┃ ┃ ┣ ReviewForm.java
┃ ┣ 📂repository
┃ ┃ ┣ ReviewRepository.java
┃ ┃ ┣ ReviewRepositoryCustom.java
┃ ┃ ┣ ReviewRepositoryCustomImpl.java
┃ ┣ 📂service
┃ ┃ ┣ ReviewService.java
┣ 📂product
┃ ┣ 📂controller
┃ ┃ ┣ ProductController.java
┃ ┣ 📂entity
┃ ┃ ┣ DetailProduct.java
┃ ┃ ┣ Image.java
┃ ┃ ┣ Product.java
┃ ┃ ┣ ProductDTO.java
┃ ┃ ┣ ProductForm.java
┃ ┣ 📂repository
┃ ┃ ┣ ImageRepository.java
┃ ┃ ┣ ProductRepository.java
┃ ┃ ┣ ProductRepositoryCustom.java
┃ ┃ ┣ ProductRepositoryCustomImpl.java
┃ ┣ 📂service
┃ ┃ ┣ ImageService.java
┃ ┃ ┣ ProductService.java




