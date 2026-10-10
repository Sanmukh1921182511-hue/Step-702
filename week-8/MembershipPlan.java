
public interface MembershipPlan {
    int getMonths();
    double getDiscount();
    String getName();

    default double calculateFee() {
        return 1000 * getMonths() * (1 - getDiscount());
    }
}

class MonthlyPlan implements MembershipPlan {
    public int getMonths() { return 1; }
    public double getDiscount() { return 0; }
    public String getName() { return "Monthly"; }
}

class QuarterlyPlan implements MembershipPlan {
    public int getMonths() { return 3; }
    public double getDiscount() { return 0.10; }
    public String getName() { return "Quarterly"; }
}

class AnnualPlan implements MembershipPlan {
    public int getMonths() { return 12; }
    public double getDiscount() { return 0.25; }
    public String getName() { return "Annual"; }
}

class Member {
    String name;
    Membership membership;

    Member(String name) {
        this.name = name;
    }

    void buyMembership(MembershipPlan plan) {
        membership = new Membership(plan);
        System.out.printf("%s membership created for %s. Fee: ₹%.2f. Status: Active.%n",
                plan.getName(), name, plan.calculateFee());
    }

    void checkIn() {
        if (membership == null || !membership.checkIn()) {
            System.out.println("Check-in denied: " + name
                    + "'s membership is "
                    + (membership == null ? "not available"
                    : membership.getStatus()) + ".");
        } else {
            System.out.println(name + " checked in successfully.");
        }
    }

    void freeze() {
        if (membership != null && membership.freeze()) {
            System.out.println(name + "'s membership frozen. Status: Frozen.");
        } else {
            System.out.println("Cannot freeze an "
                    + (membership == null ? "unavailable"
                    : membership.getStatus()) + " membership.");
        }
    }

    void unfreeze() {
        if (membership != null && membership.unfreeze()) {
            System.out.println(name + "'s membership unfrozen. Status: Active.");
        } else {
            System.out.println("Cannot unfreeze this membership.");
        }
    }

    void expire() {
        if (membership != null) {
            membership.expire();
            System.out.println(name + "'s membership expired. Status: Expired.");
        }
    }
}

class Membership {
    private String status = "Active";
    private MembershipPlan plan;

    Membership(MembershipPlan plan) {
        this.plan = plan;
    }

    String getStatus() {
        return status;
    }

    boolean checkIn() {
        return status.equals("Active");
    }

    boolean freeze() {
        if (!status.equals("Active")) return false;
        status = "Frozen";
        return true;
    }

    boolean unfreeze() {
        if (!status.equals("Frozen")) return false;
        status = "Active";
        return true;
    }

    void expire() {
        status = "Expired";
    }
}

class Main {
    public static void main(String[] args) {
        Member asha = new Member("Asha");
        Member ravi = new Member("Ravi");

        asha.buyMembership(new QuarterlyPlan());
        ravi.buyMembership(new MonthlyPlan());

        asha.checkIn();
        asha.freeze();
        asha.checkIn();

        ravi.expire();
        ravi.freeze();
    }
}