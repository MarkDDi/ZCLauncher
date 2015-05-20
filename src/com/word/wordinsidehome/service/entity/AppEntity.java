package com.word.wordinsidehome.service.entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
public class AppEntity extends WordinsideBaseEntity{
	
    private int blockID;
    private ArrayList iconsEntity;
    private String name;
    private boolean online;
    private String onlineMsg;
    private int sortID;

    public int get_blockID() {
        return this.blockID;
    }

    public ArrayList get_iconsEntity() {
        return this.iconsEntity;
    }

    public String get_name() {
        return this.name;
    }	
    public boolean get_online() {
        return this.online;
    }

    public String get_onlineMsg() {
        return this.onlineMsg;
    }		
    public int get_sortID() {
        return this.sortID;
    }	
 
    		
  public void set_blockID(int blockID) {
       this.blockID = blockID;
  }

  public void set_iconsEntity(ArrayList iconsEntity) {
       this.iconsEntity = iconsEntity;
  }

  public void set_name(String name) {
       this.name = name;
  }	
  public void set_online(boolean online) {
       this.online = online;
  }

  public void set_onlineMsg(String onlineMsg) {
       this.onlineMsg = onlineMsg;
  }		
  public void set_sortID(int sortID) {
       this.sortID = sortID;
  }
}