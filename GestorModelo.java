package Controlador;

import Modelos.Modelo;
import Modelos.ModeloDao;
import Modelos.Pais;
import Modelos.PaisDao;
import Vistas.VistaModelo;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;


public class GestorModelo implements ActionListener {

    public GestorModelo() {

    }

    VistaModelo vista = new VistaModelo();
    ModeloDao mDao = new ModeloDao();
    Modelo m = new Modelo();
    DefaultTableModel modelo = new DefaultTableModel();

    public GestorModelo(VistaModelo v) throws SQLException {
        this.vista = v;
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnListar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnBuscar.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnAgregar) {
            agregar();
            limpiarTabla();
            try {
                listarModelos(vista.tablaModelo);
            } catch (SQLException ex) {
                Logger.getLogger(GestorModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vista.btnListar) {
            limpiarTabla();
            try {
                listarModelos(vista.tablaModelo);
            } catch (SQLException ex) {
                Logger.getLogger(GestorModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vista.btnActualizar) {
            actualizar();
            limpiarTabla();
            try {
                listarModelos(vista.tablaModelo);
            } catch (SQLException ex) {
                Logger.getLogger(GestorModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == vista.btnModificar) {
            vista.txtId.setEnabled(false);
            int fila = vista.tablaModelo.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila");
            } else {
                int id = Integer.parseInt((String) vista.tablaModelo.getValueAt(fila, 0).toString());
                String nombre = (String) vista.tablaModelo.getValueAt(fila, 1);
                int anio = (int) vista.tablaModelo.getValueAt(fila, 2);
                int marca_id = (int) vista.tablaModelo.getValueAt(fila, 3);

                vista.txtId.setText("" + id);
                vista.txtNombre.setText(nombre);
                vista.txtAnio.setText(anio);
                vista.txtMarca_id.setText(marca_id);
            }
        }
        if (e.getSource() == this.vista.btnEliminar) {
            delete();
            limpiarTabla();
            try {
                listarModelos(vista.tablaModelo);
            } catch (SQLException ex) {
                Logger.getLogger(GestorModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == this.vista.btnBuscar) {
            limpiarTabla();
            try {
                this.buscarModelos(vista.tablaModelo);
            } catch (SQLException ex) {
                Logger.getLogger(GestorModelo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void agregar() {
        String name = this.vista.txtNombre.getText();
        int anio = Integer.parseInt(this.vista.txtAnio.getText());
        int marca_id = Integer.parseInt(this.vista.txtNombre.getText());

        m.setNombre(name);
        m.setAnio(anio);
        m.setMarca_id(marca_id);

        if (this.vista.txtNombre.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se puede agregar sin ingresar todos los datos");
        } else {
            try {
                mDao.agregar(m);
                JOptionPane.showMessageDialog(null, "Modelo se agrego con exito");

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se agregaron los datos");
            }
        }
    }

    public void actualizar() {

        if (vista.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "No se Identifica el Id debe selecionar la opcion Editar");
        } else {
            int id = Integer.parseInt(vista.txtId.getText());
            String name = this.vista.txtNombre.getText();
            int anio = Integer.parseInt(this.vista.txtApellido.getText());
            int marca_id = Integer.parseInt(this.vista.txtRazonSocial.getText());

            m.setId(id);
            m.setNombre(name);
            m.setAnio(anio);
            m.setMarca_id(marca_id);

            int modded = mDao.modificar(m);
            if (modded == 1) {
                JOptionPane.showMessageDialog(vista, "Modelo actualizada con exito");
            } else {
                JOptionPane.showMessageDialog(vista, "Error, no se puede actualizar el Modelo");
            }
        }
    }

    public void listarModelos(JTable tabla) throws SQLException {
        // Esto es para que se ejecute la tabla al momento de iniciar el programa
        modelo = (DefaultTableModel) tabla.getModel();

        List<Modelo> lista = mDao.listarModelos();
        Object[] object = new Object[4];

        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getNombre();
            object[2] = lista.get(i).getAnio();
            object[3] = lista.get(i).getMarca_id();
            modelo.addRow(object);
        }
    }

    public void delete() {
        int fila = vista.tablaModelo.getSelectedRow();
        if (fila == -1) {// de esta manera el usuario solo podra eliminar si selecciona una marca sino no
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un modelo");
        } else {
            try {
                int id = Integer.parseInt((String) vista.tablaModelo.getValueAt(fila, 0).toString());
                mDao.delete(id);
                JOptionPane.showMessageDialog(vista, "Modelo eliminado");
            } catch (SQLException ex) {
                Logger.getLogger(GestorMarca.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void llenarCombo() throws SQLException {
        PaisDao paises = new PaisDao();
        ArrayList<Pais> listarPaises = paises.getPais();
        //vista.cbxPais.removeAllItems();

        for (int i = 0; i < listarPaises.size(); i++) {
            vista.cbxPais.addItem(listarPaises.get(i).getName());
        }
    }

    void limpiarTabla() {
        for (int i = 0; i < vista.tablaModelo.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }
    }

    public void buscarModelos(JTable tablaMarca) throws SQLException {
        // Esto es para que se ejecute la tabla al momento de iniciar el programa
        modelo = (DefaultTableModel) vista.tablaModelo.getModel();
        String name = this.vista.txtBuscar.getText();

        List<Modelo> listaModelos = mDao.buscarModelos(name);
        Object[] object = new Object[4];

        for (int i = 0; i < listaModelos.size(); i++) {
            object[0] = listaModelos.get(i).getId();
            object[1] = listaModelos.get(i).getNombre();
            object[2] = listaModelos.get(i).getAnio();
            object[3] = listaModelos.get(i).getMarca_id();
            modelo.addRow(object);
        }
    }
}
