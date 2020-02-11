# CVOPT-Single-Aggregate-Group-By

This project is an flink implemention of the first algorithm of this
[work](https://arxiv.org/pdf/1909.02629.pdf) an algorithm for computing a random single
aggregate single group by querry.
This project uses the flink datastream API.


## Getting Started

### Start kafka server
start zookeeper and kafka
```
bin/zookeeper-server-start.sh ./config/zookeeper.properties &
bin/kafka-server-start.sh ./config/server.properties
```
Initliaze the topics (queues)
```
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic inputTopic
bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic OutputTopic
```
then start the class simple producer and run
```
bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic inputTopic` --from-beginning
```
to make sure everything works fine

## Prerequisites

```
apache flink 1.9.1
```

## Installing

```
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Contributing

No Contributions will be accepted feel free to fork it 

## Versioning

We use [SemVer](http://semver.org/) for versioning.

## Authors

* **Kallinteris Andreas** - *Initial work* - [Kallinteris-Andreas](https://github.com/Kallinteris-Andreas)
* **Savaidis Panagiotis** - *Initial work* - [WildTwister](https://github.com/wildtwister)

## License

This project is licensed under the GNU v2 License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

#TODO

