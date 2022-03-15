package SingletonDesignPattern;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class JDBCSingleton
{
    private static JDBCSingleton jdbc;
    private JDBCSingleton()
    {

    }
    public static JDBCSingleton getInstance()
    {
        if(jdbc==null)
        {
            jdbc=new JDBCSingleton();
        }
        return jdbc;
    }
    private static Connection getConnection() throws ClassNotFoundException,SQLException
{
    Connection con=null;
    Class.forName("com.mysql.cj.jdbc.Driver");
    con=DriverManager.getConnection("jdbc://mysql://localhost:3306/ecommerce");
    return con;
}
    public int insert(String name,String price,String id) throws ClassNotFoundException,SQLException
    {
        Connection c=null;
        PreparedStatement ps=null;
        int recordCounter =0;
        try
        {
            c=this.getConnection();
            ps=c.prepareStatement("INSERT INTO orders(id,name,price) values(?,?,?)");
            ps.setString(1,id);
            ps.setString(2,name);
            ps.setString(2,price);
            recordCounter=ps.executeUpdate();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(ps!=null) {
                ps.close();
            }if(c!=null)
        {
            c.close();
        }
        }
        return recordCounter;
    }
    public void view(String name) throws SQLException
    {
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try
        {
            con=this.getConnection();
            ps=con.prepareStatement("SELECT & FROM orders WHERE NAME=?");
            ps.setString(1,name);
            rs=ps.executeQuery();
            while (rs.next())
                System.out.println("Name : "+rs.getString(2)+" Price : "+rs.getString(3));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


}
