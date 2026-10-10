
import java.util.*;

public interface NotificationChannel {
    void send(Student student, String message);
}

class EmailChannel implements NotificationChannel {
    public void send(Student s, String message) {
        System.out.println("[Email → " + s.name + "] " + message);
    }
}

class SmsChannel implements NotificationChannel {
    public void send(Student s, String message) {
        System.out.println("[SMS → " + s.name + "] " + message);
    }
}

class AppChannel implements NotificationChannel {
    public void send(Student s, String message) {
        System.out.println("[App → " + s.name + "] " + message);
    }
}

class Student {
    String name, department;
    List<NotificationChannel> channels = new ArrayList<>();

    Student(String name, String department) {
        this.name = name;
        this.department = department;
    }

    void addChannel(NotificationChannel channel) {
        channels.add(channel);
    }
}

class Notice {
    String title;
    Set<String> departments;

    Notice(String title, String... departments) {
        this.title = title;
        this.departments = new HashSet<>(Arrays.asList(departments));
    }

    boolean isValid() {
        return title != null && !title.trim().isEmpty()
                && departments != null && !departments.isEmpty()
                && !departments.contains(null);
    }
}

class NoticeBoard {
    List<Student> students = new ArrayList<>();

    void addStudent(Student student) {
        students.add(student);
    }

    void postNotice(Notice notice) {
        if (!notice.isValid()) {
            System.out.println("Cannot post notice: At least one "
                    + "target department is required.");
            return;
        }

        System.out.println("Notice '" + notice.title
                + "' posted to " + String.join(", ",
                new TreeSet<>(notice.departments)) + ".");

        for (Student student : students) {
            if (notice.departments.contains(student.department)) {
                for (NotificationChannel channel : student.channels) {
                    channel.send(student, notice.title);
                }
            }
        }
    }
}

class Main {
    public static void main(String[] args) {
        NoticeBoard board = new NoticeBoard();

        Student asha = new Student("Asha", "CSE");
        asha.addChannel(new EmailChannel());
        asha.addChannel(new AppChannel());

        Student ravi = new Student("Ravi", "ECE");
        ravi.addChannel(new SmsChannel());

        board.addStudent(asha);
        board.addStudent(ravi);

        board.postNotice(new Notice("Lab Closed Tomorrow", "CSE"));
        board.postNotice(new Notice(
                "Fee Deadline Extended", "CSE", "ECE"));
        board.postNotice(new Notice("Sports Day"));
    }
}
