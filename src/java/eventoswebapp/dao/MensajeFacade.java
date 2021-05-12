/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventoswebapp.dao;

import eventoswebapp.entity.Mensaje;
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
public class MensajeFacade extends AbstractFacade<Mensaje> {

    @PersistenceContext(unitName = "EventosWebAppPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MensajeFacade() {
        super(Mensaje.class);
    }
    public List<Mensaje> findOrdenInverso(int idc){
        Query q=this.em.createQuery("select m from Mensaje m where m.conversacionId.conversacionId = :id order by m.enviado desc");
        q.setParameter("id", idc);
        return q.getResultList();
    }
}
