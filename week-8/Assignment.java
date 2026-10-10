
import java.time.LocalDate;

public abstract class Assignment {
    String title;
    int maxMarks;
    LocalDate dueDate;

    Assignment(String title, int maxMarks, LocalDate dueDate) {
        this.title = title;
        this.maxMarks = maxMarks;
        this.dueDate = dueDate;
    }

    abstract double getPenaltyPerDay();

    double calculateMarks(double marks, long lateDays) {
        return Math.max(0, marks * (1 - getPenaltyPerDay() * lateDays));
    }
}

class CodingAssignment extends Assignment {
    CodingAssignment(String t, int m, LocalDate d) {
        super(t, m, d);
    }

    double getPenaltyPerDay() { return 0.10; }
}

class WrittenAssignment extends Assignment {
    WrittenAssignment(String t, int m, LocalDate d) {
        super(t, m, d);
    }

    double getPenaltyPerDay() { return 0.20; }
}

class Student {
    String name;
    Student(String name) { this.name = name; }
}

class Submission {
    private String status = "Submitted";
    Student student;
    Assignment assignment;
    LocalDate submissionDate;

    Submission(Student s, Assignment a, LocalDate date) {
        student = s;
        assignment = a;
        submissionDate = date;

        long days = Math.max(0,
                date.toEpochDay() - a.dueDate.toEpochDay());

        System.out.println(s.name + "'s submission for '"
                + a.title + "' received ("
                + (days == 0 ? "on time" : days + " days late")
                + "). Status: " + status + ".");
    }

    void grade(double marks) {
        if (status.equals("Graded")) {
            System.out.println("Cannot grade again.");
            return;
        }

        if (marks < 0 || marks > assignment.maxMarks) {
            System.out.println("Invalid marks.");
            return;
        }

        long lateDays = Math.max(0,
                submissionDate.toEpochDay()
                        - assignment.dueDate.toEpochDay());

        double penalty = assignment.getPenaltyPerDay() * lateDays;
        double finalMarks = assignment.calculateMarks(marks, lateDays);

        System.out.printf("%s graded: %.0f/%d",
                student.name, finalMarks, assignment.maxMarks);

        if (lateDays > 0) {
            System.out.printf(" after %.0f%% late penalty",
                    penalty * 100);
        }

        System.out.println(". Status: Graded.");
        status = "Graded";
    }

    void resubmit() {
        if (status.equals("Graded")) {
            System.out.println("Cannot resubmit: '"
                    + assignment.title + "' has already been graded.");
        } else {
            System.out.println("Submission is already recorded.");
        }
    }
}
class Main {
    public static void main(String[] args) {
        Assignment coding = new CodingAssignment(
                "Linked List Lab", 50, LocalDate.of(2026, 3, 10));

        Assignment written = new WrittenAssignment(
                "Design Essay", 50, LocalDate.of(2026, 3, 12));

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");

        Submission s1 = new Submission(
                asha, coding, LocalDate.of(2026, 3, 10));

        Submission s2 = new Submission(
                ravi, written, LocalDate.of(2026, 3, 14));

        s1.grade(45);
        s2.grade(40);
        s1.resubmit();
    }
}