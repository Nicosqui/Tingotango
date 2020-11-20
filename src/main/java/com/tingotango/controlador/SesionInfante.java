/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tingotango.controlador;

import co.edu.umanizales.listase.excepciones.InfanteExcepcion;
import co.edu.umanizales.listase.modelo.Infante;
import co.edu.umanizales.listase.modelo.NodoInfanteDE;
import co.edu.umanizales.listase.modelo.ListaInfanteDE;
import com.paseoperros.controlador.JsfUtil;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.connector.StateMachineConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;

/**
 *
 * @author carloaiza
 */
@Named(value = "sesionInfante")
@SessionScoped
public class SesionInfante implements Serializable {

    private ListaInfanteDE listaInfantes;
    private Infante infante;
    private String alInicio = "1";
    private boolean deshabilitarFormulario = true;
    private NodoInfanteDE ayudante;
    private String textoVista = "Gráfico";

    private List listadoInfantes;

    private DefaultDiagramModel model;

    private short codigoEliminar;

    private short infanteSeleccionado;

    private Infante infanteDiagrama;

    private Infante infanteMenorEdad;

    /**
     * Creates a new instance of SesionInfante
     */
    public SesionInfante() {
    }

    @PostConstruct
    private void inicializar() {
        
        listaInfantes = new ListaInfanteDE();
        //LLenado de la bds
        listaInfantes.adicionarNodo(new Infante("Carlos", true, (byte) 1));
        listaInfantes.adicionarNodo(new Infante("Diana", false, (byte) 2));
        listaInfantes.adicionarNodo(new Infante("Juanita", false, (byte) 3));
        listaInfantes.adicionarNodo(new Infante("David", true, (byte) 4));
        listaInfantes.adicionarNodo(new Infante("Andrea", false, (byte) 5));
        listaInfantes.adicionarNodo(new Infante("Esteban", true, (byte) 6));
        ayudante = listaInfantes.getCabeza();
        infante = ayudante.getDato();
        //Me llena el objeto List para la tabla
        listadoInfantes = listaInfantes.obtenerListaInfantes();
        pintarLista();
    }

    public Infante getInfanteDiagrama() {
        return infanteDiagrama;
    }

    public void setInfanteDiagrama(Infante infanteDiagrama) {
        this.infanteDiagrama = infanteDiagrama;
    }

    public short getInfanteSeleccionado() {
        return infanteSeleccionado;
    }

    public void setInfanteSeleccionado(short infanteSeleccionado) {
        this.infanteSeleccionado = infanteSeleccionado;
    }

    public Infante getInfanteMenorEdad() {
        return infanteMenorEdad;
    }

    public void setInfanteMenorEdad(Infante infanteMenorEdad) {
        this.infanteMenorEdad = infanteMenorEdad;
    }

    public DiagramModel getModel() {
        return model;
    }

    private Connection createConnection(EndPoint from, EndPoint to, String label) {
        Connection conn = new Connection(from, to);
        conn.getOverlays().add(new ArrowOverlay(20, 20, 1, 1));

        if (label != null) {
            conn.getOverlays().add(new LabelOverlay(label, "flow-label", 0.5));
        }

        return conn;
    }

    public short getCodigoEliminar() {
        return codigoEliminar;
    }

    public void setCodigoEliminar(short codigoEliminar) {
        this.codigoEliminar = codigoEliminar;
    }

    public String getTextoVista() {
        return textoVista;
    }

    public void setTextoVista(String textoVista) {
        this.textoVista = textoVista;
    }

    public List getListadoInfantes() {
        return listadoInfantes;
    }

    public void setListadoInfantes(List listadoInfantes) {
        this.listadoInfantes = listadoInfantes;
    }

    public boolean isDeshabilitarFormulario() {
        return deshabilitarFormulario;
    }

    public void setDeshabilitarFormulario(boolean deshabilitarFormulario) {
        this.deshabilitarFormulario = deshabilitarFormulario;
    }

    public String getAlInicio() {
        return alInicio;
    }

    public void setAlInicio(String alInicio) {
        this.alInicio = alInicio;
    }

    public ListaInfanteDE getListaInfantes() {
        return listaInfantes;
    }

    public void setListaInfantes(ListaInfanteDE listaInfantes) {
        this.listaInfantes = listaInfantes;
    }

    public Infante getInfante() {
        return infante;
    }

    public void setInfante(Infante infante) {
        this.infante = infante;
    }

