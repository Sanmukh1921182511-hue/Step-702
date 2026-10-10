public interface WashType {
    int getDuration();
    double getCharge();
    String getName();
}

class QuickWash implements WashType {
    public int getDuration() { return 30; }
    public double getCharge() { return 20; }
    public String getName() { return "Quick"; }
}

class NormalWash implements WashType {
    public int getDuration() { return 45; }
    public double getCharge() { return 30; }
    public String getName() { return "Normal"; }
}

class HeavyWash implements WashType {
    public int getDuration() { return 60; }
    public double getCharge() { return 45; }
    public String getName() { return "Heavy"; }
}

class Student {
    String name;
    Student(String name) { this.name = name; }
}

class WashCycle {
    Student student;
    WashType washType;

    WashCycle(Student student, WashType washType) {
        this.student = student;
        this.washType = washType;
    }
}

class WashingMachine {
    String id;
    private WashCycle currentCycle;

    WashingMachine(String id) {
        this.id = id;
    }

    boolean startWash(Student s, WashType type) {
        if (currentCycle != null) {
            System.out.println("Machine " + id + " is currently busy.");
            return false;
        }

        currentCycle = new WashCycle(s, type);
        System.out.println(type.getName() + " wash started on " + id
                + " for " + s.name + " (" + type.getDuration()
                + " min). Charge: ₹"
                + String.format("%.2f", type.getCharge()) + ".");
        return true;
    }

    void completeWash() {
        if (currentCycle == null) {
            System.out.println(id + " is already free.");
            return;
        }

        System.out.println(id + " cycle completed.");
        currentCycle = null;
        System.out.println(id + " is now free.");
    }
}
class Main {
    public static void main(String[] args) {
        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");

        WashingMachine m1 = new WashingMachine("M1");
        WashingMachine m2 = new WashingMachine("M2");

        m1.startWash(asha, new QuickWash());
        m1.startWash(ravi, new HeavyWash());
        m2.startWash(ravi, new HeavyWash());
        m1.completeWash();
        m1.startWash(neha, new NormalWash());
    }
}

    