import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.io.*;
import java.sql.*;
public class Employee {
    public static void main(String[] args)throws Exception{
        Scanner sc = new Scanner(System.in);
        String dbUrl = "jdbc:mysql://localhost:3306/emp";
        String dbUser = "root";
        String dbPass = "";
        String driver = "com.mysql.cj.jdbc.Driver";
        Class.forName(driver);
        Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        if(con!=null)
        {    
            System.out.println("===================================================");
            System.out.println("====== Welcome To Employee Management System ======");
            System.out.println("===================================================");
            while (true) {
                System.out.println();
                System.out.println("======= Welcome To Home Page =======");
                System.out.println("1. Add Employee");
                System.out.println("2. Resign Employee");
                System.out.println("3. Add Department");
                System.out.println("4. Veiw All Departments");
                System.out.println("5. Add Role");
                System.out.println("6. Get Employee Full Details By Employee ID");
                System.out.println("7. Increment Salary Of Employee");
                System.out.println("8. Print All Resigned Employees Details in txt file");
                System.out.println("9. EXIT");
                int choice=0;
                boolean b = true;
                while (b) {
                    try
                    {
                        System.out.println("Enter Choice : ");
                        choice = sc.nextInt();
                        b=false;
                    }
                    catch(InputMismatchException e)
                    {
                        System.out.println("Invalid Choice , Please Enter Valid Choice .");
                        sc.next();
                    }
                }
                switch (choice) {
                    case 1:
                        new setEmployees().addEmployees(con);
                        break;
                    case 2:
                        new setEmployees().resignEmployee(con);
                        break;
                    case 3:
                        new setDepartment().addDept(con);
                        break;
                    case 4:
                        new setDepartment().viewDept(con);
                        break;
                    case 5:
                        new setRole().addRole(con);
                        break;
                    case 6:
                        new setEmployees().viewEmployee(con);
                        break;
                    case 7:
                        new setEmployees().incrementSalary(con);
                        break;
                    case 8:
                        new setEmployees().printResignEmployee(con);
                        break;
                    case 9:
                        System.out.println("----- Thank You For Visit -----");
                        System.exit(0);
                    default:
                        System.out.println("Invalid Choice , Enter Valid Choice");
                        break;
                }
            }
        }
        sc.close();
    }
}
class setEmployees
{
    Scanner sc = new Scanner(System.in);
    void addEmployees(Connection con) throws Exception
    {
        System.out.println("------- Add Employee -------");
        boolean p = true;
        String f_name="";
        while(p)
        {
            System.out.println("Enter Employee First Name : ");
            String s_name=sc.next();
            String temp_name=s_name.toLowerCase();
            int x=0;
            for (int i=0;i<s_name.length();i++) {
                if(temp_name.charAt(i)>='a'&&temp_name.charAt(i)<='z')
                {
                    x++;
                }
            }
            if(x==s_name.length())
            {
                f_name=s_name;
                break;
            }
            else
            {
                System.out.println("Invalid First Name, Enter Valid First Name");
            }
        }
        boolean q = true;
        String l_name="";
        while(q)
        {
            System.out.println("Enter Employee Last Name : ");
            String s_name=sc.next();
            String temp_name=s_name.toLowerCase();
            int x=0;
            for (int i=0;i<s_name.length();i++) {
                if(temp_name.charAt(i)>='a'&&temp_name.charAt(i)<='z')
                {
                    x++;
                }
            }
            if(x==s_name.length())
            {
                l_name=s_name;
                break;
            }
            else
            {
                System.out.println("Invalid First Name, Enter Valid First Name");
            }
        }
        String mobile;
        while (true) {
            System.out.println("Enter Phone No.");
            mobile=sc.next();
            int x = 0;
            if(mobile.length()==10)
            {
                for (int i = 0; i < 10; i++) {
                    if (mobile.charAt(i)>='0' && mobile.charAt(i)<='9') {
                        x++;
                    }
                }
            }
            if(x == 10)
            {
                break;
            }   
            else
            {
                System.out.println("Invalid Mobile No. , Please Enter Valid Mobile No. ");
            }
        }
        ArrayList<Integer> al = new ArrayList<>();
        String sql = "{call viewDept()}";
        CallableStatement cst = con.prepareCall(sql);
        ResultSet rs = cst.executeQuery();
        System.out.println("-------------------------- All Departments --------------------------   ");
        while (rs.next()) {
            al.add(rs.getInt(1));
            System.out.print("Department ID : "+rs.getInt(1));
            System.out.println("   Department Name : "+rs.getString(2));
            System.out.println("====================================================================");
        }
        int d_id;
        while (true) {
            System.out.println("Enter Department ID From Above List : ");
            d_id = sc.nextInt();
            sc.nextLine();
            if(al.contains(d_id))
            {
                break;
            }
            else
            {
                System.out.println("Department ID Not Found, Enter Valid Department ID");
            }
        }
        ArrayList<Integer> al1 = new ArrayList<>();
        String sql1 = "select * from role where dept_id = "+d_id;
        PreparedStatement pst1 = con.prepareStatement(sql1);
        ResultSet rs1 = pst1.executeQuery();
        System.out.println("----- All Available Roles For Department ID : "+d_id+" -----");
        while (rs1.next()) {
            al1.add(rs1.getInt(1));
            System.out.print("Role ID : "+rs1.getInt(1));
            System.out.println("   Role : "+rs1.getString(3));
            System.out.println("Salary : "+rs1.getInt(4));
            System.out.println("=======================================================");
        }
        int r_id;
        int salary=0;
        while (true) {
            System.out.println("Enter Role ID From Above List : ");
            r_id = sc.nextInt();
            sc.nextLine();
            if(al1.contains(r_id))
            {
                String sql3 = "select role_salary from role where role_id = "+r_id;
                PreparedStatement pst3 = con.prepareStatement(sql3);
                ResultSet rs2 = pst3.executeQuery();
                while (rs2.next()) {
                    salary = rs2.getInt(1);
                }
                break;
            }
            else
            {
                System.out.println("Role ID Not Found, Enter Valid Role ID");
            }
        }
        String sql2 = "insert into employees(dept_id,role_id,first_name,last_name,phone,salary,hire_date) values (?,?,?,?,?,?,?)";
        PreparedStatement pst2 = con.prepareStatement(sql2);
        pst2.setInt(1, d_id);
        pst2.setInt(2, r_id);
        pst2.setString(3, f_name);
        pst2.setString(4, l_name);
        pst2.setString(5, mobile);
        pst2.setInt(6, salary);
        pst2.setString(7, new Get().getDate());
        int r = pst2.executeUpdate();
        if (r>0) {
            System.out.println("===== Employee Added Successfully =====");
            String sql4 = "select emp_id from employees ";
            PreparedStatement pst4 = con.prepareStatement(sql4);
            ResultSet rs4 = pst4.executeQuery();
            int e_id=0;
            while (rs4.next()) {
                e_id = rs4.getInt(1);
            }
            System.out.println("Employee ID For "+f_name+" : "+e_id);
        }
    }
    void resignEmployee(Connection con) throws Exception
    {
        System.out.println("----- Resign Employee -----");
        System.out.println("Enter Employee ID to Resign : "); 
        int e_id = sc.nextInt();
        sc.nextLine();
        String sql = "select * from employees where emp_id = "+e_id;
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        boolean b = false;
        while (rs.next()) {
            b=true;
            System.out.println("  Employee ID : "+rs.getInt(1));
            System.out.println("  First Name : "+rs.getString(4));
            System.out.println("  Last Name : "+rs.getString(5));
            System.out.println("  Phone : "+rs.getString(6));
            System.out.println("Press 'Enter Key' To Confirm & Resign Employee (else Enter Any Digit or Letter) : ");
            if(sc.nextLine().equals(""))
            {
                String sql1 = "{call resignEmployee(?,?,?,?,?,?,?,?,?)}";
                CallableStatement cst = con.prepareCall(sql1);
                cst.setInt(1, e_id);
                cst.setInt(2, rs.getInt(2));
                cst.setInt(3,rs.getInt(3));
                cst.setString(4, rs.getString(4));
                cst.setString(5, rs.getString(5));
                cst.setString(6, rs.getString(6));
                cst.setInt(7, rs.getInt(7));
                cst.setString(8, rs.getString(8));
                cst.setString(9, new Get().getDate());
                int r = cst.executeUpdate();
                if(r>0)
                {
                    System.out.println("===== Employee Resigned Successfully =====");
                }
            }
            else
            {
                System.out.println("Employee Resign Process Cancelled.");
            }
        }
        if(!b)
        {
           System.out.println("Employee ID Not Found");
        }
    }
    void viewEmployee(Connection con) throws Exception
    {
        System.out.println("----- View Employee -----");
        System.out.println("Enter Employee ID to View Details : ");
        int e_id = sc.nextInt();
        sc.nextLine();
        String sql = "select * from employees where emp_id = "+e_id;
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        boolean b = false;
        while (rs.next()) {
            b=true;
            System.out.println("------ Employee Details ------");
            System.out.println("     Employee ID : "+rs.getInt(1));
            System.out.println("     First Name  : "+rs.getString(4));
            System.out.println("     Last Name   : "+rs.getString(5));
            System.out.println("     Phone       : "+rs.getString(6));
            System.out.println("     Salary      : "+rs.getInt(7));
            System.out.println("     Hire Date   : "+rs.getString(8));
            String sql1 = "select * from departments where dept_id = "+rs.getInt(2);
            PreparedStatement pst1 = con.prepareStatement(sql1);
            ResultSet rs1 = pst1.executeQuery();
            System.out.println("-> Employee's Department Detail <-");
            while (rs1.next()) {
                System.out.println("     Department ID            : "+rs1.getInt(1));
                System.out.println("     Department Name          : "+rs1.getString(2));
                System.out.println("     Department Creation Date : "+rs1.getString(3));
            }
            String sql2 = "select * from role where role_id = "+rs.getInt(3);
            PreparedStatement pst2 = con.prepareStatement(sql2);
            ResultSet rs2 = pst2.executeQuery();
            System.out.println("-> Employee's Role Detail <-");
            while (rs2.next()) {
                System.out.println("     Role ID   : "+rs2.getInt(1));
                System.out.println("     Role Name : "+rs2.getString(3));
                System.out.println("=============================================================");
            }
        }
        if(!b)
        {
           System.out.println("Employee ID Not Found");
        }
    }
    void incrementSalary(Connection con) throws Exception
    {
        System.out.println("----- Increment Salary -----");
        System.out.println("Enter Employee ID to Increment Salary : ");
        int e_id = sc.nextInt();
        sc.nextLine();
        String sql = "select * from employees where emp_id = "+e_id;
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        boolean b = false;
        while (rs.next()) {
            b=true;
            System.out.println("     Employee Name  : "+rs.getString(4));
            System.out.println("     Salary         : "+rs.getInt(7));
            int oldSalary=rs.getInt(7);
            int newSalary=0;
            boolean c = true;
            while (c) {
                System.out.println("---- Increment Salary Options ----");
                System.out.println("1. Increment Salary By Percentage.");
                System.out.println("2. Increment Salary By Fix Amount.");
                System.out.println("Enter Your Choice :");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("Enter Percentage :");
                        double percentage = sc.nextInt();
                        if (percentage>=0 && percentage<=100) {
                            newSalary=(int)(oldSalary+oldSalary*(percentage/100));
                            c=false;
                        }
                        else
                        {
                            System.out.println("Invalid Percentage,Enter From 0 to 100");
                        }
                        break;
                    case 2:
                        System.out.println("Enter Amount (Maximum Limit is "+oldSalary+") :");
                        int amt = sc.nextInt();
                        if (amt>=0 && amt<=oldSalary)
                        {
                            newSalary=oldSalary+amt;
                            c=false;
                        }
                        else
                        {
                            System.out.println("Invalid Amount .");
                        }
                        break;
                    default:
                        System.out.println("Invalid Choice, Enter Valid Choice .");
                        break;
                }
            }
            String sql1 = "update employees set salary = ? where emp_id = ?";
            PreparedStatement pst1 = con.prepareStatement(sql1);
            pst1.setInt(1, newSalary);
            pst1.setInt(2, e_id);
            int r = pst1.executeUpdate();
            if(r>0)
            {
                System.out.println("Incremented Salary : "+newSalary);
                System.out.println("====== Salary Incremented Successfully ======");
            }
        }
        if(!b)
        {
           System.out.println("Employee ID Not Found");
        }
    }
    void printResignEmployee(Connection con) throws Exception
    {
        File f = new File("src/Resigned Employees.txt");
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        String sql = "select * from resigned_employees";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        bw.write("------------ All Resigned Employees Details ------------\n");
        int i = 1;
        while (rs.next()) {
            bw.write("------------------ Employee "+i+" Detail ------------------\n");
            bw.write("     Employee ID   : "+rs.getInt(1)+"\n");
            bw.write("     Department ID : "+rs.getInt(2)+"\n");
            bw.write("     Role ID       : "+rs.getInt(3)+"\n");
            bw.write("     First Name    : "+rs.getString(4)+"\n");
            bw.write("     Last Name     : "+rs.getString(5)+"\n");
            bw.write("     Phone         : "+rs.getString(6)+"\n");
            bw.write("     Salary        : "+rs.getInt(7)+"\n");
            bw.write("     Hire Date     : "+rs.getString(8)+"\n");
            bw.write("     Resign Date   : "+rs.getString(9)+"\n");
            bw.write("=======================================================\n");
            i++;
        }
        bw.close();
        System.out.println("All Resigned Employees Details Printed In 'Resigned Employees.txt' File.");
    }
}
class Departments
{
    int dept_id;
    String dept_name;
    String creation_date;
    public Departments(int dept_id, String dept_name, String creation_date) {
        this.dept_id = dept_id;
        this.dept_name = dept_name;
        this.creation_date = creation_date;
    }
    public String toString() {
        return "  Department ID : " + dept_id + "    Department Name : " + dept_name + "\n  Department Creation Date : " + creation_date;
    }
}
class setDepartment
{
    Scanner sc = new Scanner(System.in);
    void addDept(Connection con) throws Exception
    {
        System.out.println("---- Add Department ----");
        System.out.println("Enter Department ID : ");
        int dept_id = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter Department Name : ");
        String dept_name = sc.nextLine();
        try
        {
            String sql="insert into departments(dept_id,dept_name,creation_date) values(?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, dept_id);
            pst.setString(2, dept_name);
            pst.setString(3, new Get().getDate());
            int r = pst.executeUpdate();
            if(r>0)
            {
                System.out.println("==== Department Added Suuccessfully ====");
            }
        }
        catch(SQLIntegrityConstraintViolationException e)
        {
            System.out.print("Department ID Already exist ");
            System.out.println("Please Add Department With Different Department ID");
        }
    }
    void viewDept(Connection con) throws Exception
    {
        LinkedList ll = new LinkedList();
        String sql = "select * from departments order by dept_id desc";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            Departments d = new Departments(rs.getInt(1), rs.getString(2), rs.getString(3));
            ll.add(d);
        }
        System.out.println("---------------------- All Departments ----------------------");
        System.out.println("=============================================================");
        ll.display();
    }
}
class setRole
{
    Scanner sc = new Scanner(System.in);
    void addRole(Connection con) throws Exception
    {
        ArrayList<Integer> al = new ArrayList<>();
        String sql = "{call viewDept()}";
        CallableStatement cst = con.prepareCall(sql);
        ResultSet rs = cst.executeQuery();
        System.out.println("-------------------------- All Departments --------------------------   ");
        while (rs.next()) {
            al.add(rs.getInt(1));
            System.out.print("Department ID : "+rs.getInt(1));
            System.out.println("   Department Name : "+rs.getString(2));
            System.out.println("====================================================================");
        }
        System.out.println("Enter Department ID From Above List : ");
        int id = sc.nextInt();
        sc.nextLine();
        if(al.contains(id))
        {
            System.out.println("----- Add Role For Departmment ID : "+id+" -----");
            System.out.println("Enter Role Name :");
            String role_name = sc.nextLine();
            System.out.println("Enter Salary For This Role : ");
            int salary = sc.nextInt();
            String sql1 = "insert into role (dept_id,role_name,role_salary) values (?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql1);
            pst.setInt(1, id);
            pst.setString(2, role_name);
            pst.setInt(3, salary);
            int r = pst.executeUpdate();
            if(r>0)
            {
                System.out.println("===== Role Added Successfully =====");
            }
        }
        else
        {
            System.out.println("Department ID Not Found.");
        }
    }
}
class Get
{
    String getDate()
    {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }
}
class LinkedList
{
    class Node
    {
        Departments d;
        Node next;
        Node (Departments d)
        {
            this.d=d;
            next=null;
        }
    }    
    Node first=null;
    void add(Departments d)
    {
        Node n = new Node(d);
        if(first==null)
        {
            first=n;
        }
        else
        {
            n.next = first;
            first = n;
        }
    }
    void display()
    {
        Node temp = first;
        while (temp != null) {
            System.out.println(temp.d);
            System.out.println("=============================================================");
            temp = temp.next;
        }
    }
}