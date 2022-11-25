import java.util.*;

enum EngineType{
    ELECTRIC,
    PETROL,
    DIESEL
}

enum VehicleType{
    BUS,
    CAR,
    VAN,
    BIKE,
    SCOOTY
}

enum userType{
    User,
    Admin
}

class Vehicle{
    private VehicleType vehicleType;
    private EngineType engineType;
    private int price;
    private String vehicleId;

    public Vehicle(){}

    public Vehicle(VehicleType v,EngineType E,int price,String id){
        this.engineType=E;
        this.vehicleType=v;
        this.price=price;
        this.vehicleId=id;
    }
}

class Branch{
    private String name;
    private List<VehicleType> vehicleEntry;
    private List<Vehicle> vehicle;
    private Vector<Vector<Integer>> vehicleBooking;

}

class RentalSystem{
    private String Name;
    private List<User> Users;
    private List<Branch> Branches;

    public RentalSystem(){}

    public RentalSystem(String name){
        this.Name = name;
        Users = new ArrayList<User>();
        Branches = new ArrayList<Branch>();
    }

    // Methods
    public void UpdateBranch(Branch L1,Vehicle V1){
        
    }

    public void AddBranch(Branch B1){

    }
}

class Slot{

}

class User{
    private String userName;
    private userType user;
    private String email;
    private String contact;
    private String password;
    public User(){}

    public User(String name,userType user,String Email,String number){
        this.userName = name;
        this.user= user;
        this.email = Email;
        this.contact = number;    
    }

    public void setPassword(String pass){
        this.password = pass;
    }
    //reset
}

class Admin extends User{
    private RentalSystem R1;

    public void AddVehicle(Branch L1,Vehicle V1){
        R1.UpdateBranch(L1, V1);
    }

    public void AddBranch(Branch B1){
        R1.AddBranch(B1);
    }
}

class Member extends User{

    public void Book(){

    }

    public List<Vehicle> DisplayVehicle(Branch L1, Slot T){

        List<Vehicle> search = new ArrayList<Vehicle>();
        return search;
    }
}



public class vehicleRentalSystem{
    public static void mai(String [] args){
        
    }
}