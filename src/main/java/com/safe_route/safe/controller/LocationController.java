package com.safe_route.safe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;  
import org.json.simple.parser.JSONParser; 
import org.json.simple.parser.ParseException;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import com.safe_route.safe.dto.CCTVPosDTO;
import com.safe_route.safe.dto.ResponseDTO;
import com.safe_route.safe.model.CctvPosModel;
import com.safe_route.safe.service.LocationService;
import com.safe_route.safe.service.Routing;


@RestController
@RequestMapping("safe")
public class LocationController {

    private final static String APPKEY = "l7xx47ffd778fcc54f49baa6e2ea37859c5d";
    @Autowired
    private LocationService service;

    @GetMapping("/cctv")
    public ResponseEntity<?> locationController(
        @RequestParam(required = true) Double srcLati,
        @RequestParam(required = true) Double srcLong,
        @RequestParam(required = true) Double dstLati,
        @RequestParam(required = true) Double dstLong
    ){
        JSONObject res = new JSONObject();
        String noLocal = new String();
        Routing routing = new Routing();
        noLocal = "nope";

        CctvPosModel src = CCTVPosDTO.toEntity(srcLati, srcLong);
        CctvPosModel dst = CCTVPosDTO.toEntity(dstLati, dstLong);

		List<CctvPosModel> entities = service.findCCTV(src, dst);

		List<CCTVPosDTO> dtos = entities.stream().map(CCTVPosDTO::new).collect(Collectors.toList());

		ResponseDTO<CCTVPosDTO> response = ResponseDTO.<CCTVPosDTO>builder().
                                            total(entities.size()).
                                            data(dtos).
                                            build();
        
		return ResponseEntity.ok().body(response);
    }

    @GetMapping("/routing")
    public String defaultRouting(
        @RequestParam(required = true) String srcLati,
        @RequestParam(required = true) String srcLongti,
        @RequestParam(required = true) String dstLati,
        @RequestParam(required = true) String dstLongti
    ){
        Routing routing = new Routing();

        return routing.getRoutePoint(srcLati, srcLongti, dstLati, dstLongti,"");
    }
}
