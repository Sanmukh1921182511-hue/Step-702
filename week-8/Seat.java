import java.util.*;

abstract class Seat {
    String id;
    Seat(String id) { this.id = id; }
    abstract double getPrice();
}

class RegularSeat extends Seat {
    RegularSeat(String id) { super(id); }
    double getPrice() { return 150; }
}

class PremiumSeat extends Seat {
    PremiumSeat(String id) { super(id); }
    double getPrice() { return 250; }
}

class ReclinerSeat extends Seat {
    ReclinerSeat(String id) { super(id); }
    double getPrice() { return 400; }
}

class Customer {
    String name;
    Customer(String name) { this.name = name; }
}

class Show {
    String name;
    Date startTime;
    Map<String, Seat> seats = new HashMap<>();
    Set<String> booked = new HashSet<>();

    Show(String name, Date startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    void addSeat(Seat seat) {
        seats.put(seat.id, seat);
    }

    Booking book(Customer customer, String... ids) {
        if (ids.length == 0 || ids.length > 6) {
            System.out.println("Booking must contain 1 to 6 seats.");
            return null;
        }

        Set<String> selected = new HashSet<>();

        for (String id : ids) {
            if (!seats.containsKey(id)) {
                System.out.println("Seat " + id + " does not exist.");
                return null;
            }

            if (booked.contains(id) || !selected.add(id)) {
                System.out.println("Seat " + id
                        + " is already booked for this show.");
                return null;
            }
        }

        booked.addAll(selected);
        Booking booking = new Booking(customer, this, ids);

        System.out.println("Booking confirmed for " + customer.name
                + ": " + String.join(", ", ids)
                + ". Total: ₹" + String.format("%.2f", booking.total));
        return booking;
    }
}

class Booking {
    Customer customer;
    Show show;
    List<String> seatIds;
    double total;
    boolean cancelled = false;

    Booking(Customer customer, Show show, String... ids) {
        this.customer = customer;
        this.show = show;
        seatIds = new ArrayList<>(Arrays.asList(ids));

        for (String id : ids) {
            total += show.seats.get(id).getPrice();
        }
    }

    void cancel() {
        if (cancelled) {
            System.out.println("Booking is already cancelled.");
            return;
        }

        if (!new Date().before(show.startTime)) {
            System.out.println("Cannot cancel after the show starts.");
            return;
        }

        show.booked.removeAll(seatIds);
        cancelled = true;

        System.out.println(customer.name + "'s booking cancelled. Seats "
                + String.join(", ", seatIds) + " released.");
    }
}
class Main {
    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 2);

        Show show = new Show("7 PM Show", calendar.getTime());

        show.addSeat(new RegularSeat("A1"));
        show.addSeat(new RegularSeat("A2"));
        show.addSeat(new PremiumSeat("F5"));
        show.addSeat(new ReclinerSeat("R1"));

        Customer asha = new Customer("Asha");
        Customer ravi = new Customer("Ravi");
        Customer neha = new Customer("Neha");

        Booking b1 = show.book(asha, "A1", "A2", "F5");
        show.book(ravi, "A2");
        show.book(ravi, "R1");

        if (b1 != null) b1.cancel();

        show.book(neha, "A2");
    }
}