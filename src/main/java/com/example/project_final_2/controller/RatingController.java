package com.example.project_final_2.controller;

import com.example.project_final_2.dto.reponse.SuccessResponse;
import com.example.project_final_2.dto.request.RatingRequestDTO;
import com.example.project_final_2.entity.product.UserProductKey;
import com.example.project_final_2.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }


    @GetMapping("/show-rating/{productId}/{pageIndex}/{pageSize}")
    public ResponseEntity getAllRatingProduct(@PathVariable int productId, @PathVariable int pageIndex,
                                              @PathVariable int pageSize) {
        return new ResponseEntity(new SuccessResponse(service.getAllRatingOfOneProduct(productId, pageIndex, pageSize)), HttpStatus.OK);
    }

    @PostMapping("/add-rating")
    public ResponseEntity createRating(@RequestBody RatingRequestDTO request) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        service.createRating(username, request);
        return new ResponseEntity(new SuccessResponse("Add success"), HttpStatus.OK);
    }

    @PutMapping("/edit-rating")
    public ResponseEntity editRating(@RequestBody RatingRequestDTO request) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        service.editRating(username, request);
        return new ResponseEntity(new SuccessResponse("Edit success"), HttpStatus.OK);
    }

    @DeleteMapping("/delete-rating/{id}")
    public ResponseEntity editRating(@PathVariable long id) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        service.deleteRating(username, id);
        return new ResponseEntity(new SuccessResponse("Delete success"), HttpStatus.OK);
    }
}