    public void guardarInfante() {
        //obtiene el consecutivo
        infante.setIdentificador((byte) (listaInfantes.contarNodos() + 1));
        if (alInicio.compareTo("1") == 0) {
            listaInfantes.adicionarAlInicio(infante);
        } else {
            listaInfantes.adicionarNodo(infante);
        }
        //Vuelvo a llenar la lista para la tabla
        listadoInfantes = listaInfantes.obtenerListaInfantes();
        pintarLista();
        deshabilitarFormulario = true;
        JsfUtil.addSuccessMessage("El infante se ha guardado exitosamente");

    }

    public void habilitarFormulario() {
        deshabilitarFormulario = false;
        infante = new Infante();
    }

    public void irSiguiente() {
        if (ayudante.getSiguiente() != null) {
            ayudante = ayudante.getSiguiente();
            infante = ayudante.getDato();
        }
    }

    public void irPrimero() {
        if (listaInfantes.getCabeza() != null) {
            ayudante = listaInfantes.getCabeza();
            infante = ayudante.getDato();

        } else {
            infante = new Infante();
        }
        listadoInfantes = listaInfantes.obtenerListaInfantes();
        pintarLista();

    }

    public void irUltimo() {
        if (listaInfantes.getCabeza() != null) {
            while (ayudante.getSiguiente() != null) {
                ayudante = ayudante.getSiguiente();
            }
            infante = ayudante.getDato();
        }
    }

    public void cambiarVistaInfantes() {
        if (textoVista.compareTo("Tabla") == 0) {
            textoVista = "Gráfico";
        } else {
            textoVista = "Tabla";
        }
    }

    public void invertirLista() {
        //Invierte la lista
        listaInfantes.invertirLista();
        irPrimero();
    }

    public void pintarLista() {
        //Instancia el modelo
        model = new DefaultDiagramModel();
        //Se establece para que el diagrama pueda tener infinitas flechas
        model.setMaxConnections(-1);

        StateMachineConnector connector = new StateMachineConnector();
        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
        model.setDefaultConnector(connector);

        ///Adicionar los elementos
        if (listaInfantes.getCabeza() != null) {
            //llamo a mi ayudante
            NodoInfanteDE temp = listaInfantes.getCabeza();
            int posX = 2;
            int posY = 2;
            //recorro la lista de principio a fin
            while (temp != null) {
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                Element ele = new Element(temp.getDato().getIdentificador() + " "
                        + temp.getDato().getNombre(),
                        posX + "em", posY + "em");
                ele.setId(String.valueOf(temp.getDato().getIdentificador()));
                //adiciona un conector al cuadrito
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));
                model.addElement(ele);
                temp = temp.getSiguiente();
                posX = posX + 5;
                posY = posY + 6;
            }

            //Pinta las flechas            
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(1),
                        model.getElements().get(i + 1).getEndPoints().get(0), "Sig"));
            }

        }
    }

    public void onClickRight() {
        String id = FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get("elementId");

        infanteSeleccionado = Short.valueOf(id.replaceAll("frmInfante:diagrama-", ""));

    }

    public void eliminarInfante() {
        if (codigoEliminar > 0) {
            //llamo el eliminar de la lista
            try {
                listaInfantes.eliminarInfante(codigoEliminar);
                irPrimero();
                JsfUtil.addSuccessMessage("Infante " + codigoEliminar + " eliminado.");
            } catch (InfanteExcepcion e) {
                JsfUtil.addErrorMessage(e.getMessage());
            }
        } else {
            JsfUtil.addErrorMessage("El código a eliminar " + codigoEliminar + " no es válido");
        }
    }

    public void obtenerInfanteDiagrama() {
        try {
            infanteDiagrama = listaInfantes.obtenerInfante(infanteSeleccionado);
        } catch (InfanteExcepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void enviarAlFinal() {
        try {
            ///Buscar el infante y guardar los datos en una variable temporal
            Infante infTemporal = listaInfantes.obtenerInfante(infanteSeleccionado);
            // Eliminar el nodo
            listaInfantes.eliminarInfante(infanteSeleccionado);
            // Adicionarlo al final
            listaInfantes.adicionarNodo(infTemporal);

            pintarLista();
        } catch (InfanteExcepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }

    public void enviarAlInicio() {
        try {
            ///Buscar el infante y guardar los datos en una variable temporal
            Infante infTemporal = listaInfantes.obtenerInfante(infanteSeleccionado);
            // Eliminar el nodo
            listaInfantes.eliminarInfante(infanteSeleccionado);
            // Adicionarlo al inicio
            listaInfantes.adicionarAlInicio(infTemporal);

            pintarLista();
        } catch (InfanteExcepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public void eliminarInfanteConfirmacion() {
        try {
            Infante infTemporal = listaInfantes.obtenerInfante(infanteSeleccionado);
            listaInfantes.eliminarInfante(infanteSeleccionado);
            pintarLista();
        } catch (InfanteExcepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());

        }
    }
}
