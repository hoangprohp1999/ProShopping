package com.example.project_final_2.controller;

import com.example.project_final_2.dto.reponse.SuccessResponse;
import com.example.project_final_2.dto.reponse.UserResponseDTO;
import com.example.project_final_2.dto.request.UserRequestDTO;
import com.example.project_final_2.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/show-user/{pageIndex}/{pageSize}")
    public ResponseEntity getAllUser(@PathVariable int pageIndex,
                                     @PathVariable int pageSize){
        return ResponseEntity.ok(new SuccessResponse(userService.getAllUser(pageIndex, pageSize)));
    }

    @GetMapping("/search-user")
    public ResponseEntity getUserDTOByID() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return ResponseEntity.ok(new SuccessResponse(userService.getUserDTOByID(username)));
    }

//    @PostMapping("/add-new")
//    public ResponseEntity addNewUser(@Valid @RequestBody UserRequestDTO userRequestDTO) throws Exception {
//        userService.saveNewUser(userRequestDTO);
//        return ResponseEntity.ok(new SuccessResponse("Check email"));
//    }

    @PutMapping("/edit-user")
    public ResponseEntity editUser(@RequestBody UserRequestDTO userRequestDTO) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.editUserInfo(username, userRequestDTO);
        return ResponseEntity.ok(new SuccessResponse("Success"));
    }

    @DeleteMapping("/delete-user/{userID}")
    public ResponseEntity deleteUser(@PathVariable Long userID) {
        userService.deleteUserByID(userID);
        return ResponseEntity.ok(new SuccessResponse("Success"));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity getUserDTOByUsername(@PathVariable String username) {
        return ResponseEntity.ok(new SuccessResponse(userService.getUserDTOByUsername(username)));
    }
}
