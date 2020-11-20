/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.tingotango.controlador;

import co.edu.umanizales.listase.modelo.Infante;
import co.edu.umanizales.listase.modelo.ListaInfanteDE;
import org.primefaces.model.diagram.Connection;
import org.primefaces.model.diagram.DefaultDiagramModel;
import org.primefaces.model.diagram.DiagramModel;
import org.primefaces.model.diagram.Element;
import org.primefaces.model.diagram.connector.FlowChartConnector;
import org.primefaces.model.diagram.endpoint.BlankEndPoint;
import org.primefaces.model.diagram.endpoint.EndPoint;
import org.primefaces.model.diagram.endpoint.EndPointAnchor;
import org.primefaces.model.diagram.overlay.ArrowOverlay;
import org.primefaces.model.diagram.overlay.LabelOverlay;
import co.edu.umanizales.listase.modelo.NodoInfanteDE;
import com.paseoperros.controlador.JsfUtil;
import javax.inject.Named;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import org.primefaces.model.diagram.connector.StateMachineConnector;
import co.edu.umanizales.listase.excepciones.*;
import co.edu.umanizales.listase.modelo.Perro;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.ToggleEvent;

/**
 *
 * @author Nicolas Trujillo
 */


@Named(value = "tingoTangoController")
@SessionScoped
public class TingoTangoController implements Serializable {

    private Infante infantGuardar;
    private Infante infantEncontrado;
    private ListaInfanteDE listadoInfantes;
    private ListaInfanteDE tingotango;
    private int totalInfantes;
    private boolean estadoJuego=true;
    private byte cantInfantesJuego;
    private byte numeroOportunidades;
    private String[][] tablaOportunidades;
    private byte posicionIngreso;
    private NodoInfanteDE tempInfanteDE;
    private DefaultDiagramModel model;
    private short infanteSeleccionado;    
    private Infante infanteDiagrama;
    private String nombreTurno;
    
    private int posicionInfante;
    
    //variables para el menu de opciones.
    private String jugadorAEliminar;
    private Infante nuevoJugador = new Infante();
    
    private String msgUltimaAccion;
    
    /**
     * Creates a new instance of TingoTangoController
     */
    public TingoTangoController() {
    }

    @PostConstruct
    public void iniciar() {

        listadoInfantes = new ListaInfanteDE();
//        // Conectaría a un archivo plano o a una base de datos para llenar la
//        Infante Infante1 = new Infante("Pastor", (byte) 1, (byte) 3, "Masculino" );
//        //lista de Infantes
//        this.getListaInfantes().adicionarAlFinalNodoInfanteDE(Infante1);
////        listadoInfantes.adicionarAlFinalNodoInfanteDE(Infante1);
//        listadoInfantes.adicionarNodo(new Infante("Lulú", (byte) 2, (byte) 4, "Femenino"));
//        listadoInfantes.adicionarNodo(new Infante("Firulais", (byte) 3, (byte) 6, "Masculino"));
//
//        listadoInfantes.adicionarNodo(new Infante("Rocky", (byte) 4, (byte) 5, "Masculino"));
//        infantGuardar = listadoInfantes.getCabeza().getDato();
          cargarJugadores();
          nombreTurno = ControladorUsuarios.getUsuarios().get(0).getNombre();
        tempInfanteDE = listadoInfantes.getCabeza();
        

        inicializarModelo();
    }
    
    private void cargarJugadores(){
        listadoInfantes = new ListaInfanteDE();
        for (Infante usuario : ControladorUsuarios.getUsuarios()) {
            
            Infante miJugador = new Infante(usuario.getNombre());
            
            listadoInfantes.adicionarNodo(miJugador);
            
        }
    }

    public Infante getInfantGuardar() {
        return infantGuardar;
    }

    public String getMsgUltimaAccion() {
        return msgUltimaAccion;
    }

    public void setMsgUltimaAccion(String msgUltimaAccion) {
        this.msgUltimaAccion = msgUltimaAccion;
    }
    
    

    public void setInfantGuardar(Infante infantGuardar) {
        this.infantGuardar = infantGuardar;
    }

    public ListaInfanteDE getListadoInfantes() {
        return listadoInfantes;
    }

