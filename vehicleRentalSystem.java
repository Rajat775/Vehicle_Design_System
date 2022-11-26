import java.util.*;

// enum EngineType{
//     ELECTRIC,
//     PETROL,
//     DIESEL
// }

enum VehicleType{
    BUS,
    CAR,
    VAN,
    BIKE,
    SCOOTY
}

// enum userType{
//     User,
//     Admin
// }

class Vehicle{
    private VehicleType vehicleType;
    // private EngineType engineType;
    private int price;
    private String vehicleId;

    public Vehicle(){}


    //constructor for vehicle class
    public Vehicle(VehicleType v,int price,String id){
        this.vehicleType=v;
        this.price=price;
        this.vehicleId=id;
    }

    //getter 
    public String getName(){
        return this.vehicleId;
    }

    public String getVehicleType(){
        if(this.vehicleType == VehicleType.VAN)
            return "VAN";
        if(this.vehicleType == VehicleType.CAR)
            return "CAR";
        if(this.vehicleType == VehicleType.SCOOTY)
            return "SCOOTY";
        if(this.vehicleType == VehicleType.BUS)
            return "BUS";
        if(this.vehicleType == VehicleType.BIKE)
            return "BIKE";
        return "";    
    }

    public Integer getPrice(){
        return this.price;
    }
}

class Branch{
    private String name;
    private Set<VehicleType> supportingVehicle;
    private List<Vehicle> vehicles;
    private Vector<Vector<Integer>> vehicleBooking;
    // private RentalSystem R1;

    public Branch(){}

    public String getName(){
        return this.name;
    }

    //getter
    public Set<VehicleType> getVehicleType(){
        return this.supportingVehicle;
    }

    //constructor
    public Branch(String name,Set<VehicleType> V){
        this.name = name;
        this.supportingVehicle = V;
        this.vehicleBooking = new Vector<Vector<Integer>>();
        this.vehicles = new Vector<Vehicle>();
    }

    // function to check vehicle type
    public Boolean checkVehicleType(VehicleType type){
        // for(VehicleType v : this.getVehicleType())
        //     if(v == type)
        //         return true;
        // return false;
        return this.getVehicleType().contains(type);
    }

    //function to add vehicle in branch
    public void addVehicle(VehicleType type, String name, int price){
        Vehicle vehicle = new Vehicle(type,price,name);
        this.vehicles.add(vehicle);
        Vector<Integer>t = new Vector<Integer>(24);
        for(int i=0;i<24;i++)
        t.add(-1);
        this.vehicleBooking.add(t);
    }


    //function to search vehicle while booking availability display details
    public void searchVehicle(int startTime,int EndTime){
        Vector<Vector<Integer>>temp = this.vehicleBooking;
        if(temp.size()==0)
        {
            System.out.println("No vehicle yet added.\n");
            return;
        }
        for(int i=0;i<temp.size();i++)
        {   int count = 0; 
            for(int j=startTime-1;j<EndTime-1;j++)
            {
                if(temp.get(i).get(j)==-1)
                {
                    count++;
                }
            }
            if(count==EndTime-startTime)
            {
                System.out.println(this.vehicles.get(i).getName()+" "+this.vehicles.get(i).getVehicleType()+" "
                +this.vehicles.get(i).getPrice());
            }
        }
    }

    // capacity of branch
    private Integer checkCapacity(Vector<Vector<Integer>> temp){
        int countBooked=0,countUnBooked=0;
        for(int i=0;i<temp.size();i++)
        {
            for(int j=0;j<24;j++)
            {
                if(temp.get(i).get(j)==1)
                    countBooked++;
                if(temp.get(i).get(j)==-1) 
                    countUnBooked++;   
            }
        }
        return (countBooked*100)/(countBooked+countUnBooked);
    }


