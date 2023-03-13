package service;

import dao.GenericDao;

@SuppressWarnings("rawtypes")
public abstract class GenericService<T1, T2 extends GenericDao> {
	private Class<T1> entityClass;
	private T2 dao;
	
	public abstract T2 getDao ();
	
	public GenericService (Class <T1> eClass) {
		this.setEntityClass(eClass);
		dao =  getDao ();
	}
	
	@SuppressWarnings("unchecked")
	public void add (T1 entity) {
		dao.create(entity);
	}
	
	@SuppressWarnings("unchecked")
	public void update(T1 entity) {
		dao.update(entity);
	}
	
	public T1 find (int id) {
		@SuppressWarnings("unchecked")
		T1 entity = (T1) dao.find(id);
		return entity;
	}
	
	public Class<T1> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<T1> entityClass) {
		this.entityClass = entityClass;
	}
}
