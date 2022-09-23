package Modelos;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.ArrayList;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModeloDao {
    PreparedStatement insert;
    ResultSet rs;
    Connection con;
    Conexion conectar = new Conexion();
    Modelo m = new Modelo();


    public int agregar(Modelo m) {
        String sql = ("INSERT INTO modelo(nombre,anio,marca_id)values(?,?,?)");
        try {
            PaisDao paises = new PaisDao();
            ArrayList<Pais> listarPaises = paises.getPais();
            int pais_id = 0;
            for (Pais p : listarPaises) {
                /*
                if (m.getPais().equals(p.getName())) {
                    pais_id = p.getId();
                }
                */
            }
            con = conectar.getConection();
            insert = con.prepareStatement(sql);
            insert.setString(1, m.getNombre());
            insert.setInt(2, m.getAnio());
            insert.setInt(3, m.getMarca_id());
            System.out.println(insert);
            insert.executeUpdate();
        } catch (Exception e) {

        }
        return 1;
    }

    public ArrayList<Modelo> listarModelos() throws SQLException {
        ArrayList<Modelo> data = new ArrayList<>();
        String sql = "SELECT modelo.id as \"ID\", modelo.nombre as \"Nombre\", modelo.anio as \"Anio\", modelo.marca_id as \"Marca_id\" FROM modelo ORDER BY modelo.id DESC";
        try {
            con = conectar.getConection();
            insert = con.prepareStatement(sql);
            rs = insert.executeQuery();
            while (rs.next()) {
                Modelo m = new Modelo();
                m.setId(rs.getInt(1));
                m.setNombre(rs.getString(2));
                m.setAnio(rs.getInt(3));
                m.setMarca_id(rs.getInt(4));
                data.add(m);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return data;
    }

    public ArrayList<Modelo> buscarModelos(String name) throws SQLException {
        ArrayList<Modelo> modelos = new ArrayList<>();
        String sql = "select * from modelo where modelo.nombre=" + "'" + name + "'";
        System.out.println(sql);
        try {
            con = conectar.getConection();
            insert = con.prepareStatement(sql);
            rs = insert.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                Modelo m = new Modelo();
                m.setId(rs.getInt(1));
                m.setNombre(rs.getString(2));
                m.setAnio(rs.getInt(3));
                m.setMarca_id(rs.getInt(4));
                modelos.add(m);
            }
        } catch (Exception e) {
        }
        return modelos;
    }

    public int modificar(Modelo mar) {
        int act = 0;
        String sqlU = ("UPDATE modelo SET nombre=?,anio=?, marca_id=? WHERE id=?");
        try {
            /*
            PaisDao paises = new PaisDao();
            ArrayList<Pais> listarPaises = paises.getPais();
            int pais_id = 0;
            for (Pais p : listarPaises) {
                if (mar.getPais().equals(p.getName())) {
                    pais_id = p.getId();
                }
            }
            */

            con = conectar.getConection();
            insert = con.prepareStatement(sqlU);
            insert.setString(1, mar.getNombre());
            insert.setInt(2, mar.getAnio());
            insert.setInt(3, mar.getMarca_id());
            insert.setInt(4, mar.getId());
            act = insert.executeUpdate();
            if (act == 1) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {

        }
        return act;
    }

    public int delete(int id) throws SQLException {
        int del = 0;
        String sqlD = ("DELETE FROM modelo WHERE id=" + id);
        System.out.println(sqlD);
        try {
            con = conectar.getConection();
            insert = con.prepareStatement(sqlD);
            del = insert.executeUpdate();
        } catch (Exception e) {

        }
        return del;
    }

    public ArrayList<Modelo> filtrarModelos(String name) throws SQLException {
        ArrayList<Modelo> modelos = new ArrayList<>();
        String sql = "select * from modelo where modelo.nombre=" + "'" + name + "'";
        System.out.println(sql);
        try {
            con = conectar.getConection();
            insert = con.prepareStatement(sql);
            rs = insert.executeQuery();
            System.out.println(rs);
            while (rs.next()) {
                Modelo m = new Modelo();
                m.setId(rs.getInt(1));
                m.setNombre(rs.getString(2));
                m.setAnio(rs.getInt(3));
                m.setMarca_id((rs.getInt(4)));
                modelos.add(m);
            }
        } catch (Exception e) {
        }
        System.out.println("");
        return modelos;
    }
}