    // booking a given time line
    public Integer tryBooking(VehicleType vehicleType, int starttime, int endtime){

        int price = 10000;
        int index = -1;
        Vector<Integer> temp;
        for(int i=0;i<this.vehicles.size();i++)
        {   
            boolean flag = true;
            if(this.vehicles.get(i).getVehicleType() == vehicleType.toString()){
                temp = this.vehicleBooking.get(i);
                for(int j=starttime-1;j<endtime-1;j++)
                    if(temp.get(j)!=-1)
                        flag=false;
                // first encounter
                if(flag && this.vehicles.get(i).getPrice() < price)
                {
                    price = this.vehicles.get(i).getPrice();
                    index= i;
                }
            }
        }

        if(index==-1)
            return -1;
        temp = this.vehicleBooking.get(index);  // selecting minimum price vehicle
        for(int j=starttime-1;j<endtime-1;j++)
            temp.set(j,1);
        this.vehicleBooking.set(index,temp);

        // point 6 price spike based on capacity
        int capacity = checkCapacity(this.vehicleBooking);
        if(capacity>=80)
        return (this.vehicles.get(index).getPrice()*11*(endtime-starttime))/10;
        else 
        return this.vehicles.get(index).getPrice()*(endtime-starttime);
    }}

class RentalSystem{
    private String Name;
    // private List<User> Users;
    private List<Branch> Branches;

    public RentalSystem(){}

    public RentalSystem(String name){
        this.Name = name;
        // Users = new ArrayList<User>();
        Branches = new ArrayList<Branch>();
        System.out.println("Vehicle Rental System Created For "+this.Name+"\n");
    }


    // checking whether branch exists or not
    private boolean branchExists(String branchName){
        for(Branch b: Branches){
            if(b.getName().equalsIgnoreCase(branchName)){
                return true;
            }
        }
        return false;
    }

    // getting branch
    public Branch getBranch(String branchName){
        // scanning through list of branches
        for(Branch b: Branches){
            if(b.getName().equalsIgnoreCase(branchName)){
                return b;
            }}
        return null;
    }

    // adding branch
    public Boolean AddBranch(Branch branch){
        if(branchExists(branch.getName()))
            return false;
        Branches.add(branch);
        return true;
    }
    
    //call add vehicle in branch after checking branch exists
    public Boolean addVehicle(String branchName, VehicleType type, String vehicleName, int price){
        if(!branchExists(branchName) ) {
            return false;}
        Branch b1 = getBranch(branchName);
        if(!b1.checkVehicleType(type)) {  
            return false;
        }
        b1.addVehicle(type,vehicleName,price);
        return true;
    }

    // calling search in branch after validating branch
    public void displayVehicle(String branchName,int startTime,int EndTime){
        if(!branchExists(branchName) ) {
            System.out.println("Branch Does Not Exists");
            return;
        }
        Branch b1 = getBranch(branchName);

        b1.searchVehicle(startTime,EndTime);
    }
    
    // attempting booking 
    public Integer tryBooking(String branchName, VehicleType vehicleType, int starttime, int endtime){
        if(!branchExists(branchName)) return -1;
        Branch b1 = getBranch(branchName);
        return b1.tryBooking(vehicleType,starttime,endtime);
    }}

