package com.example.xedd.controller;

import com.example.xedd.dto.ItemRequestDto;
import com.example.xedd.exception.NotFoundException;
import com.example.xedd.model.Item;
import com.example.xedd.model.Post;
import com.example.xedd.repository.ItemRepository;
import com.example.xedd.service.ItemService;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class ImageResource {

    @Autowired
    ItemService itemService;

    //See https://stackoverflow.com/questions/35680932/download-a-file-from-spring-boot-rest-service
    //https://stackoverflow.com/questions/61146092/read-image-from-resources-folder
    //See for use response com.example.xedd.controller.PostController.showImage

    //Return first available image file
    @GetMapping("item/display")
    void showAnyImage(HttpServletResponse response)
            throws ServletException, IOException {
        List<Item> items= itemService.getAllItems();
        if (items.size()==0) throw new NotFoundException();
        //Get the location from first available item
        File file = new File(items.stream().findFirst().get().getLocation());
        Path path = Paths.get(file.getAbsolutePath());
        //ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getOutputStream().write(Files.readAllBytes(path));
        response.getOutputStream().close();
    }

    @GetMapping("item/display/{id}")
    void showImage(HttpServletResponse response, @PathVariable("id") long id)
            throws ServletException, IOException {
        if (!itemService.itemExistsById(id)) throw new NotFoundException();
        Item item = itemService.getItem(id);
        File file = new File(item.getLocation());
        Path path = Paths.get(file.getAbsolutePath());
        //ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.getOutputStream().write(Files.readAllBytes(path));
        response.getOutputStream().close();
    }

}

