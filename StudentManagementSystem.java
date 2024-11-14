import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentManagementSystem extends JFrame {

    // Declare the components for the UI
    private JTextField txtName, txtAge, txtEmail;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private int selectedRowIndex = -1;

    // List to hold student data
    private ArrayList<Student> studentList = new ArrayList<>();

    public StudentManagementSystem() {
        // Set up the main window properties
        setTitle("Student Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for input fields (Name, Age, Email)
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        JLabel lblName = new JLabel("Student Name:");
        txtName = new JTextField();

        JLabel lblAge = new JLabel("Student Age:");
        txtAge = new JTextField();

        JLabel lblEmail = new JLabel("Student Email:");
        txtEmail = new JTextField();

        inputPanel.add(lblName);
        inputPanel.add(txtName);
        inputPanel.add(lblAge);
        inputPanel.add(txtAge);
        inputPanel.add(lblEmail);
        inputPanel.add(txtEmail);

        add(inputPanel, BorderLayout.NORTH);

        // Panel for buttons (Add, Update, Delete, Clear)
        btnAdd = new JButton("Add Student");
        btnUpdate = new JButton("Update Student");
        btnDelete = new JButton("Delete Student");
        btnClear = new JButton("Clear Fields");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        add(buttonPanel, BorderLayout.SOUTH);

        // Table to display student records
        tableModel = new DefaultTableModel(new Object[]{"ID", "Name", "Age", "Email"}, 0);
        studentTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(studentTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Load initial data into the table
        loadStudentData();

        // Add action listeners for buttons
        btnAdd.addActionListener(e -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e -> clearInputFields());

        // Handle row selection in the table to populate the fields for editing
        studentTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedRowIndex = studentTable.getSelectedRow();
                if (selectedRowIndex != -1) {
                    txtName.setText(tableModel.getValueAt(selectedRowIndex, 1).toString());
                    txtAge.setText(tableModel.getValueAt(selectedRowIndex, 2).toString());
                    txtEmail.setText(tableModel.getValueAt(selectedRowIndex, 3).toString());
                }
            }
        });
    }

    // Method to populate the table with student data
    private void loadStudentData() {
        for (Student student : studentList) {
            tableModel.addRow(new Object[]{student.getId(), student.getName(), student.getAge(), student.getEmail()});
        }
    }

    // Method to add a new student record
    private void addStudent() {
        String name = txtName.getText();
        int age = Integer.parseInt(txtAge.getText());
        String email = txtEmail.getText();

        Student newStudent = new Student(name, age, email);
        studentList.add(newStudent);
        tableModel.addRow(new Object[]{newStudent.getId(), name, age, email});
    }

    // Method to update an existing student record
    private void updateStudent() {
        if (selectedRowIndex != -1) {
            String updatedName = txtName.getText();
            int updatedAge = Integer.parseInt(txtAge.getText());
            String updatedEmail = txtEmail.getText();

            Student selectedStudent = studentList.get(selectedRowIndex);
            selectedStudent.setName(updatedName);
            selectedStudent.setAge(updatedAge);
            selectedStudent.setEmail(updatedEmail);

            tableModel.setValueAt(updatedName, selectedRowIndex, 1);
            tableModel.setValueAt(updatedAge, selectedRowIndex, 2);
            tableModel.setValueAt(updatedEmail, selectedRowIndex, 3);
        }
    }

    // Method to delete a student record
    private void deleteStudent() {
        if (selectedRowIndex != -1) {
            studentList.remove(selectedRowIndex);
            tableModel.removeRow(selectedRowIndex);
        }
    }

    // Method to clear the input fields
    private void clearInputFields() {
        txtName.setText("");
        txtAge.setText("");
        txtEmail.setText("");
    }

    // Main method to launch the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentManagementSystem frame = new StudentManagementSystem();
            frame.setVisible(true);
        });
    }

    // Inner class representing a Student object
    class Student {
        private static int idCounter = 1;
        private int id;
        private String name;
        private int age;
        private String email;

        // Constructor
        public Student(String name, int age, String email) {
            this.id = idCounter++;
            this.name = name;
            this.age = age;
            this.email = email;
        }

        // Getter and setter methods for the Student fields
        public int getId() { return id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public int getAge() { return age; }
        public void setAge(int age) { this.age = age; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }
}
