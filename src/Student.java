import java.io.Serializable;
import java.util.Arrays;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private int[] moduleMarks = new int[3];
    private String moduleGrade;

    public Student(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {

        return id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public int[] getModuleMarks() {
        return moduleMarks;
    }

    public void setModuleMarks(int mark1, int mark2, int mark3) {
        this.moduleMarks[0] = mark1;
        this.moduleMarks[1] = mark2;
        this.moduleMarks[2] = mark3;
        calculateGrade();
    }

    private void calculateGrade() {
        int average = (moduleMarks[0] + moduleMarks[1] + moduleMarks[2]) / 3;
        if (average >= 80) {
            this.moduleGrade = "Distinction";
        } else if (average >= 70) {
            this.moduleGrade = "Merit";
        } else if (average >= 40) {
            this.moduleGrade = "Pass";
        } else {
            this.moduleGrade = "Fail";
        }
    }

    public boolean PassedAllModules() {
        return moduleMarks[0] >= 40 && moduleMarks[1] >= 40 && moduleMarks[2] >= 40;
    }



    public int getTotalMarks() {
        return moduleMarks[0] + moduleMarks[1] + moduleMarks[2];
    }

    public int getAvMarks() {
        return getTotalMarks() / 3;
    }


    public String getGrade() {
        return moduleGrade;
    }

    public String getReport() {
        return name + "\t" + id + "\t" + moduleMarks[0] + "\t" + moduleMarks[1] + "\t" + moduleMarks[2] + "\t" + getTotalMarks() + "\t" + getAvMarks() + "\t" + getGrade();
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Marks: " + Arrays.toString(moduleMarks) + ", Grade is: " + moduleGrade;
    }
}