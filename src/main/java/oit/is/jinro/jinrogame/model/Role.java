package oit.is.jinro.jinrogame.model;

public class Role {
  int id;
  public String roleName;
  String camp;

  public Role(){

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getRole(){
    return this.roleName;
  }

  public void setRole(String roleName){
    this.roleName = roleName;
  }

  public String getCamp(){
    return this.camp;
  }

  public void setCamp(String camp)  {
    this.camp = camp;
 }
}
