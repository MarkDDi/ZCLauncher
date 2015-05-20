package com.word.wordinsidehome.service.entity;
import java.io.Serializable;
public class LauncherEntity implements Serializable {
  private String animVersion;	
  private String animation;
  private String apk;
  private String description;
  private String icon;
  private int launcherID;
  private int online;
  private String onlineMsg;
  private String updateDate; 
  private String version;	
  

    public String get_animVersion() {
        return this.animVersion;
    } 

    public String get_animation() {
        return this.animation;
    }       
    public String get_apk() {
        return this.apk;
    }

    public String get_description() {
        return this.description;
    }
    
    public String get_icon() {
        return this.icon;
    }
    public int get_launcherID() {
        return this.launcherID;
    }

    public int get_online() {
        return this.online;
    }

    public String get_onlineMsg() {
        return this.onlineMsg;
    }
    
    public String get_updateDate() {
        return this.updateDate;
    }
    
    public String get_version() {
        return this.version;
    }
    
    public void set_animVersion(String animVersion) {
         this.animVersion = animVersion;
    } 

    public void set_animation(String animation) {
         this.animation = animation;
    }       
    public void set_apk(String apk) {
         this.apk = apk;
    }

    public void set_description(String description) {
         this.description = description;
    }
    
    public void set_icon(String icon) {
         this.icon = icon;
    }
    public void set_launcherID(int launcherID) {
         this.launcherID = launcherID;
    }

    public void set_online(int online) {
         this.online = online;
    }

    public void set_onlineMsg(String onlineMsg) {
         this.onlineMsg = onlineMsg ;
    }
    
    public void set_updateDate(String updateDate) {
         this.updateDate = updateDate;
    }
    
    public void set_version(String version) {
         this.version = version;
    }
    
    public String toString() {
         return this.animVersion + "animation = " +this.animation + " " + this.apk + " " + this.description+ " " + this.icon + " " + this.launcherID + " " + this.online + " " + this.onlineMsg + " " + this.updateDate + " "  + this.version;
    }	
	
}