    public void setListadoInfantes(ListaInfanteDE listadoInfantes) {
        this.listadoInfantes = listadoInfantes;
    }

    public ListaInfanteDE getTingotango() {
        return tingotango;
    }

    public void setTingotango(ListaInfanteDE tingotango) {
        this.tingotango = tingotango;
    }

    public boolean isEstadoJuego() {
        return estadoJuego;
    }

    public void setEstadoJuego(boolean estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    public byte getCantInfantesJuego() {
        return cantInfantesJuego;
    }

    public void setCantInfantesJuego(byte cantInfantesJuego) {
        this.cantInfantesJuego = cantInfantesJuego;
    }

    public byte getNumeroOportunidades() {
        return numeroOportunidades;
    }

    public void setNumeroOportunidades(byte numeroOportunidades) {
        this.numeroOportunidades = numeroOportunidades;
    }

    public String[][] getTablaOportunidades() {
        return tablaOportunidades;
    }

    public void setTablaOportunidades(String[][] tablaOportunidades) {
        this.tablaOportunidades = tablaOportunidades;
    }

    public byte getPosicionIngreso() {
        return posicionIngreso;
    }

    public void setPosicionIngreso(byte posicionIngreso) {
        this.posicionIngreso = posicionIngreso;
    }

    public NodoInfanteDE gettempInfanteDE() {
        return tempInfanteDE;
    }

    public void settempInfanteDE(NodoInfanteDE tempInfanteDE) {
        this.tempInfanteDE = tempInfanteDE;
    }

    public Infante getInfantEncontrado() {
        return infantEncontrado;
    }

    public void setInfantEncontrado(Infante infantEncontrado) {
        this.infantEncontrado = infantEncontrado;
    }

    public int getTotalInfantes() {
        return totalInfantes;
    }

    public void setTotalInfantes(int totalInfantes) {
        this.totalInfantes = totalInfantes;
    }

    public NodoInfanteDE getTempInfanteDE() {
        return tempInfanteDE;
    }

    public void setTempInfanteDE(NodoInfanteDE tempInfanteDE) {
        this.tempInfanteDE = tempInfanteDE;
    }

    public Infante getInfanteDiagrama() {
        return infanteDiagrama;
    }

    public void setInfanteDiagrama(Infante infanteDiagrama) {
        this.infanteDiagrama = infanteDiagrama;
    }

    public int getPosicionInfante() {
        return posicionInfante;
    }

    public void setPosicionInfante(int posicionInfante) {
        this.posicionInfante = posicionInfante;
    }

    public String getJugadorAEliminar() {
        return jugadorAEliminar;
    }

    public void setJugadorAEliminar(String jugadorAEliminar) {
        this.jugadorAEliminar = jugadorAEliminar;
    }

    public String getNombreTurno() {
        return nombreTurno;
    }

    public void setNombreTurno(String nombreTurno) {
        this.nombreTurno = nombreTurno;
    }

    public Infante getNuevoJugador() {
        return nuevoJugador;
    }

    public void setNuevoJugador(Infante nuevoJugador) {
        this.nuevoJugador = nuevoJugador;
    }

    public String irTingo() {
        infantEncontrado = new Infante();
        //pinta
        inicializarModelo();
        return "tingo";
    }
    
    public void irSiguiente() {
        if (tempInfanteDE.getSiguiente() != null) {
            tempInfanteDE = tempInfanteDE.getSiguiente();
            infantGuardar = tempInfanteDE.getDato();
        }
    }

    public void irPrimero() {
        if (listadoInfantes.getCabeza() != null) {
            tempInfanteDE = listadoInfantes.getCabeza();
            infantGuardar = tempInfanteDE.getDato();
            inicializarModelo();
        } else {
            JsfUtil.addErrorMessage("No existen datos en la lista");
        }

    }

    public void irUltimo() {

        tempInfanteDE = listadoInfantes.getCabeza();
        while (tempInfanteDE.getSiguiente() != null) {
            tempInfanteDE = tempInfanteDE.getSiguiente();
        }
        /// Parado en el último nodo
        infantGuardar = tempInfanteDE.getDato();
    }

    public void intercambiar() {
        listadoInfantes.intercambiarExtremos();
        irPrimero();
    }

    public void eliminar() {
        listadoInfantes.eliminarPorPosicion(tempInfanteDE.getDato().getIdentificador());
        irPrimero();
    }

    public void buscarInfante(int datobuscar) {
        infantEncontrado = listadoInfantes.encontrarxPosicionDE(datobuscar).getDato();
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

    public void guardarInfante() {        
//        switch (seleccionUbicacion) {
//            
//            case 1:
//                listadoInfantes.adicionarAlInicio(infantEncontrado);
//                break;
//            case 2:
//                listadoInfantes.adicionarNodo(infantEncontrado);
//                break;
//            default:
//                listadoInfantes.adicionarNodo(infantEncontrado);
//        }
        
        listadoInfantes.adicionarNodo(infantEncontrado);
        
        infantEncontrado = new Infante();
        irPrimero();
        JsfUtil.addSuccessMessage("Se ha adicionado el Infante a la lista");
        setTotalInfantes(this.totalInfantes + 1);
    }
    
    public void agregarJugador() {
        if (!nuevoJugador.getNombre().equals("")) {
            listadoInfantes.adicionarNodo(nuevoJugador);
            inicializarModelo();
        }
    }


    public String listade() {
        infantEncontrado = new Infante();
        //pinta
        inicializarModelo();
        return "listadeInfante";
    }

    public String irCrearInfanteDE() {
        infantEncontrado = new Infante();

        return "crearDE";
    }
    
    public void inicializarModelo() {
        iniciar();
        //Instancia el modelo
        model = new DefaultDiagramModel();
        //Se establece para que el diagrama pueda tener infinitas flechas
        model.setMaxConnections(-1);

        StateMachineConnector connector = new StateMachineConnector();
        connector.setOrientation(StateMachineConnector.Orientation.ANTICLOCKWISE);
        connector.setPaintStyle("{strokeStyle:'#7D7463',lineWidth:3}");
        model.setDefaultConnector(connector);
        
        ///Adicionar los elementos
        if (listadoInfantes.getCabeza() != null) {
            //llamo a mi jugadorActual
            NodoInfanteDE temp = listadoInfantes.getCabeza();

            /**
             * variables para la posicion de los elementos en el diagrama:
             * 
             *      'posY' y 'posX': indican el centro del circulo
             *      'numElementos': indica el total de los puntos que se van a distribuir
             *      'angle': indica el ángulo en el que se dibujará el punto
             *      'cont': indica el número 
             */
            double posX;
            double posY;
            int numElementos = listadoInfantes.contarNodos();
            double angle;
            int cont = 0;//
            
            
            //recorro la lista de principio a fin
            do {
                

                //calculando la posición del elemento:
                angle = (2 * Math.PI * cont) / numElementos;
                
                //para acomodar el primer jugador a 90 grados de la horizontal:
                //angle += (1.5 * Math.PI);

                posX = 35 + (15 * Math.cos(angle));
                posY = 15 + (15 * Math.sin(angle));
                cont++;
                
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                Element ele = new Element(temp.getDato().getNombre(),
                        posX + "em", posY + "em");
                ele.setId(String.valueOf(temp.getDato().getNombre()));
                
                //adiciona puntos de anclaje al elemento de acuerdo al ángulo:
                if (angle > 0 && angle < Math.PI) {
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                } else if (angle > Math.PI && angle < 2 * Math.PI) {
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                } else if (angle == Math.PI) {
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                } else if (angle == 0){
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.LEFT));
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.RIGHT));
                } else {
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                    ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                }
//                
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));
//
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_LEFT));
//                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));

                //si es el primer elemento, añadirlo a la clase css que lo pone morado.
