package Producer.ECE622;//package Producer.ECE622;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;

//Create java class named “SimpleProducer”

public class SimpleProducer {
 
 public static void main(String[] args) throws Exception{
	String fileName = "population.csv";
    
    //Assign topicName to string variable
    String topicName = "inputTopic";
    // create instance for properties to access producer configs   
    Properties props = new Properties();
    //Assign localhost id
    props.put("bootstrap.servers", "localhost:9092");  
    //Set acknowledgements for producer requests.      
    props.put("acks", "all");
    //If the request fails, the producer can automatically retry,
    props.put("retries", 0);    
    //Specify buffer size in config
    props.put("batch.size", 16384);
    
    //Reduce the no of requests less than 0   
    props.put("linger.ms", 0);
    
    //The buffer.memory controls the total amount of memory available to the producer for buffering.   
    props.put("buffer.memory", 33554432);
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    Producer<String, String> producer = new KafkaProducer<String, String>(props); 
    
    BufferedReader br = new BufferedReader(new FileReader(fileName));
    String line = br.readLine();
        while (line != null) {
        	//System.out.println(line);
        	String[] words = line.split(",");	   
	        producer.send(new ProducerRecord<String, String>(topicName, words[1] ,words[7]));
            line = br.readLine();
	      }
     br.close();
     producer.close();
             
 }
}
