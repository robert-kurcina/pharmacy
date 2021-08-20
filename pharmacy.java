class Prescription {
	String id;
	String label;
	BigDecimal price;
	boolean isFulfilled;
}

class Issue {
	String id;
	String label;
	boolean isFulfilled;
}

enum Roles {
	MANAGER, CASHIER, PHARMACIST
}

interface IRole {
	String getRoleName();
}

interface IPerson {
	void setId(String id);
	void setPersonName(String personName);
	String getPersonName();
	String getId();
}

class Customer implements IPerson {
	List<Issue> issues;
	List<Prescription> prescriptions;
	BigDecimal walletAmount;
}

interface IEmployee extends IPerson {
	void setManager(Manager manager);
	void setRoles(List<Roles> roles);
	void setAvailability(boolean isAvailable);
	void processCustomer(Customer customer);
	boolean removeRoles(List<Roles> roles);
	Manager getManager();
	List<Roles> getRoles();
}

class Cashier implements IEmployee, IRole {
	String roleName = Roles.CASHIER;
	boolean isAvailable = true;
}

class Pharmacist implements IEmployee, IRole {
	String roleName = Roles.PHARMACIST;
	boolean isAvailable = true;
}

class Manager implements IEmployee, IRole {
	String roleName = Roles.MANAGER;
	boolean isAvailable = true;

	List<Employee> managedEmployees = new ArrayList<Employee>();
	void addManagedEmployee(Employee employee);
	boolean removeManagedEmployee(Employee employee);
    boolean assignRole(Employee employee); 
    boolean assignManager(Employee employee);
	List<Employee> getManagedEmployees();
}

interface IProcessQueue {
	void enqueueCustomer(Customer customer);
	void removeCustomer(Customer customer);
	void dequeueCustomer();
	void appendHistory();
	List<Customer> getCustomerQueue();
}

class ProcessorQueue implements IProcessQueue {
	Pharmacy pharmacy;
	List<Customer> queue = new ArrayList<Customer>();
}

interface IPharmacy {
	void addStaffMember(Employee employee);
	boolean removeStaffMember(Employee employee);

	void addPrescriptionHistory(Prescription prescription, Cashier cashier);
	void cancelPrescriptionHistory(Prescription prescription, Manager manager);

	void addIssueHistory(Issue issue, Manager manager);
	void cancelIssueHistory(Issue issue, Manager manager);
	
	ProcessorQueue decideCustomerQueue(Customer customer);
	Employee findEmployeeForCustomer(Customer customer);
	boolean assignCustomerToEmployee(Customer customer, Employee employee);

	List<Employee> getStaffMembers();
	List<String> getHistory();
}

class Pharmacy implements IPharmacy {
	BigDecimal cash;
	List<Customer> customerQueue;
	List<Employee> staffMembers;
	List<String> history;

	ProcessorQueue mainQueue = new ProcessorQueue(this);
	ProcessorQueue cashierQueue = new ProcessorQueue(this);
	ProcessorQueue prescriptionQueue = new ProcessorQueue(this);
	ProcessorQueue issuesQueue = new ProcessorQueue(this);
	ProcessorQueue completedQueue = new ProcessorQueue(this);
}