package oit.is.jinro.jinrogame.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.jinro.jinrogame.model.UserMapper;
import oit.is.jinro.jinrogame.model.Users;

@Service
public class AsyncJinroVote {

  private final Logger logger = LoggerFactory.getLogger(AsyncJinroVote.class);
  
  @Autowired
  UserMapper uMapper;

  @Transactional
  public Users syncKillUsers(int id) {
    // 削除対象のフルーツを取得
    Users user = uMapper.selectById(id);

    // 削除
    uMapper.deleteById(id);

    // 非同期でDB更新したことを共有する際に利用する

    return user;
  }

  public Users syncShowUsersById(int id){
    Users user = uMapper.selectById(id);
    return user;
  }

  public ArrayList<Users> syncShowUsersList() {
    return uMapper.selectAll();
  }


  @Async
  public void asyncShowUsersList(SseEmitter emitter){
    try {
      while (true) {// 無限ループ

        // DBが更新されていれば更新後のフルーツリストを取得してsendし，1s休み，dbUpdatedをfalseにする
        ArrayList<Users> users = this.syncShowUsersList();
        emitter.send(users);
        TimeUnit.MILLISECONDS.sleep(1000);
      }
    } catch (Exception e) {
      // 例外の名前とメッセージだけ表示する
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
      emitter.complete();
    }
    System.out.println("asyncShowUsersList complete");
  }

}
