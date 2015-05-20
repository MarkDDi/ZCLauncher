package com.word.wordinsidehome.service.entity;
import java.io.Serializable;
public class IconsEntity implements Serializable {
  private int position;	
  private String action;	
  private String apk;
  private int appID;
  private String appName;
  private String className;
  private String description;
  private String icon;
  private String packageName;
  private String params;
  private int startType;  
  private String version;	
  
    public int get_position() {
        return this.position;
    }	
    public String get_action() {
        return this.action;
    } 
       
    public String get_apk() {
        return this.apk;
    }

    public int get_appID() {
        return this.appID;
    }

    public String get_appName() {
        return this.appName;
    }

    public String get_className() {
        return this.className;
    }

    public String get_description() {
        return this.description;
    }

    public String get_icon() {
        return this.icon;
    }

    public String get_packageName() {
        return this.packageName;
    }

    public String get_params() {
        return this.params;
    }
    
    public int get_startType() {
        return this.startType;
    }
    
    public String get_version() {
        return this.version;
    }
    
    public void set_position(int position) {
         this.position = position;
    }

    public void set_action(String action) {
         this.action = action;
    }
    public void set_apk(String apk) {
         this.apk = apk;
    }

    public void set_appID(int appID) {
         this.appID = appID;
    }

    public void set_appName(String appName) {
         this.appName = appName;
    }

    public void set_className(String className) {
         this.className = className;
    }

    public void set_description(String description) {
         this.description = description;
    }

    public void set_icon(String icon) {
         this.icon = icon;
    }

    public void set_packageName(String packageName) {
         this.packageName = packageName;
    }

    public void set_params(String params) {
         this.params = params;
    }
    
    public void set_version(String version) {
         this.version = version;
    }
    
    public void set_startType(int startType) {
         this.startType = startType;
    }
    
    public String toString() {
         return this.position + "action = " +this.action + " " + this.apk + " " + this.appID+ " " + this.appName + " " + this.className + " " + this.description + " " + this.description + " " + this.icon + " " + this.packageName + " " + this.params + " " + this.startType + " " + this.version;
    }	
	
}