package accenturebank.com.accentureBank.exceptions;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.MultiValueMap;

public class ErrorModel implements MultiValueMap<String, String> {
    private String msgError;
    
    
    public ErrorModel() {
    	}


	public ErrorModel(String msgError) {
		super();
		this.msgError = msgError;
	}


	public String getMsgError() {
		return msgError;
	}


	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}


	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean containsKey(Object key) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean containsValue(Object value) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public List<String> get(Object key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<String> put(String key, List<String> value) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<String> remove(Object key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void putAll(Map<? extends String, ? extends List<String>> m) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Set<String> keySet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Collection<List<String>> values() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Set<Entry<String, List<String>>> entrySet() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getFirst(String key) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void add(String key, String value) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addAll(String key, List<? extends String> values) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void addAll(MultiValueMap<String, String> values) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void set(String key, String value) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setAll(Map<String, String> values) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Map<String, String> toSingleValueMap() {
		// TODO Auto-generated method stub
		return null;
	}
    
    
}