package Core;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


/**
 * 
 * @author hafeez
 * This class generates a policy
 */

public class Policy implements Comparable<Policy> {
	

	//Local variables
	protected Integer policyid=0;
	protected String name="X";
	protected String ipAddress="X";
	protected String macAddress="X";
	protected int port=1;
	protected int vlanID=1;
	protected int priority=0;
	protected String decision="X";
	
	
	protected short ethtype;
	protected byte protocol;
	protected short ingressport;
	protected String ipdst;
	protected int ipsrc;
	protected String ethsrc;
	protected String ethdst;
	protected short tcpudpsrcport;
	protected short tcpudpdstport;
	
	/**
	 * Default Constructor
	 */
	
	public Policy(){
		this.policyid=-1;
		this.name=null;
		this.ipAddress=null;
		this.macAddress=null;
		this.port=-1;
		this.vlanID=-1;
		this.priority=-1;
		this.decision=null;
	}
	
	/**
	 * Copy constructor
	 */
	
	public Policy (Policy policy){
		this.policyid=policy.policyid;
		this.name=policy.name;
		this.ipAddress=policy.ipAddress;
		this.macAddress=policy.macAddress;
		this.port=policy.port;
		this.vlanID=policy.vlanID;
		this.priority=policy.priority;
		this.decision=policy.decision;
	}
	
	@Override
	public final String toString(){
		String NEW_LINE= System.getProperty("line.separator");
		return "PolicyID: "+policyid+NEW_LINE
				+"Policy Name: " +name+NEW_LINE
				+"Source IP Address: "+ipAddress+NEW_LINE
				+"Source MAC Address: "+macAddress+NEW_LINE
				+"Destination Port Number: "+port+NEW_LINE
				+"Vlan ID: "+vlanID+NEW_LINE
				+"Priority: "+priority+NEW_LINE
				+"Decision: "+decision+NEW_LINE;
	}
	
	/**
	 * Policy comparison
	 * @param policy
	 * @return
	 */
	
	@Override
	public int compareTo(Policy policy) {
		return this.priority - ((Policy) policy).priority;
	}
	
	/**
	 * Generate Unique ID for policy
	 * @param: primeNumber: integer
	 * @return: Policy ID: integer
	 */
	
	public Integer generateID(int primeNumber){
		final int prime=primeNumber;
		Integer id=super.hashCode();
		id = prime* id + (int) policyid;
		if (name!=null){
			id= prime*id + name.hashCode();
		}
		
		if (id<0){
			id=id*15551;
			id=Math.abs(id);
		}
		return id;
	}
	
	
	/**
	 * Changes  priority of policy of caller policy
	 * @param policyStorage: HashMap
	 * @param priority: integer
	 */
	
	protected void changePolicyPriority(HashMap<Integer, Policy> policyStorage, int priority){
		Policy pol=null;
		//get appropriate policy
		try{
			
			pol= (Policy) policyStorage.get(this.policyid);
		} catch (NullPointerException e) {
			System.out.println("Unable to get Policy from storage");
//			e.printStackTrace();
		}
		
		//change priority
		pol.vlanID=priority;
		//updates the policy storage
		policyStorage.put(this.policyid, pol);
		// We can optionally commit these changes in the policy file by writing policy storage to file, 
		// other wise we can do it later too.
	}
	
	/**
	 * Changes  decision for caller policy
	 * @param policyStorage: HashMap
	 * @param priority: integer
	 */
	
	protected void changePolicyDecision(HashMap<Integer, Policy> policyStorage, String decision){
		//get the appropriate policy
		Policy pol=null;
		try{
			
			pol= (Policy) policyStorage.get(this.policyid);
		} catch (NullPointerException e) {
			System.out.println("Unable to get Policy from storage");
//			e.printStackTrace();
		}
		
		//change decision
		if (pol!=null){
			pol.decision=decision;
		} else {
			System.out.println("Mango");
		}
		
		//updates the policy storage
		policyStorage.put(this.policyid, pol);
		// We can optionally commit these changes in the policy file by writing policy storage to file, 
		// other wise we can do it later too.
	}
	
	/**
	 * Changes name of policy
	 * @param policyStorage: HashMap
	 * @param Name: String
	 */
	
