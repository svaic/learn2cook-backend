package com.ukim.finki.learn2cookbackend.controller;

import com.ukim.finki.learn2cookbackend.configuration.jwt.JwtTokenUtil;
import com.ukim.finki.learn2cookbackend.model.Ingredient;
import com.ukim.finki.learn2cookbackend.model.IngredientWithSize;
import com.ukim.finki.learn2cookbackend.model.User;
import com.ukim.finki.learn2cookbackend.model.request.ChangeUserIngredientRequest;
import com.ukim.finki.learn2cookbackend.model.request.LoginRequest;
import com.ukim.finki.learn2cookbackend.service.IngredientsService;
import com.ukim.finki.learn2cookbackend.service.UserService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private IngredientsService ingredientsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

        final User userDetails = (User) userService
                .loadUserByUsername(request.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(userDetails);
    }

    @PostMapping("register")
    public ResponseEntity<User> register(@RequestBody LoginRequest registerRequest) {
        User user = userService.registerUser(registerRequest.getUsername(), registerRequest.getPassword());

        final String token = jwtTokenUtil.generateToken(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(user);
    }

    @GetMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) throws Exception {
        // From the HttpRequest get the claims
        DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

        Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
        String token = jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, token)
                .build();
    }

    public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
        Map<String, Object> expectedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : claims.entrySet()) {
            expectedMap.put(entry.getKey(), entry.getValue());
        }
        return expectedMap;
    }

    @PostMapping("changeIngredients")
    public User changeIngredientState(Authentication authentication, @RequestBody ChangeUserIngredientRequest request) {
        User user = userService.findUser(authentication.getName());
        return userService.changeIngredients(user, request.getIngredient(), request.isAddIngredient());
    }

    @GetMapping("ingredientsSize")
    public List<IngredientWithSize> getAllIngredientsWithSize() {
        return ingredientsService.getAllWithSize();
    }

    @GetMapping("ingredients")
    public List<Ingredient> getAllIngredients() {
        return ingredientsService.getAll();
    }

    @PostMapping("image/receipt")
    public User receiveImage(Authentication authentication, @RequestParam("receiptId") Long receiptId, @RequestParam("image") MultipartFile image) {
        return userService.addReceiptDone(authentication.getName(), receiptId, image);
    }
}