class vehicleRentalSystem{
    public static void main(String [] args){
        RentalSystem rentalSystem = new RentalSystem("Navi Travels");
        int f,branchVehicleType,vehiclePrice,startTime,EndTime;
        String name,vehicleType,vehicleName;
        VehicleType v;
        try (Scanner sc = new Scanner(System.in)) { // counter measure for file input 
            while(true)
            {
                System.out.println("1.Add Branch 2.Add Vehicle 3.Book 4.Display Vehicle 5.Exit\n");// INPUT 1 2 3 4
                f= sc.nextInt();
                sc.nextLine();
                if(f==1){
                    System.out.println("Enter Branch Name\n");
                    name = sc.nextLine();
                    Set<VehicleType>st = new HashSet<VehicleType>();
                    System.out.println("How Many Types of Vehicle in Branch\n"); // No. of support Vehicles
                    branchVehicleType=sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter Types of Vehicle[BUS,CAR,VAN,BIKE,SCOOTY]\n"); // input vehicles types
                    while(branchVehicleType>0)
                    {   
                        vehicleType=sc.nextLine();
                        if(vehicleType.equalsIgnoreCase("BUS")){
                            st.add(VehicleType.BUS);
                        }else if(vehicleType.equalsIgnoreCase("CAR")){
                            st.add(VehicleType.CAR);
                        }
                        else if(vehicleType.equalsIgnoreCase("VAN")){
                            st.add(VehicleType.VAN);
                        }  
                        else if(vehicleType.equalsIgnoreCase("BIKE")){
                            st.add(VehicleType.BIKE);
                        }  
                        else if(vehicleType.equalsIgnoreCase("SCOOTY")){
                            st.add(VehicleType.SCOOTY);
                        }
                        else{
                            System.out.println("Wrong Type");
                            branchVehicleType++;
                        }    
                        branchVehicleType--;
                    }
                    Branch b1 = new Branch(name,st);
                    if(rentalSystem.AddBranch(b1))  // calling add branch
                    {
                        System.out.println("TRUE");
                    }else{
                        System.out.println("FALSE");
                    }

                }else if(f==2){
                    System.out.println("Enter Branch Name:");
                    name = sc.nextLine();
                    System.out.println("Enter Types of Vehicle[BUS,CAR,VAN,BIKE,SCOOTY]:"); // Add vehicle section
                    vehicleType=sc.nextLine();
                    if(vehicleType.equalsIgnoreCase("BUS")){
                        v = VehicleType.BUS;
                    }else if(vehicleType.equalsIgnoreCase("CAR")){
                        v = VehicleType.CAR;
                    }
                    else if(vehicleType.equalsIgnoreCase("VAN")){
                        v = VehicleType.VAN;
                    }  
                    else if(vehicleType.equalsIgnoreCase("BIKE")){
                        v = VehicleType.BIKE;
                    }  
                    else{
                        v = VehicleType.SCOOTY;
                    }
                    System.out.println("Enter Vehicle Name");
                    vehicleName = sc.nextLine();
                    System.out.println("Enter vehicle price");
                    vehiclePrice = sc.nextInt();
                    sc.nextLine();
                    if(rentalSystem.addVehicle(name, v, vehicleName, vehiclePrice)){ // calling add vehicle
                        System.out.println("TRUE");
                    }else{
                        System.out.println("FALSE");
                    }
                }else if(f==3){ // Booking segment
                    System.out.println("Enter Branch Name");
                    name = sc.nextLine();
                    System.out.println("Enter Types of Vehicle[BUS,CAR,VAN,BIKE,SCOOTY]");
                    vehicleType=sc.nextLine();
                    if(vehicleType.equalsIgnoreCase("BUS")){
                        v = VehicleType.BUS;
                    }else if(vehicleType.equalsIgnoreCase("CAR")){
                        v = VehicleType.CAR;
                    }
                    else if(vehicleType.equalsIgnoreCase("VAN")){
                        v = VehicleType.VAN;
                    }  
                    else if(vehicleType.equalsIgnoreCase("BIKE")){
                        v = VehicleType.BIKE;
                    }  
                    else{
                        v = VehicleType.SCOOTY;
                    }
                    System.out.println("Enter Start Time[1 to 23]");
                    startTime= sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter End Time [2 to 24]");
                    EndTime = sc.nextInt();
                    sc.nextLine();
                    System.out.println(rentalSystem.tryBooking(name,v,startTime,EndTime));
                }else if(f==4){ // branch display
                    System.out.println("Enter Branch Name");
                    name = sc.nextLine();
                    System.out.println("Enter Start Time[1 to 23]");
                    startTime= sc.nextInt();
                    sc.nextLine();
                    System.out.println("Enter End Time [2 to 24]");
                    EndTime = sc.nextInt();
                    sc.nextLine();
                    rentalSystem.displayVehicle(name,startTime,EndTime);
                }else{
                    break;
                }
            }
        }
    }}
