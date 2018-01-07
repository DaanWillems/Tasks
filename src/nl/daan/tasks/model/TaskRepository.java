package nl.daan.tasks.model;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TaskRepository implements ITaskRepository {

    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    public TaskRepository() {
       connect();
    }

    public void insert(Task task) {
       try {
          preparedStatement = convertTask(task, true);
          preparedStatement.executeUpdate();
       }
       catch (Exception e) {
          System.out.println(e);
       }
    }

    public void update(Task task) {
       convertTask(task, false);
       try {
          preparedStatement.executeUpdate();
       } catch (SQLException e) {
          e.printStackTrace();
       }
    }

    public void delete(Task task) {
       try {
          preparedStatement = connect
                  .prepareStatement("DELETE FROM `23558`.`tasks` WHERE `id` = ?");
          preparedStatement.setInt(1, task.id);
          preparedStatement.executeUpdate();
       }
       catch (Exception e) {
          System.out.println(e);
       }
    }

    public ArrayList<Task> getUnsolved() {
       ArrayList<Task> tasks = new ArrayList<>();
       try {

          statement = connect.createStatement();
          resultSet = statement
                  .executeQuery("select * from tasks where solved = 0");

          while (resultSet.next()) {
             Task t = convertTask(resultSet);
             tasks.add(t);
          }
       }
       catch (Exception e) {
          e.printStackTrace();
       }
       return tasks;
    }

    public ArrayList<Task> getAll() {
       ArrayList<Task> tasks = new ArrayList<>();
       try {

          statement = connect.createStatement();
          resultSet = statement
                  .executeQuery("select * from tasks");

          while (resultSet.next()) {
             Task t = convertTask(resultSet);
             tasks.add(t);
          }
       }
       catch (Exception e) {
          e.printStackTrace();
       }
       return tasks;
    }

    public Task getById(int id) {
       Task t = null;
       try {
          statement = connect.createStatement();
          resultSet = statement
                  .executeQuery("select * from tasks where id = "+id);

          while (resultSet.next()) {
             t = convertTask(resultSet);
             break;
          }
       }
       catch (Exception e) {
          e.printStackTrace();
       }
       return t;
    }

   @Override
   public ArrayList<Task> getAssignedTasks(Player p) {
      ArrayList<Task> tasks = new ArrayList<>();
      try {

         statement = connect.createStatement();
         preparedStatement = connect.
                 prepareStatement("select * from tasks where assignedUUID = ? AND solved = ?");
         preparedStatement.setString(1, p.getUniqueId().toString());
         preparedStatement.setBoolean(2, false);

         resultSet = preparedStatement.executeQuery();
         while (resultSet.next()) {
            Task t = convertTask(resultSet);
            tasks.add(t);
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return tasks;
   }

   @Override
   public ArrayList<Task> getUnassigned() {
      ArrayList<Task> tasks = new ArrayList<>();
      try {

         statement = connect.createStatement();
         resultSet = statement
                 .executeQuery("select * from tasks where assignedUUID IS NULL");

         while (resultSet.next()) {
            Task t = convertTask(resultSet);
            tasks.add(t);
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return tasks;
   }

   public Task convertTask(ResultSet resultSet) {
      Task t = new Task();
      try {
         t.title = resultSet.getString("title");
         t.description = resultSet.getString("description");
         t.creator = Bukkit.getPlayer(UUID.fromString(resultSet.getString("creatorUUID")));
         t.creatorUUID = resultSet.getString("creatorUUID");
         if (resultSet.getString("assignedUUID") != null) {
            t.assignedPlayer = Bukkit.getPlayer(UUID.fromString(resultSet.getString("assignedUUID")));
            t.assignedUUID = resultSet.getString("assignedUUID");
         }
         t.solved = resultSet.getBoolean("solved");
         t.location = getLocationFromString(resultSet.getString("location"));
         t.id = resultSet.getInt("id");
         if (resultSet.getString("deadline") != null) {
            t.deadline = resultSet.getDate("deadline").toLocalDate();
         }
         return t;
      } catch (Exception e) {
         e.printStackTrace();
      }
      return t;
   }

   public PreparedStatement convertTask(Task task, boolean newTask) {
      //Start of by serializing the location
      Location loc = task.location;
      String locString = loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ() + ":" + loc.getYaw() + ":" + loc.getPitch() ;
      try {
         if(newTask) {
            preparedStatement = connect
                    .prepareStatement("    INSERT INTO `23558`.`tasks` " +
                            "(`id`, " +
                            "`creatorUUID`, " +
                            "`assignedUUID`, " +
                            "`solved`, " +
                            "`title`, " +
                            "`description`, " +
                            "`location`, " +
                            "`deadline`) " +
                            "VALUES (NULL, ?, ?, ?, ?, ?, ?, ?);");

         } else {
            preparedStatement = connect
                    .prepareStatement("UPDATE `23558`.`tasks` SET " +
                            "`creatorUUID` = ?, " +
                            "`assignedUUID` = ?, " +
                            "`solved` = ?, " +
                            "`title` = ?, " +
                            "`description` = ?, " +
                            "`location` = ?," +
                            "`deadline` = ? " +
                            "WHERE `id` = ?;");
            preparedStatement.setInt(8, task.id);
         }

         preparedStatement.setString(1, task.creatorUUID.toString());

         if(task.assignedPlayer != null) {
            preparedStatement.setString(2, task.assignedPlayer.getUniqueId().toString());
         } else {
            preparedStatement.setString(2, null);
         }
         preparedStatement.setBoolean(3, task.solved);
         preparedStatement.setString(4, task.title);
         preparedStatement.setString(5, task.description);
         preparedStatement.setString(6, locString);
         if(task.deadline != null) {
            final java.sql.Date sqlDate=  java.sql.Date.valueOf(task.deadline);
            preparedStatement.setDate(7, sqlDate);
         } else {
            preparedStatement.setString(7, null);
         }
      }
      catch (Exception e) {
         e.printStackTrace();
      }
      return preparedStatement;
   }

   private void connect() {
      try {
         Class.forName("com.mysql.jdbc.Driver");
         // Setup the connection with the DB
         connect = DriverManager
                 .getConnection("[redacted]");
      }
      catch (Exception e) {
         e.printStackTrace();
      }
   }

   public Location getLocationFromString(String s) {
      if (s == null || s.trim() == "") {
         return null;
      }
      final String[] parts = s.split(":");
      if (parts.length == 6) {
         World w = Bukkit.getServer().getWorld(parts[0]);
         double x = Double.parseDouble(parts[1]);
         double y = Double.parseDouble(parts[2]);
         double z = Double.parseDouble(parts[3]);
         float yaw = Float.parseFloat(parts[4]);
         float pitch = Float.parseFloat(parts[5]);
         return new Location(w, x, y, z, yaw, pitch);
      }
      return null;
   }
}
