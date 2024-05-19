import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PersonCollectionImpl implements PersonCollection {
	
	static class PeopleEmailSorted{
		
		private TreeMap<String, Person> data;
		
		public PeopleEmailSorted() {
			data = new TreeMap<>();
		}
		
		public void add(Person person) {
			data.put(person.getEmail(), person);
		}
		
		public void delete(Person person) {
			data.remove(person).getEmail();
		}
		
		public List<Person> getSorted(){
			List<Person> sorted = new ArrayList<>();
			for(Map.Entry<String, Person> entry : data.entrySet()) {
				sorted.add(entry.getValue());
			}
			
			return sorted;
		}
	}
	
	static class PeopleAgeSorted{
		
		private TreeMap<Integer, PeopleEmailSorted> data;
		
		public PeopleAgeSorted() {
			data = new TreeMap<>();
		}
		
		public void add(Person person) {
			PeopleEmailSorted peopleByAge = data.get(person.getAge());
			
			if(peopleByAge == null) {
				peopleByAge = new PeopleEmailSorted();
				data.put(person.getAge(), peopleByAge);
			}
			peopleByAge.add(person);
		}
		
		public List<Person> getSorted(int startAge, int endAge){
			List<Person> sorted = new ArrayList<>();
			
			for(Map.Entry<Integer, PeopleEmailSorted> entry : data.entrySet()) {
				if(entry.getKey() >= startAge && entry.getKey() <= endAge)
				sorted.addAll(entry.getValue().getSorted());
			}
			return sorted;
		}
		
	}

	private Map<String, Person> peopleByEmail = new HashMap<>();
	private Map<String, PeopleEmailSorted> peopleByEmailDomain = new HashMap<>();
	private Map<String, PeopleEmailSorted> peopleByNameAndTown = new HashMap<>();
	private PeopleAgeSorted peopleAgeSorted = new PeopleAgeSorted();
	private Map<String, PeopleAgeSorted> peopleByTownAndAge = new HashMap<>();
	
    @Override
    public boolean add(String email, String name, int age, String town) {
       if(peopleByEmail.containsKey(email)) return false;
       Person person = new Person(email, name, age, town);
       peopleByEmail.put(email, person );
       
       addToIndices(person);
       
       return true;
    }
    
    private void addToIndices(Person person) {
    	String domain = person.getEmail().substring(person.getEmail().indexOf("@") + 1);
        PeopleEmailSorted peopleInDomainByEmail = peopleByEmailDomain.get(domain);
        
        if(peopleInDomainByEmail == null) {
     	   peopleInDomainByEmail = new PeopleEmailSorted();
     	   peopleByEmailDomain.put(domain, peopleInDomainByEmail);
        }
        peopleInDomainByEmail.add(person);
        
        String nameAndTown = person.getName() + "*" + person.getTown();
        PeopleEmailSorted peopleInTownWithName = peopleByNameAndTown.get(nameAndTown);
        
        if(peopleInTownWithName == null) {
        	peopleInTownWithName = new PeopleEmailSorted();
        	peopleByNameAndTown.put(nameAndTown, peopleInTownWithName);
        }
        peopleInTownWithName.add(person);
        
        peopleAgeSorted.add(person);
        
        PeopleAgeSorted peopleAgeSorted = peopleByTownAndAge.get(person.getTown());
        
        if(peopleAgeSorted == null) {
        	peopleAgeSorted = new PeopleAgeSorted();
        	peopleByTownAndAge.put(person.getTown(), peopleAgeSorted);
        }
        peopleAgeSorted.add(person);
    }

    @Override
    public int getCount() {
        return peopleByEmail.size();
    }

    @Override
    public boolean delete(String email) {
        Person deleted = peopleByEmail.remove(email);
        return deleted != null;
    }

    @Override
    public Person find(String email) {
        return peopleByEmail.get(email);
    }

    @Override
    public Iterable<Person> findAll(String emailDomain) {
        PeopleEmailSorted peopleByEmail = peopleByEmailDomain.get(emailDomain);
        
        if(peopleByEmail == null) {
        	return new ArrayList<>();
        }
        
        return peopleByEmail.getSorted();
    }

    @Override
    public Iterable<Person> findAll(String name, String town) {
    	String nameAndTown = name + "*" + town;
        PeopleEmailSorted result = peopleByNameAndTown.get(nameAndTown);
        
        if(result == null) {
        	return new ArrayList<>();
        }
        
        return result.getSorted();
    }

    @Override
    public Iterable<Person> findAll(int startAge, int endAge) {
        List<Person> result = peopleAgeSorted.getSorted(startAge, endAge);
        
        if(result == null) {
        	return new ArrayList<>();
        }
        return result;
    }

    @Override
    public Iterable<Person> findAll(int startAge, int endAge, String town) {
        PeopleAgeSorted result = peopleByTownAndAge.get(town);
        
        if(result == null) {
        	return new ArrayList<>();
        }
        return result.getSorted(startAge, endAge);
        
    }
}
