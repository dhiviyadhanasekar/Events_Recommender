import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.conf.Configuration;
import java.io.IOException;


class Scoring
{
	public boolean score(String userID, String eventID) throws IOException{

		Configuration conf = HBaseConfiguration.create();

		//get interested and not-interested events for user
		HTable userInterestsTable = new HTable(conf, "/user/user01/user_interests");
		Get userEventData = new Get(Bytes.toBytes(userID));
		Result userEventResults = userInterestsTable.get(userEventData);
		String interestedEvents = Bytes.toString(userEventResults.getValue(Bytes.toBytes("interested"),Bytes.toBytes("list")));
		String uninterestedEvents = Bytes.toString(userEventResults.getValue(Bytes.toBytes("not_interested"),Bytes.toBytes("list")));
		String[] interestedEventList = interestedEvents.split(",");
		String[] uninterestedEventList = uninterestedEvents.split(",");

		//open event table
		HTable eventDataTable = new HTable(conf, "/user/user01/events");

		Double interestedEventSimilarity = 0.0;
		Double uninterestedEventSimilarity = 0.0;

		//get event data for eventID
		Double[] currentEventVector = eventData(eventID,eventDataTable);
		Double[] interestedEventVector;
		Double[] uninterestedEventVector;

		//get interested event data, find cosineSimilarity and get mean
		for(String event:interestedEventList){
			if (!event.equals("")) {
			    interestedEventVector = eventData(event,eventDataTable);
			    interestedEventSimilarity +=  cosineSimilarity(currentEventVector,interestedEventVector);
			}
		}
		interestedEventSimilarity /= interestedEventList.length;

		//get uninterested event data, find cosineSimilarity and get mean
		for(String event:uninterestedEventList){
			if (!event.equals("")) {
			    uninterestedEventVector = eventData(event,eventDataTable);
			    uninterestedEventSimilarity +=  cosineSimilarity(currentEventVector,uninterestedEventVector);
			}
		}
		uninterestedEventSimilarity /= uninterestedEventList.length;

		//interested = 1, uninterested = 0
		return (interestedEventSimilarity > uninterestedEventSimilarity ? true : false);
	}

	//return event data for eventID
	public Double[] eventData(String eventID, HTable eventDataTable) throws IOException{
		Double[] incomingEventVector = null;
		Get eventData = new Get(Bytes.toBytes(eventID));
		if (eventDataTable.exists(eventData)) {
		    Result eventDataResults = eventDataTable.get(eventData);
		    String incomingEventData = Bytes.toString(eventDataResults.getValue(Bytes.toBytes("description"),Bytes.toBytes("vector")));
		    String[] incomingEventVectorString = incomingEventData.split(",");
		    incomingEventVector = new Double[incomingEventVectorString.length];

		    for (int count = 0; count < incomingEventVectorString.length; count++) {
		        incomingEventVector[count] = Double.parseDouble(incomingEventVectorString[count]);
		    }
		}
		return incomingEventVector;
	}

	/*Cosine Similarity (d1, d2) =  Dot product(d1, d2) / ||d1|| * ||d2||

	Dot product (d1,d2) = d1[0] * d2[0] + d1[1] * d2[1] * … * d1[n] * d2[n]
	||d1|| = square root(d1[0]2 + d1[1]2 + ... + d1[n]2)
	||d2|| = square root(d2[0]2 + d2[1]2 + ... + d2[n]2) */

	public double cosineSimilarity(Double[] firstEvent, Double[] secondEvent){
		if(firstEvent.length != secondEvent.length)
			return Double.MAX_VALUE;

		Double dotProduct = 0.0;
		Double firstEventMagnitude = 0.0;
		Double secondEventMagnitude = 0.0;

		for(int count = 0; count < firstEvent.length; count++){
			dotProduct += firstEvent[count] * secondEvent[count];
			firstEventMagnitude += Math.pow(firstEvent[count],2);
			secondEventMagnitude += Math.pow(secondEvent[count],2);
		}

		firstEventMagnitude = Math.sqrt(firstEventMagnitude);
		secondEventMagnitude = Math.sqrt(secondEventMagnitude);

		if(firstEventMagnitude <= 0.000001 || secondEventMagnitude <= 0.000001)
			return Double.MAX_VALUE;

		return (dotProduct / (firstEventMagnitude * secondEventMagnitude));
	}

	public static void main(String[] args) {
		Scoring scoring = new Scoring();
		String userId = args[0];
		String eventId = args[1];
		try {
		    System.out.println(scoring.score(userId, eventId));
		}
		catch(IOException e) {
		    e.printStackTrace();
		}
	}

}
