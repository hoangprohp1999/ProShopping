package com.example.project_final_2.service.Impl;

import com.example.project_final_2.dto.reponse.RatingResponseDTO;
import com.example.project_final_2.dto.request.RatingRequestDTO;
import com.example.project_final_2.entity.product.Product;
import com.example.project_final_2.entity.product.UserProductKey;
import com.example.project_final_2.entity.user.Rating;
import com.example.project_final_2.entity.user.User;
import com.example.project_final_2.exception.BusinessException;
import com.example.project_final_2.exception.CanNotSaveToDatabaseException;
import com.example.project_final_2.exception.NotFoundEntityException;
import com.example.project_final_2.repository.ProductRepository;
import com.example.project_final_2.repository.RatingRepository;
import com.example.project_final_2.repository.UserRepository;
import com.example.project_final_2.service.RatingService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class RatingServiceImpl implements RatingService {

    private final ModelMapper modelMapper;
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public RatingServiceImpl(ModelMapper modelMapper, RatingRepository ratingRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<RatingResponseDTO> getAllRatingOfOneProduct(long productID, int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        return ratingRepository.findAllByProduct_Id(productID, pageable).stream().map(rating -> {
            RatingResponseDTO dto = modelMapper.map(rating, RatingResponseDTO.class);
            dto.setProductID(rating.getProduct().getId());
            dto.setUserID(rating.getUser().getId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createRating(String username, RatingRequestDTO request) throws Exception {
        try {
            Rating rating = getNewRatingFromDTO(username, request);
            ratingRepository.save(rating);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CanNotSaveToDatabaseException(descriptionOf("Cannot add"));
        }
    }

    protected Rating getNewRatingFromDTO(String username, RatingRequestDTO request) throws Exception {

        Product ratingProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundEntityException("Add rating fail"));

        User userRating = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundEntityException("User not found"));

        if(ratingRepository.existsByUserProductKey(new UserProductKey(userRating.getId(), ratingProduct.getId()))){
            throw new BusinessException("Add rating fail");        //User rated this product
        }

        Rating newRating = new Rating(userRating, ratingProduct, request.getScore(), request.getComment());
        return newRating;
    }

    @Override
    public void editRating(String username, RatingRequestDTO request) throws Exception {
        try {
            Rating rating = getEditRatingFromDTO(username, request);
            ratingRepository.save(rating);
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new CanNotSaveToDatabaseException(descriptionOf("Cannot edit"));
        }
    }

    protected Rating getEditRatingFromDTO(String username, RatingRequestDTO request) throws Exception {
        User userRating = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundEntityException("User not found"));
        Product ratingProduct = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new NotFoundEntityException("Add rating fail"));

        if (!ratingRepository.existsByUserProductKey(new UserProductKey(userRating.getId(), ratingProduct.getId()))) {
            throw new NotFoundEntityException("Edit rating fail");       //Check rating is exist
        }

        Rating ratingToEdit = ratingRepository.findByUserProductKey(new UserProductKey(userRating.getId(),ratingProduct.getId())).orElseThrow(() -> new NotFoundEntityException("Edit rating fail"));

        ratingToEdit.setComment(request.getComment());
        ratingToEdit.setScore(request.getScore());
        return ratingToEdit;
    }


    protected String descriptionOf(String message) {
        return message;
    }

    @Transactional
    @Override
    public void deleteRating(String username, long productID) {

        User userRating = userRepository.findByEmail(username)
                .orElseThrow(() -> new NotFoundEntityException("User not found"));

        UserProductKey userProductKey = new UserProductKey(userRating.getId(), productID);

        if(!ratingRepository.existsByUserProductKey(userProductKey)){
            throw new NotFoundEntityException("Delete rating fail");
        }

        ratingRepository.deleteByUserProductKey(userProductKey);
    }
}
