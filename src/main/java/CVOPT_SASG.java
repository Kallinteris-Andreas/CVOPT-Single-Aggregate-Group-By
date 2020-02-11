import org.apache.flink.api.common.serialization.SerializationSchema;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import java.util.Properties;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer;
import org.apache.flink.streaming.util.serialization.JSONKeyValueDeserializationSchema;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.shaded.jackson2.com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;
import org.apache.flink.streaming.api.windowing.time.Time;

public class CVOPT_SASG {
    public static void main(String[] args) throws Exception {
        final int port;
        final int MEM_BUDGET;
        final int aggregationAttributeIndex;
        {
            final ParameterTool params = ParameterTool.fromArgs(args);
            try {
                port = params.getInt("port");
            } catch (Exception e) {
                System.err.println("No port specified. Please provide '--port <port>'");
                return;
            }
            try {
                MEM_BUDGET = params.getInt("mem") * 1024 * 1024;
            } catch (Exception e) {
                System.err.println("Memory Budget not specified. Please provide '--mem <MiB of memory>'");
                return;
            }
            try {
                aggregationAttributeIndex = params.getInt("aggregationIndex");
            } catch (Exception e) {
                System.err.println("No aggregation specified. Please provide '--aggregationIndex <index>'");
                return;
            }
        }
        final StreamExecutionEnvironment env = org.apache.flink.streaming.api.environment.StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:" + port);
        properties.setProperty("group.id", "ECE622");
        FlinkKafkaConsumer<ObjectNode> inputConsumer = (FlinkKafkaConsumer<ObjectNode>) new FlinkKafkaConsumer<>("inputTopic", new JSONKeyValueDeserializationSchema(false), properties).setStartFromEarliest();
        FlinkKafkaProducer outputProducer = new FlinkKafkaProducer<>(
                "localhost:" + port,            // broker list
                "outputTopic",                  // target topic
                (SerializationSchema<Tuple2<String, Integer>>) new BasicSerializer2());
        outputProducer.setWriteTimestampToKafka(true);
        DataStream<ObjectNode> inputStream = env.addSource(inputConsumer);
        DataStream<String> text = inputStream
                .map(new MapFunction<ObjectNode, String>() {
                    private static final long serialVersionUID = 1L;
                    @Override
                    public  String map(ObjectNode node) throws Exception {
                        return  node.get("value").toString();
                    }
                });

    }
}

