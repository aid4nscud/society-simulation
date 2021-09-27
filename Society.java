import java.util.Random;

enum SimulationModes {
    Society,
    Anarchy
}

class SocietyTester {
    public static void main(String args[]){
        int testRuns = Integer.parseInt(args[1]);
        int totalDays = 0;
        SimulationModes mode;
        System.out.println(args[0]);
        if(args.length < 3){
            if(args[0].equals("Society")){
                mode = SimulationModes.Society;
            }
            else if(args[0].equals("Anarchy")){
                mode = SimulationModes.Anarchy;
            }
            else {
                 System.out.println("Error: unable to parse input\nPlease try again using the arguments <mode> <testRuns>\nAvailable modes are 'Society' and 'Anarchy'.\n");
                return;
            }
        }
        else{
            System.out.println("Error: unable to parse input\nPlease try again using the arguments <mode> <testRuns>\nAvailable modes are 'Society' and 'Anarchy'.\n");
            return;
        }
        int successes = 0;

        for(int i = 0; i<testRuns; i++){

            int simLength = SocietySimulation.runSimulation(mode);
            if(simLength >= 365){
                
                successes++;
                
            }
            totalDays += simLength;
        }
        double avgDays = totalDays/testRuns;
        double successRate = successes/testRuns;
        System.out.println("This society succeeded " + successes + " times out of " + testRuns + " simulations.\nThe average number of days survived was " + avgDays);
    }
}
class SocietySimulation {
    