//                if (temp == listaJugadores.getCabeza()) {
//                    ele.setStyleClass("ui-diagram-primero");
//                }
                
                model.addElement(ele);

                temp = temp.getSiguiente();

            } while (temp != listadoInfantes.getCabeza());

            //Pinta las flechas            
            for (int i = 0; i < model.getElements().size() - 1; i++) {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(0),
                        model.getElements().get(i + 1).getEndPoints().get(0), "Sig"));

                model.connect(createConnection(model.getElements().get(i + 1).getEndPoints().get(1),
                        model.getElements().get(i).getEndPoints().get(1), "Ant"));
            }
            
            //Pinta flechas del último elemento al primero:
            model.connect(createConnection(model.getElements().get(model.getElements().size()-1).getEndPoints().get(0),
                        model.getElements().get(0).getEndPoints().get(0), "Sig"));
            
            model.connect(createConnection(model.getElements().get(0).getEndPoints().get(1),
                        model.getElements().get(model.getElements().size()-1).getEndPoints().get(1), "Ant"));
            
            
            //pone un elemento en el centro del diagrama con las fichas en la caja
            model.addElement(new Element("Tango: " + tempInfanteDE.getDato().getNombre(), 35 + "em", 15 + "em"));
            
            //XXX: Por alguna razón, si se añade este elemento, 
            //desaparecen las conecciones del resto del diagrama:
            //model.addElement(new Element("Turno", 70 + "em", 15 + "em"));
        }
        
        ///Adicionar los elementos
        if (listadoInfantes.getCabeza() != null) {
            //llamo a mi ayudante
            NodoInfanteDE temp = listadoInfantes.getCabeza();
            int posX=2;
            int posY=2;
            //recorro la lista de principio a fin
            while(temp !=null)
            {
                //Parado en un elemento
                //Crea el cuadrito y lo adiciona al modelo
                Element ele = new Element(temp.getDato().getIdentificador()+" "+
                        temp.getDato().getNombre(), 
                        posX+"em", posY+"em");
                ele.setId(String.valueOf(temp.getDato().getIdentificador()));
                //adiciona un conector al cuadrito
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.TOP));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_RIGHT));
                
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM_LEFT));
                ele.addEndPoint(new BlankEndPoint(EndPointAnchor.BOTTOM));
                model.addElement(ele);                    
                temp=temp.getSiguiente();
                posX=  posX+5;
                posY= posY+6;
            }            
           
            //Pinta las flechas            
            for(int i=0; i < model.getElements().size() -1; i++)
            {
                model.connect(createConnection(model.getElements().get(i).getEndPoints().get(1), 
                        model.getElements().get(i+1).getEndPoints().get(0), "Sig"));
                
                
                model.connect(createConnection(model.getElements().get(i+1).getEndPoints().get(2), 
                        model.getElements().get(i).getEndPoints().get(3), "Ant"));
            }
            
        }
    }
    
    public void pasarTurno() throws InfanteExcepcion {
        this.nombreTurno = tempInfanteDE.getSiguiente().getDato().getNombre();
    }
    
    public void obtenerPosicionInfante()
    {
        try {
            posicionInfante = listadoInfantes.obtenerPosicionInfante(infanteSeleccionado);
        } catch (InfanteExcepcion ex) {
            JsfUtil.addErrorMessage(ex.getMessage());
        }
    }
    
    public String irCrearInfante() {
        infantEncontrado = new Infante();
        iniciar();
        return "crearInfante";
    }
    
    public void eliminarJugador() throws InfanteExcepcion {
        listadoInfantes.eliminarNodo(jugadorAEliminar);
        inicializarModelo();
    }
    
    public void hacerTango() throws InfanteExcepcion {

        double randomDouble = Math.random();
        randomDouble = randomDouble * 10 + 1;
        int randomInt = (int) randomDouble;
        
        int a = 0;
        while(a<randomInt){
            pasarTurno();
            a++;
            
        }
        NodoInfanteDE temp = new NodoInfanteDE();
        temp = tempInfanteDE;
        listadoInfantes.eliminarNodo(tempInfanteDE.getDato().getNombre());

        tempInfanteDE = temp.getSiguiente();
        listadoInfantes.setCabeza(tempInfanteDE);


        if (listadoInfantes.contarNodos() == 1) {
            estadoJuego = false;
        }
        
        
        inicializarModelo();
    }
    
    public void verSiguiente() {
        listadoInfantes.setCabeza(listadoInfantes.getCabeza().getSiguiente());
        inicializarModelo();
    }

    public void verAnterior() {
        listadoInfantes.setCabeza(listadoInfantes.getCabeza().getAnterior());
        inicializarModelo();
    }
    
    public void handleToggle(ToggleEvent event) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggled", "Visibility:" + event.getVisibility());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
}
