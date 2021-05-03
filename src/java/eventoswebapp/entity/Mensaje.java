/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author migue
 */
@Entity
@Table(name = "MENSAJE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mensaje.findAll", query = "SELECT m FROM Mensaje m")
    , @NamedQuery(name = "Mensaje.findByMensajeId", query = "SELECT m FROM Mensaje m WHERE m.mensajeId = :mensajeId")
    , @NamedQuery(name = "Mensaje.findByTexto", query = "SELECT m FROM Mensaje m WHERE m.texto = :texto")
    , @NamedQuery(name = "Mensaje.findByEnviado", query = "SELECT m FROM Mensaje m WHERE m.enviado = :enviado")})
public class Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "MENSAJE_ID")
    private Integer mensajeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 280)
    @Column(name = "TEXTO")
    private String texto;
    @Column(name = "ENVIADO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date enviado;
    @JoinColumn(name = "CONVERSACION_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario conversacionId;
    @JoinColumn(name = "REMITENTE_ID", referencedColumnName = "USUARIO_ID")
    @ManyToOne(optional = false)
    private Usuario remitenteId;

    public Mensaje() {
    }

    public Mensaje(Integer mensajeId) {
        this.mensajeId = mensajeId;
    }

    public Mensaje(Integer mensajeId, String texto) {
        this.mensajeId = mensajeId;
        this.texto = texto;
    }

    public Integer getMensajeId() {
        return mensajeId;
    }

    public void setMensajeId(Integer mensajeId) {
        this.mensajeId = mensajeId;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getEnviado() {
        return enviado;
    }

    public void setEnviado(Date enviado) {
        this.enviado = enviado;
    }

    public Usuario getConversacionId() {
        return conversacionId;
    }

    public void setConversacionId(Usuario conversacionId) {
        this.conversacionId = conversacionId;
    }

    public Usuario getRemitenteId() {
        return remitenteId;
    }

    public void setRemitenteId(Usuario remitenteId) {
        this.remitenteId = remitenteId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (mensajeId != null ? mensajeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mensaje)) {
            return false;
        }
        Mensaje other = (Mensaje) object;
        if ((this.mensajeId == null && other.mensajeId != null) || (this.mensajeId != null && !this.mensajeId.equals(other.mensajeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "eventoswebapp.entity.Mensaje[ mensajeId=" + mensajeId + " ]";
    }
    
}
