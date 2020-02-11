import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;

class BasicSerializer2<T0,T1> implements SerializationSchema<Tuple2< T0, T1>> {
	private static final long serialVersionUID = 1L;
/*
	@Override
    public byte[] serializeKey(Tuple2 element) {
      return ("\"" + element.getField(0).toString() + "\"").getBytes();
    }

    @Override
    public byte[] serializeValue(Tuple2 element) {
      String value = element.getField(1).toString();
      return value.getBytes();
    }
*/
	public String getTargetTopic(Tuple2<T0, T1> element) {
		return null;
	}

	@Override
	public byte[] serialize(Tuple2<T0, T1> element) {
		return ("\""+element.getField(0).toString()+ ","+element.getField(1).toString() +"\"").getBytes();
	}
}
