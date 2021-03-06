package com.example.accessingdatamysql.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.accessingdatamysql.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import static com.example.accessingdatamysql.GlobalConstants.general_page_size;

// import java.sql.Timestamp;
import java.util.*;

// import javax.validation.constraints.Null;

import com.example.accessingdatamysql.service.MissionService;

@CrossOrigin(origins = "*")
@RestController // This means that this class is a Controller
@RequestMapping(path = "/mission") // This means URL's start with /demo (after Application path)
public class MissionController {
  @Autowired // This means to get the bean called MissionRepository
  // Which is auto-generated by Spring, we will use it to handle the data
  private MissionService MissionService;

  @RequestMapping(value = "/getMission")
  public Mission findMissionByMissionId(@RequestParam("missionId") Integer MissionId) {
    return MissionService.getOneMission(MissionId);
  }

  @RequestMapping(value = "/addMission")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody Mission addNewMission(@RequestBody Mission newMission) {
    return MissionService.addNewMission(newMission);
  }

  @RequestMapping(value = "/updateMission")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public @ResponseBody Mission updateMission(@RequestBody Mission updateMission) {
    return MissionService.updateMission(updateMission);
  }

  // 获取指定页数的数据
  @RequestMapping(value = "/List")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public JSONObject ListPage(@RequestBody ListRequest ListRequest) {
    ListRequest.setPageSize(general_page_size);
    String request = JSON.toJSONString(ListRequest);
    System.out.print(request);
    JSONObject response = MissionService.ListPage(ListRequest);
    return response;
  }

  @RequestMapping(value = "/getAllMissions")
  public List<Mission> getAllMissions() {
    return MissionService.getAllMissions();
  }

  @RequestMapping(value = "/deleteMissions")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteMissions(@RequestParam("missionIds") List<Integer> MissionIds) {
    return MissionService.deleteMissions(MissionIds);
  }

  @RequestMapping(value = "/deleteAllMissions")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public String deleteAll() {
    return MissionService.deleteAll();
  }

  @RequestMapping(value = "/deleteMission")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public List<Mission> deleteMission(@RequestParam("missionId") Integer MissionId) {
    return MissionService.deleteMission(MissionId);
  }
}
