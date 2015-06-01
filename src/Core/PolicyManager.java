package Core;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;


public class PolicyManager {

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
	 * @return HashMap containing Policy Policys
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
	
	public static void main (String [] args){
		try {
//			CSVWriter csvWriter= new CSVWriter(new FileWriter("/home/hafeez/Desktop/policyfile"),',');
//			CSVReader csvReader= new CSVReader(new FileReader("/home/hafeez/Desktop/policyfile"),',');
//			pol.writePolicy(pol,csvWriter);
//			pol.readPolicy(csvReader, 0);
//			csvWriter.close();
//			csvReader.close();
//			
			HashMap<Integer, Policy> policy_store= new HashMap<Integer, Policy>();
//			
			policy_store=readPolicies("/home/hafeez/Desktop/policyfile");
			System.out.println("Policies read");
//			
//			
			System.out.println(policy_store.keySet().toString());
			Object[] keyset=policy_store.keySet().toArray();
//			Policy myPol=null;
//			//Updates the keys
//			for (int i=0;i<keyset.length;i++){
//				myPol=(Policy) policy_store.get(keyset[i]);
//				myPol.name="abc"+(i+1);
//				policy_store.put(myPol.policyid, myPol);
//			}
			
			//Make a copy of third policy
			Policy myPolicy=new Policy((Policy) policy_store.get((int) keyset[4]));
//			System.out.println(myPolicy.toString());
//			
//			System.out.println("new policy created");
//			
//			// Change the name of that policy
//			myPolicy.changePolicyName(policy_store, "See :P :P :P :P :P :P :P");
//			System.out.println("name updated: "+myPolicy.name);
//			
//			// change the priority
//			myPolicy.changePolicyPriority(policy_store,2011);
//			System.out.println("Priority updated");
//			
//			// change the decision
//			myPolicy.changePolicyDecision(policy_store, "Allow 123142141");
//			System.out.println("Decision updated");
//			
//			// add this policy to HashMap
//			policy_store.put(myPolicy.policyid, myPolicy);
//			System.out.println("policy added");
//			
//			// Copy this policy
//			myPolicy.generatePolicyCopy(policy_store);
//			System.out.println("policy copied");
//			
//			//write HashMap to the file
//			writePolicies("/home/hafeez/Desktop/policyfile", policy_store);
//			System.out.println("policies written");

			//manual garbage collection
			System.gc();

			
		} catch (Exception e) {
			System.out.println("Something wrong with File Read/ Write");
			e.printStackTrace();
		}
		
	}
}