	protected void changePolicyName(HashMap<Integer, Policy> policyStorage, String name){
	
		Policy pol=null;
		//get appropriate policy
		try{
			pol= (Policy) policyStorage.get(this.policyid);
		} catch (NullPointerException e) {
			System.out.println("Unable to get Policy from storage");
//			e.printStackTrace();
		}
		//change priority
		pol.name=name;
		
		//updates the policy storage
		policyStorage.put(this.policyid, pol);
		Policy check=(Policy) policyStorage.get(this.policyid);
		System.out.println("Name in function is "+check.name);
	}
	
	/**
	 * This function generates one copy of calling policy, generate a unique ID for it and adds it to HashMap
	 * @param policyStorage : HashMap
	 * @param policyToBeCloned : Policy
	 * @return Policy ID of newly added policy :Integer
	 */
	
	protected Integer generatePolicyCopy(HashMap<Integer, Policy> policyStorage){
		
			//get the appropriate policy
			Policy pol= new Policy (this);
			
			//change priority
			pol.policyid=generateID(3167);
			
			//updates the policy storage
			policyStorage.put(pol.policyid, pol);
			
			//return the policy ID of newly added policy
			return pol.policyid;
		
		
	}
	
	
	/**
	 * Write policy to a file
	 * @param csvWriter: CSVWrite: OpenCSV library
	 */
	protected static void writePolicy (Policy policy, CSVWriter csvWriter){
		//get a list of entries in policy
		List<String> policy_array= new ArrayList<String>();
		//add all entries to the policy
		policy_array.add(String.valueOf(policy.policyid));
		policy_array.add(policy.name);
		policy_array.add(policy.ipAddress);
		policy_array.add(policy.macAddress);
		policy_array.add(String.valueOf(policy.port));
		policy_array.add(String.valueOf(policy.vlanID));
		policy_array.add(String.valueOf(policy.priority));
		policy_array.add(policy.decision);
		
		//convert list to string array
		String[] entries= policy_array.toArray(new String[7]);
		//Write to the writer
		csvWriter.writeNext(entries);
		//Explicitly close the CSV Writer outside this function
	}
	
	/**
	 * Finds a policy from a file
	 * @param csvReader:CSVReader
	 * @param returns Policy Policy
	 * @throws IOException 
	 */
	protected static Policy findPolicy(CSVReader csvReader, int policyID) throws IOException{
		//Read policy entry to a policy in a list
		String [] policy_str;
		
		boolean found=false;
		do {
			//if policy is read
			if ((policy_str= csvReader.readNext())!=null){
				//if required policy is found
				if (Integer.parseInt(policy_str[0])==policyID){
					Policy pol= new Policy();
					pol.decision=policy_str[7];
					pol.priority=Integer.parseInt(policy_str[6]);
					pol.vlanID=Integer.parseInt(policy_str[5]);
					pol.port=Integer.parseInt(policy_str[4]);
					pol.macAddress=policy_str[3];
					pol.ipAddress=policy_str[2];
					pol.name=policy_str[1];
					pol.policyid=Integer.parseInt(policy_str[0]);
					found=true;
					return pol;
				}
			} else {
				System.out.println("Policy with given ID not found");
				break;
			}
		} while (!found);
		return null;
	}
	
	/**
	 * Write Policies to file
	 * @param : HashMap containing all policy Policys
	 * @param : fileName where policies should be stored 
	 */
	
	public static void writePolicies(String fileName, HashMap<Integer, Policy> policyStorage){
		CSVWriter csvWriter=null;
		try {
			//Open the writer
			csvWriter= new CSVWriter(new FileWriter(fileName),',');
			Set <Integer> keySet = policyStorage.keySet();
			//Write the policies
			Policy nextPolicy;
			for (Integer key:keySet){
				nextPolicy=(Policy) policyStorage.get(key);
				writePolicy(nextPolicy, csvWriter);
			}
			csvWriter.close();
		} catch (Exception e){
			System.err.println("Error: "+e.getMessage());
			e.printStackTrace();
		}finally{
			try {
				if (csvWriter!=null){
					//Close the writer
					csvWriter.close();
				}
			}catch(IOException e){
				System.err.println("Error in closing file during exception");				
			}
		}
	}
	
