package com.example.project_final_2.service;

import com.example.project_final_2.dto.reponse.RatingResponseDTO;
import com.example.project_final_2.dto.request.RatingRequestDTO;
import com.example.project_final_2.entity.product.UserProductKey;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RatingService {
    List<RatingResponseDTO> getAllRatingOfOneProduct(long productID, int pageIndex, int pageSize);

    void createRating(String username, RatingRequestDTO request) throws Exception;

    void editRating(String username, RatingRequestDTO request) throws Exception;

    void deleteRating(String username, long productID);
}
