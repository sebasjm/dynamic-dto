package ar.com.sebasjm.dev.dynamicdto;


public interface DynamicDTOControl {
	
	public String[] names();
	
	public Object[] values();
	
	public <T> T fillInstance(T entity);
	
	public DynamicDTOControl addData(Class dtoInterface, Object resource);
	
	public DynamicDTOControl removeData(Class dtoInterface);
}