    public static int runSimulation(SimulationModes mode){
        
        Person doctor = new Person(PeopleType.DOCTOR);
        Person farmer = new Person(PeopleType.FARMER);
        Person carpenter = new Person(PeopleType.CARPENTER);
        Person hunter = new Person(PeopleType.HUNTER);
        Person[] people = new Person[]{doctor, farmer, carpenter, hunter};
        int days = 1;
        Random random = new Random();

        while(days <= 365 && (doctor.isAlive() || farmer.isAlive() || carpenter.isAlive() || hunter.isAlive())){
            //Apply Skills

             //Doctor
            if(doctor.isAlive()){
                //Anarchy Mode
                if(mode == SimulationModes.Anarchy){
                    if(doctor.getFood() == 1){
                    doctor.setFood(1);
                }
                else{
                    doctor.setHealth(2);
                    }

                }
                //Society Mode
                else {
                      if(doctor.getFood() == 1){
                    doctor.setFood(1);
                }
                else{
                    for(Person p : people){
                        if(p.isAlive()){
                            p.setHealth(2);
                        }
                    }
                }

                }
                
              


            }
            //Farmer
            if(farmer.isAlive()){
                //Anarchy Mode
                if(mode == SimulationModes.Anarchy){
                    if(days % 3 == 0){
                        farmer.setFood(3);
                    }

                }
                //Society Mode
                else {
                     if(days % 3 == 0){
                    for(Person p : people){
                        if(p.isAlive()){
                            p.setFood(3);
                        }
                    }
                }

                }
               
            }
            //Carpenter
            if(carpenter.isAlive()){
                //Anarchy Mode
                if(mode == SimulationModes.Anarchy){
                    if(carpenter.getFood() == 1){
                        carpenter.setFood(1);
                    }
                    else{
                        carpenter.setShelter(2);
                    }
                }
                //Society Mode
                else {
                     if(carpenter.getFood() == 1){
                    carpenter.setFood(1);
                }
                else{
                    for(Person p : people){
                        if(p.isAlive()){
                            p.setShelter(1);
                        }
                    }
                }

                }
               

            }
            //hunter
            if(hunter.isAlive()){
                int val = random.nextInt(5);
                boolean meatFound = val == 0;
                if(meatFound){
                    //Anarchy Mode
                    if(mode == SimulationModes.Anarchy){
                        hunter.setFood(2);

                    }
                    else {
                        for(Person p : people){
                        if(p.isAlive()){
                            p.setFood(2);
                        } 
                    }
                        }
                    //Society Mode
                    
                }
            }
               
        //decrement food attribute
        for(Person p : people){
            if(p.isAlive()){
                p.setFood(-1);
            }
        }

        //disaster
        int disasterVal = random.nextInt(5);
        boolean disaster = disasterVal != 0;
        if(disaster){
            switch(disasterVal){
                //hurricane
                case 1:
                for(Person p : people){
                    if(p.isAlive()){
                        if(p.getShelter() == 0){
                            p.setHealth(-5);
                        }
                        p.setShelter(-3);
                    }
                }
                break;
                //famine
                case 2:
                for(Person p : people){
                    if(p.isAlive()){
                        p.setFood(-2);
                    }
                }
                break;
                //disease
                case 3:
                for(Person p : people){
                    if(p.isAlive()){
                        p.setHealth(-2);
                    }
                }
                break;
                //wolves
                case 4:
                if(mode == SimulationModes.Anarchy){
                    hunter.setHealth(-1);
                    for(Person p : people){
                        if(p.isAlive() && p.getName() != PeopleType.HUNTER){
                            p.setHealth(-3);
                        }
                    }
                }
                else {
                    if(hunter.getHealth() != 0){
                    for(Person p : people){
                        if(p.isAlive() && p.getName() != PeopleType.HUNTER){
                            p.setHealth(-1);
                        }
                    }

                }
                else{
                    for(Person p : people){
                        if(p.isAlive() && p.getName() != PeopleType.HUNTER){
                            p.setHealth(-3);
                        }
                    }

                }

                }
                
                hunter.setHealth(-1);
                
            }
        }

        //display on screen
        
        System.out.println("********************");
        System.out.println("Day " + days + "\n");
        switch(disasterVal){
            case 1:
            System.out.println("Disaster: Hurricane");
            break;
            case 2:
            System.out.println("Disaster: Famine");
            break;
            case 3:
            System.out.println("Disaster: Disease");
            break;
            case 4:
            System.out.println("Disaster: Wolves");
            break;
            default:
            System.out.println("No Disaster!");

            
        }
        System.out.println("---------------------");

        for(Person p : people){
            if(p.isAlive()){
                PeopleType type = p.getName();
                String name = "";
                switch(type){
                    case DOCTOR:
                        name = "Doctor";
                        break;
                    case FARMER:
                        name = "Farmer";
                        break;
                    case CARPENTER:
                        name = "Carpenter";
                        break;
                    case HUNTER:
                        name = "Hunter";
                }

                System.out.print(name + ": "+ p.displayStatus() + "\n");
            }
        }
        
        days++;

        }

    String modeString = mode == SimulationModes.Anarchy ? "Anarchy" : "Society";

    if(days > 365){
        System.out.println("Congratulations, this society survived the simulation in "+ modeString + " mode!");
        return days;
    }
    else {
        System.out.println("Oh no! This society failed after " + days + " days :(");
        return days;
    }

       

    }

}

enum PeopleType {
  DOCTOR,
  FARMER,
  CARPENTER,
  HUNTER
}

class Person {
    PeopleType name;
    int food;
    int health;
    int shelter;
    
    public Person(PeopleType _name){
        this.name = _name;
        food = health = shelter = 10;
    }
    public boolean isAlive(){
        return this.food > 0 && this.health > 0;
    }

    public PeopleType getName(){
        return this.name;
    }
    public int getFood(){
        return this.food;
    }
    public int getHealth(){
        return this.health;
    }
    public int getShelter(){
        return this.shelter;
    }
    public void setFood(int inc){
        if(this.food + inc < 0){
            this.food = 0;
        }
        else{
            this.food = this.food + inc;
        }
    }
    public void setHealth(int inc){
         if(this.health + inc < 0){
            this.health = 0;
        }
         else if(this.health + inc > 10){
            this.health = 10;
        }
        else{
            this.health = this.health + inc;
        }
    }
    public void setShelter(int inc){
         if(this.shelter + inc < 0){
            this.shelter = 0;
        }
        else if(this.shelter + inc > 10){
            this.shelter = 10;
        }
        else{
            this.shelter = this.shelter + inc;
        }
    }

    public String displayStatus(){
        String food = String.valueOf(this.food);
        String health = String.valueOf(this.health);
        String shelter = String.valueOf(this.shelter);
        String status = food + ", " + health + ", " + shelter;
        return status;
        }

}