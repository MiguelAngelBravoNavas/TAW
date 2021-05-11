/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.dao;

import eventoswebapp.entity.Conversacion;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author migue
 */
@Stateless
public class ConversacionFacade extends AbstractFacade<Conversacion> {

    @PersistenceContext(unitName = "EventosWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConversacionFacade() {
        super(Conversacion.class);
    }
    
    public Conversacion findByUserAndTele(int u, int t) {
        Query q;
        List<Conversacion> lista;
        Conversacion c = null;

        q=this.em.createQuery("SELECT c FROM Conversacion c WHERE c.usuarioId.usuarioId = :u AND c.teleoperadorId.usuarioId = :t");
        q.setParameter("u", u); 
        q.setParameter("t", t);

        lista = q.getResultList(); 
        if (lista != null && !lista.isEmpty()) { 
           c=lista.get(0);
        }
        return c;
    }
    
}
