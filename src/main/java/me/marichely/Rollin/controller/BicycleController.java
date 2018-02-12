package me.marichely.Rollin.controller;

import me.marichely.Rollin.entity.Bicycle;
import me.marichely.Rollin.entity.BicyclePicture;
import me.marichely.Rollin.entity.Location;
import me.marichely.Rollin.exceptions.RestException;
import me.marichely.Rollin.model.BicycleWithPictures;
import me.marichely.Rollin.repository.BicyclePictureRepository;
import me.marichely.Rollin.repository.BicycleRepository;
import me.marichely.Rollin.repository.CategoryRepository;
import me.marichely.Rollin.repository.LocationRepository;
import me.marichely.Rollin.services.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import org.apache.commons.io.IOUtils;

@Controller
@RequestMapping("/bicycle")
public class BicycleController {

    private BicyclePictureRepository bicyclePictureRepository;
    private BicycleRepository bicycleRepository;
    private CategoryRepository categoryRepository;
    private LoginService loginService;
    private LocationRepository locationRepository;
    @Value("${stjepan.desktop.slike}")
    private String filePath;

    @Autowired
    BicycleController(BicyclePictureRepository bicyclePictureRepository, BicycleRepository bicycleRepository, CategoryRepository categoryRepository, LoginService loginService, LocationRepository locationRepository) {
        this.bicyclePictureRepository = bicyclePictureRepository;
        this.bicycleRepository = bicycleRepository;
        this.categoryRepository = categoryRepository;
        this.loginService = loginService;
        this.locationRepository = locationRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    List<Bicycle> getAllBicycles(@RequestHeader(value = "UserApiKey") String userApiKey) {
        loginService.userCheck(userApiKey);
        List<Bicycle> bicycles = bicycleRepository.findAll();
        if (bicycles == null) throw new RestException(HttpStatus.NOT_FOUND, "Bicycle repositoy error contact admin");
        if (bicycles.isEmpty()) throw new RestException(HttpStatus.NOT_FOUND, "Empty Bicycle repositoy");

        return bicycles;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    @CrossOrigin
    public @ResponseBody
    Bicycle getByID(@RequestHeader(value = "UserApiKey") String userApiKey, @PathVariable String id) {
        loginService.userCheck(userApiKey);
        if (!StringUtils.isNumeric(id)) {
            throw new RestException(HttpStatus.FORBIDDEN, "Id needs to be a number");
        }
        Integer ids = Integer.parseInt(id);
        Bicycle bicycle = bicycleRepository.findOne(ids);
        return bicycle;
    }

    @GetMapping("/cat{category}")
    @CrossOrigin
    public @ResponseBody
    List<Bicycle> getByCategory(@RequestHeader(value = "UserApiKey") String userApiKey, @PathVariable String category) {
        loginService.userCheck(userApiKey);
        List<Bicycle> bicycles = bicycleRepository.findByCategory(categoryRepository.findOne(Integer.parseInt(category)));
        if (bicycles == null)
            throw new RestException(HttpStatus.NOT_FOUND, "Bicycle/Category Repository error contact admin");
        if (bicycles.isEmpty())
            throw new RestException(HttpStatus.NOT_FOUND, "Empty Bicycle repositoy for the provided category" + category);
        return bicycles;
    }

    @PostMapping("/update")
    @CrossOrigin
    public void updateBicycleStatus(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestParam("bicycleid") String bicycleID, @RequestParam("status") String status, HttpServletResponse httpServletResponse) {
        loginService.userCheck(userApiKey);
        if (!StringUtils.isNumeric(status))
            throw new RestException(HttpStatus.BAD_REQUEST, "Status must be a number (0, 1 or 2)");
        if (!StringUtils.isNumeric(bicycleID))
            throw new RestException(HttpStatus.BAD_REQUEST, "bicycleid must be a number");
        Integer statusint = Integer.parseInt(status);
        if (statusint > 2) {
            throw new RestException(HttpStatus.FORBIDDEN, "Status must be (0 ,1 or 2) and no other numeric value");
        }
        Bicycle bicycle = bicycleRepository.findOne(Integer.parseInt(bicycleID));
        if (bicycle == null)
            throw new RestException(HttpStatus.NOT_FOUND, "Couldn't find the bicycle for the provided ID" + bicycleID);
        bicycle.setState(statusint);
        bicycleRepository.save(bicycle);
        httpServletResponse.setStatus(200);
    }

    @GetMapping("/location")
    @CrossOrigin
    public @ResponseBody
    List<Location> getBicycleLocations(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestParam("bicycleid") String bicycleID) {
        loginService.userCheck(userApiKey);
        if (!StringUtils.isNumeric(bicycleID))
            throw new RestException(HttpStatus.BAD_REQUEST, "bicycleid must be a number");
        Bicycle bicycle = bicycleRepository.findOne(Integer.parseInt(bicycleID));
        if (bicycle == null)
            throw new RestException(HttpStatus.NOT_FOUND, "Couldn't find the bicycle for the provided ID" + bicycleID);
        List<Location> location = locationRepository.findByBicycle(bicycle);
        return location;
    }

    @PostMapping("/location")
    @CrossOrigin
    public void postBicycleLocation(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestBody Location location, HttpServletResponse httpServletResponse) {
        loginService.userCheck(userApiKey);
        if (location == null && location.getBicycle() == null && location.getBicycle().getBicycleID() == null)
            throw new RestException(HttpStatus.BAD_REQUEST, "location object is invalid");
        Integer bicycleID = location.getBicycle().getBicycleID();
        Bicycle bicycle = bicycleRepository.findOne(bicycleID);
        if (bicycle == null)
            throw new RestException(HttpStatus.NOT_FOUND, "Couldn't find the bicycle for the provided ID" + bicycleID);
        locationRepository.save(location);
        httpServletResponse.setStatus(200);
    }

    @PostMapping("/picture")
    @CrossOrigin
    public @ResponseBody
    String uploadpicture(@RequestParam("slika") MultipartFile multipartFile, @RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader("bicycleid") String bicycleID) {
        String id = UUID.randomUUID().toString();
        String name = id + "_" + multipartFile.getOriginalFilename();
        try {
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(filePath + name);
            Files.write(path, bytes);
            Bicycle bicycle = findBycicleWithId(bicycleID);
            BicyclePicture bicyclePicture = new BicyclePicture();
            bicyclePicture.setPicture_path(path.toString());
            bicyclePicture.setBicycle(bicycle);
            bicyclePicture.setCreated(new Date());
            bicyclePicture.setBicycleID(id);
            bicycle.getBicyclePicture().add(bicyclePicture);
            bicycleRepository.save(bicycle);
            return "Bicycle picture can be found under ID " + id;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failure";
    }

    @RequestMapping(value = "/picture/latest", method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    byte[] getLatestImageforBicycleID(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader("bicycleid") String bicycleID) throws IOException {
        loginService.userCheck(userApiKey);
        Bicycle bicycle = findBycicleWithId(bicycleID);
        List<BicyclePicture> bicyclePictures = bicycle.getBicyclePicture();
        Date date = null;
        BicyclePicture latest= null;
        for (BicyclePicture bicyclePicture : bicyclePictures) {
            if (date == null) {
                date = bicyclePicture.getCreated();
                latest = bicyclePicture;
            }
            if(bicyclePicture.getCreated().after(date)){
                latest = bicyclePicture;
            }
        }
        if(latest == null) throw new RestException(HttpStatus.BAD_REQUEST,"No images found for bicycle");
        String path = latest.getPicture_path();
        FileInputStream in = new FileInputStream(path);
        return IOUtils.toByteArray(in);
    }

    @RequestMapping(value = "/picture/id", method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    byte[] getImageFromImageID(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader("imageid") String imageID) throws IOException {
        loginService.userCheck(userApiKey);
        BicyclePicture bicyclePicture = findBicyclePictureFromID(imageID);
        String path = bicyclePicture.getPicture_path();
        FileInputStream in = new FileInputStream(path);
        return IOUtils.toByteArray(in);
    }

    @RequestMapping(value = "/picture/id", method = RequestMethod.DELETE)
    @CrossOrigin
    public @ResponseBody
    String deleteImageWithImageID(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader("imageid") String imageID) throws IOException {
        loginService.userCheck(userApiKey);
        BicyclePicture bicyclePicture = findBicyclePictureFromID(imageID);
        if (bicyclePicture == null) throw new RestException(HttpStatus.BAD_REQUEST, "No image found for given id");
        Path path = Paths.get(bicyclePicture.getPicture_path());
        Files.deleteIfExists(path);
        bicyclePictureRepository.delete(bicyclePicture.getBicycleID());
        return "image deleted successfully";
    }

    @RequestMapping(value = "/picture/id/all", method = RequestMethod.GET)
    @CrossOrigin
    public @ResponseBody
    BicycleWithPictures getAllImageIdsForBicycle(@RequestHeader(value = "UserApiKey") String userApiKey, @RequestHeader("bicycleid") String bicycleID) {
        loginService.userCheck(userApiKey);
        Bicycle bicycle = findBycicleWithId(bicycleID);
        List<String> pictureIds = new ArrayList<>();
        for (BicyclePicture bicyclePicture : bicycle.getBicyclePicture()) {
            pictureIds.add(bicyclePicture.getBicycleID());
        }
        return new BicycleWithPictures(bicycle, pictureIds);
    }

    private BicyclePicture findBicyclePictureFromID(String id) {
        return bicyclePictureRepository.findOne(id);
    }

    private Bicycle findBycicleWithId(String id) {
        if (!StringUtils.isNumeric(id)) {
            throw new RestException(HttpStatus.FORBIDDEN, "Id needs to be a number");
        }
        Integer ids = Integer.parseInt(id);
        Bicycle bicycle = bicycleRepository.findOne(ids);
        return bicycle;
    }


}
