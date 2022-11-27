package oit.is.jinro.jinrogame.controller;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.jinro.jinrogame.model.UserMapper;
import oit.is.jinro.jinrogame.model.Users;
import oit.is.jinro.jinrogame.service.AsyncJinroVote;
import oit.is.jinro.jinrogame.model.Role;
import oit.is.jinro.jinrogame.model.RoleMapper;

import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/vote")
public class JirogameController {
  @Autowired
  UserMapper UMapper;
  @Autowired
  RoleMapper RMapper;
  @Autowired
  AsyncJinroVote JVote;

  @GetMapping("step1")
  public String vote01(ModelMap model) {
    ArrayList<Users> users = JVote.syncShowUsersList();
    model.addAttribute("users", users);
    return "vote.html";
  }
  @GetMapping("step3")
  public String vote03(@RequestParam Integer id, ModelMap model) {
    Users user = JVote.syncKillUsers(id);
    model.addAttribute("user", user);

    ArrayList<Users> usersList = JVote.syncShowUsersList();
    model.addAttribute("users", usersList);
    return "vote.html";
  }

  @GetMapping("step5")
  public SseEmitter vote05() throws IOException {
    final SseEmitter sseEmitter = new SseEmitter();
    this.JVote.asyncShowUsersList(sseEmitter);
    
    return sseEmitter;
  }
}
