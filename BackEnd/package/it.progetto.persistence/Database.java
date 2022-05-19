package it.progetto.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.ParameterMode;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import it.progetto.entity.Auto;
import it.progetto.entity.Categoria;
import it.progetto.entity.Cliente;
import it.progetto.entity.Noleggia;
import it.progetto.entity.Notifiche;
import it.progetto.entity.Staff;

public class Database {
	
	private static Database database = null;
	private EntityManagerFactory em =  Persistence.createEntityManagerFactory("autonoleggio");
	private EntityManager manager = em.createEntityManager();
	
	private Database() {
		
	}
	
	public static Database getIstance() {
		if(database == null) {
			database = new Database();
		}
		
		return database;
	}
	
	public void addCliente(Cliente cliente) {
		manager.getTransaction().begin(); //Database db= new Database()
		manager.persist(cliente); //insert into
		manager.getTransaction().commit(); //st.executeUpdate()
		System.out.println("cliente aggiunto al db");
	}
	
	public void addCategoria(Categoria c) {
		manager.getTransaction().begin(); //Database db= new Database()
		manager.persist(c); //insert into
		manager.getTransaction().commit(); //st.executeUpdate()
		System.out.println("Categoria aggiunto al db");
	}
	
	public Cliente getClienteById(int id) {
		manager.getTransaction().begin();
		Cliente c = manager.find(Cliente.class, id); // select * from user where user_id = id
		manager.getTransaction().commit();
		return c;
		
	}
	
	public Categoria getCategoriaById(int id) {
		manager.getTransaction().begin();
		Categoria c = manager.find(Categoria.class, id); // select * from user where user_id = id
		manager.getTransaction().commit();
		return c;
		
	}
	
	public Staff getStaffById(int id) {
		manager.getTransaction().begin();
		Staff s = manager.find(Staff.class, id); // select * from user where user_id = id
		manager.getTransaction().commit();
		return s;
		
	}
	
	public void updateStaff(Staff staff) {
		manager.getTransaction().begin();
		manager.merge(staff);
		manager.getTransaction().commit();
		
	}
	
	
	
	public void updateCategoria(Categoria cate) {
		manager.getTransaction().begin();
		manager.merge(cate);
		manager.getTransaction().commit();
		
	}
	
	public void removeCliente(Cliente cliente) {
		manager.getTransaction().begin();
		manager.remove(cliente);
		manager.getTransaction().commit();
	}
	
	public void removeCategoria(Categoria c) {
		manager.getTransaction().begin();
		manager.remove(c);
		manager.getTransaction().commit();
	}
	
	public List<Categoria> getAllCategoria(){
		Query sql = manager.createQuery("SELECT c FROM Categoria c ");
		return sql.getResultList();
	}
	
	
	
	public List<Cliente> getAllClienteAttivo(){
		Query sql = manager.createQuery("SELECT c FROM Cliente c WHERE c.stato = 1 ");
		return sql.getResultList();
	}
	
    public List<Auto> getAllAuto(){
		
		Query sql= manager.createQuery("SELECT a FROM Auto a ");
		
		
		return sql.getResultList();
		
	}

    public List<Noleggia> getNoleggiByCliente(int id){
	
	Query sql= manager.createQuery("SELECT n FROM Noleggia n WHERE n.idCliente = ?1 ORDER BY n.idAuto");
	sql.setParameter(1, id);
	
	return sql.getResultList();
	
}
    
    public List<Staff> getStaffNamedQuery(){
		Query sql = manager.createNamedQuery("Staff.findAll");
		return sql.getResultList();
		
	}
    
    public List<Noleggia> getAllNoleggi(){
		Query sql = manager.createQuery("SELECT n FROM Noleggia n ");
		return sql.getResultList();
	}
    
    public List<Categoria> getCategoriaByName(String nome){
		Query sql = manager.createQuery("SELECT c FROM Categoria c WHERE c.nome = :nome");
		sql.setParameter("nome", nome);
		return sql.getResultList();
	}
	
	public void addAuto(Auto a) {
		manager.getTransaction().begin();
		manager.persist(a);
		manager.getTransaction().commit();
	}
	
	
	
	public List<Auto> getAutoNamedQuery(){
		Query sql = manager.createQuery("SELECT a FROM Auto a ORDER BY a.categoria");
		return sql.getResultList();
		
	}
	
	
	
	public List<Cliente> getAllClienteDaApprovare(){
		Query sql = manager.createQuery("SELECT c FROM Cliente c WHERE c.stato = 0 ");
		return sql.getResultList();
	}
	
	
	
	public void removeNoleggio(Noleggia n) {
		manager.getTransaction().begin();
		manager.remove(n);
		manager.getTransaction().commit();
	}
	
	
	
	public void addStaff(Staff s) {
		manager.getTransaction().begin();
		manager.persist(s);
		manager.getTransaction().commit();
	}
	
	public List<Staff> getAllStaff(){
		Query sql = manager.createQuery("SELECT s FROM Staff s ");
		return sql.getResultList();
	}
	