	/**
	 * Reads all the policies into a HashMap with policyID as key
	 * @param policyFileName
	 * @return HashMap containing Policy Object
	 */
	public static HashMap<Integer, Policy>  readPolicies(String fileName) {
		
		//Make HashMap for storage of policies
		HashMap<Integer, Policy> policy_storage= new HashMap<Integer, Policy>();
		CSVReader inputFile=null;
		try {
			//open file reader
			inputFile= new CSVReader(new FileReader("/home/hafeez/Desktop/policyfile"),',');
			String[] policy_str;
	
			while ((policy_str=inputFile.readNext())!=null){
				//save the policy
				Policy pol=new Policy();
				pol.decision=policy_str[7];
				pol.priority=Integer.parseInt(policy_str[6]);
				pol.vlanID=Integer.parseInt(policy_str[5]);
				pol.port=Integer.parseInt(policy_str[4]);
				pol.macAddress=policy_str[3];
				pol.ipAddress=policy_str[2];
				pol.name=policy_str[1];
//				pol.policyid=pol.generateID(1193);
				pol.policyid=Integer.parseInt(policy_str[0]);
				policy_storage.put( pol.policyid,pol);
			}
			inputFile.close();
			return policy_storage;
			
		} catch (Exception e){
			System.err.println("Error: "+e.getMessage());
			e.printStackTrace();
			return null;
		} finally{
			try {
				if (inputFile!=null){
					//Close the writer
					inputFile.close();
				}
			}catch(IOException e){
				System.err.println("Error in closing file during exception");				
			}
		}
		
	}
	
	/**
	 * Compare if calling policy is same as some other policy
	 * @param policy
	 * @return true if policies are same, false otherwise
	 */
	
	public boolean isSimilar(Policy policy){
		if (this.name.equals(policy.policyid)){
			System.out.println("Both policies are same");
			return true;
		}else {
			System.out.println("Both policies are same");
			return false;			
		}
	}
	
	/**
	 * Main Function
	 * @throws IOException 
	 */
	
	public static void main (String[] args) {
//		Policy pol= new Policy();
		try {
//			CSVWriter csvWriter= new CSVWriter(new FileWriter("/home/hafeez/Desktop/policyfile"),',');
//			CSVReader csvReader= new CSVReader(new FileReader("/home/hafeez/Desktop/policyfile"),',');
//			pol.writePolicy(pol,csvWriter);
//			pol.readPolicy(csvReader, 0);
//			csvWriter.close();
//			csvReader.close();
//			
//			HashMap<Integer, Policy> policy_store= new HashMap<Integer, Policy>();
//			
//			policy_store=readPolicies("/home/hafeez/Desktop/policyfile");
//			System.out.println("Policies read");
//			
//			
//			System.out.println(policy_store.keySet().toString());
//			Policy[] keyset=policy_store.keySet().toArray();
//			Policy myPol=null;
//			//Updates the keys
//			for (int i=0;i<keyset.length;i++){
//				myPol=(Policy) policy_store.get(keyset[i]);
//				myPol.policyid=myPol.generateID(2191);
//				policy_store.put(myPol.policyid, myPol);
//			}
//			
//			//Make a copy of third policy
//			Policy myPolicy=new Policy((Policy) policy_store.get(228114751));
//			System.out.println(myPolicy.toString());
//			
//			System.out.println("new policy created");
//			
//			// Change the name of that policy
//			myPolicy.changePolicyName(policy_store, "See 1 2 3 4 5");
//			System.out.println("name updated");
////			
////			// change the priority
////			myPolicy.changePolicyPriority(policy_store,2011);
////			System.out.println("Priority updated");
////			
////			// change the decision
////			myPolicy.changePolicyDecision(policy_store, "Allow this policy right now");
////			System.out.println("Decision updated");
////			
////			// add this policy to HashMap
////			policy_store.put(myPolicy.policyid, myPolicy);
////			System.out.println("policy added");
////			
////			// Copy this policy
////			myPolicy.generatePolicyCopy(policy_store);
////			System.out.println("policy copied");
//			
//			//write HashMap to the file
//			writePolicies("/home/hafeez/Desktop/policyfile", policy_store);
//			System.out.println("policies written");
//			
//			//manual garbage collection
//			System.gc();

			
		} catch (Exception e) {
			System.out.println("Something wrong with File Read/ Write");
			e.printStackTrace();
		}
		
	}
	//End class
}

	
