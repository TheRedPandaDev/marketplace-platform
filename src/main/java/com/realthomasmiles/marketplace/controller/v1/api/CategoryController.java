package com.realthomasmiles.marketplace.controller.v1.api;

import com.realthomasmiles.marketplace.dto.response.Response;
import com.realthomasmiles.marketplace.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public Response<Object> getAllCategories() {
        return Response
                .ok()
                .setPayload(categoryService.getAllCategories());
    }
}