	public void removeStaff(Staff s) {
		manager.getTransaction().begin();
		manager.remove(s);
		manager.getTransaction().commit();
	}
	
	
	public List<Noleggia> getNoleggioByNumero(int numero,String data){
		
    	Query query = manager.createNativeQuery("{call noleggioByNumero(?,?)}",
                Noleggia.class)           
                .setParameter(1, numero)
                .setParameter(2, data);

        List<Noleggia> result = query.getResultList();
        
        return result;
	}
	
	
	
	public void addNoleggio(Noleggia n) {
		
		manager.getTransaction().begin(); 
		manager.createNativeQuery("INSERT INTO noleggia (data_inizio, data_fine, id_cliente, id_auto , n_prenotazione , totale) VALUES (?,?,?,?,?,?)")
	      .setParameter(1, n.getDataInizio())
	      .setParameter(2, n.getDataFine())
	      .setParameter(3, n.getIdCliente())
	      .setParameter(4, n.getIdAuto())
	      .setParameter(5, n.getNumeroPrenotazione())
	      .setParameter(6, n.getTotale())
	      .executeUpdate();
		manager.getTransaction().commit(); 
		System.out.println("Noleggio aggiunta al db");
	}
	
	public Auto getAutoById(int id) {
		manager.getTransaction().begin();
		Auto a = manager.find(Auto.class, id); 
		manager.getTransaction().commit();
		return a;
		
		
	}
	
	
	
	public List<Auto> getAutoByCategoria(int id) {
		Query sql= manager.createQuery("SELECT a  FROM Auto a WHERE a.categoria.id = :id");
		sql.setParameter("id", id);
		
		return sql.getResultList();
		
	}
	
	public void updateAuto(Auto auto) {
		manager.getTransaction().begin();
		manager.merge(auto);
		manager.getTransaction().commit();
		
	}
	
	public void removeAuto(Auto auto) {
		manager.getTransaction().begin();
		manager.remove(auto);
		manager.getTransaction().commit();
	}
	
	public List<Auto> getAllAutoAvailable(int id){
		
		Query sql= manager.createQuery("SELECT a FROM Auto a WHERE a.id_auto not ?1");
		sql.setParameter(1, id);
		
		return sql.getResultList();
		
	}
	
	public double getPrezzoFromCategoria(int id){
		List<Categoria> categoria = null;
		double prezzo= 0.0;
		Query sql= manager.createQuery("SELECT c FROM Categoria c  WHERE c.id = ?1");
		sql.setParameter(1, id);
		categoria = sql.getResultList();
		for(Categoria c : categoria) {
			prezzo = c.getPrezzo();
		}
		return prezzo;
	}
	
	public List<Noleggia> getNoleggiByData(String dataInizio , String dataFine){
		Query query = manager.createNativeQuery("{call noleggiAttivi(?,?)}",
                Noleggia.class)           
                .setParameter(1, dataInizio)
                .setParameter(2, dataFine);

        List<Noleggia> result = query.getResultList();
        
        return result;
	}
	
	

   public List<Noleggia> cercaNoleggioByNumero(int numero){
	Query sql = manager.createQuery("SELECT n FROM Noleggia n WHERE n.numeroPrenotazione = :numero ");
	sql.setParameter("numero", numero);
	return sql.getResultList();
    }
   
   
	public List<Noleggia> getNoleggiByAuto(){
		Query sql = manager.createQuery("SELECT n FROM Noleggia n ORDER BY n.idAuto ");
		return sql.getResultList();
	}
	
	
	public void deleteNoleggioByAutoAndData(int id , String data){
	 StoredProcedureQuery query = manager
   		    .createStoredProcedureQuery("deleteNoleggi")
   		    .registerStoredProcedureParameter(1, int.class, 
   		        ParameterMode.IN)
   		    .registerStoredProcedureParameter(2, String.class, 
   		        ParameterMode.IN)
   		    .setParameter(1, id)
               .setParameter(2, data);

   		query.execute();
       
       
	}
	
	
   public List<Auto> getAllAutoManutenzione(){
		Query sql = manager.createQuery("SELECT a FROM Auto a WHERE a.disponibile = 1 ");
		return sql.getResultList();
	}
   
   public List<Auto> getAllAutoDisponibili(){
		Query sql = manager.createQuery("SELECT a FROM Auto a WHERE a.disponibile = 0 ");
		return sql.getResultList();
	}
   
   public List<Categoria> getPrezzoFromCategoria(){
		
		
		Query sql= manager.createQuery("SELECT c FROM Categoria c");
		
		return sql.getResultList();
	}
   
   
   
   public void addNotifica(Notifiche n) {
		manager.getTransaction().begin(); //Database db= new Database()
		manager.persist(n); //insert into
		manager.getTransaction().commit(); //st.executeUpdate()
		System.out.println("notifica aggiunto al db");
	}
   
   
   public List<Notifiche> getAllNotifiche(){
		
		
		Query sql= manager.createQuery("SELECT n FROM Notifiche n");
		
		return sql.getResultList();
	}
   
   public Notifiche getNotificaById(int id) {
		manager.getTransaction().begin();
		Notifiche n = manager.find(Notifiche.class, id); 
		manager.getTransaction().commit();
		return n;
		
		
	}
   
   public void removeNotifica(Notifiche not) {
		manager.getTransaction().begin();
		manager.remove(not);
		manager.getTransaction().commit();
	}
   
   
   


    
    
    

}
