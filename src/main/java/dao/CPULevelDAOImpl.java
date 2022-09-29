package dao;

import entity.CPULevel;

import java.util.List;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CPULevelDAOImpl implements CPULevelDAO {

    private final Connection connection;

    public CPULevelDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<CPULevel> searchAll() {
        List<CPULevel> cpuLevels = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM Cpu_Level");

            while (resultSet.next()){
                cpuLevels.add(new CPULevel(resultSet.getInt("id"), resultSet.getString("description"), resultSet.getInt("depth")));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }

        return cpuLevels;
    }
}
