# Elasticsearch start container

```bash
docker rm elasticsearch; \
rm -rf ~/elasticsearch; \
docker run -p 9200:9200 -p 9300:9300 --name elasticsearch \
--volume=$HOME/elasticsearch/data:/usr/share/elasticsearch/data \
--volume=$HOME/elasticsearch/logs:/usr/share/elasticsearch/logs \
-e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.5.4
```  

# Node4j start container

```bash
docker rm neo4j; \
rm -rf ~/neo4j; \
docker run \
         --name=neo4j \
         --publish=7474:7474 --publish=7687:7687 \
         --volume=$HOME/neo4j/data:/data \
         --volume=$HOME/neo4j/logs:/logs \
         neo4j:3.1.4
```