package com.thinkit.cloud.springbootes.util.es.elasticsearch;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.ResourceAlreadyExistsException;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.WriteRequest.RefreshPolicy;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhongkexinli.micro.serv.common.bean.RestApiResult2;
import com.zhongkexinli.micro.serv.common.bean.RestApiResultBuilder;

/**
 * Java操作ES，使用RestHighLevelClient
 *
 */
public class EsCrud {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsCrud.class);

    // 话单索引分片数
    private static final Integer SHARDS = 24;

    // 话单索引副本数
    private static final Integer REPLICAS = 1;

    /**
     * 检查索引是否存在
     */
    public static boolean indexExists(RestHighLevelClient restHighLevelClient, String index) {
        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(index);

        try {
            boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
            if (exists) {
                LOGGER.info("索引{}存在", index);
                return true;
            } else {
                LOGGER.info("索引{}不存在", index);
                return false;
            }
        } catch (ElasticsearchException | IOException e) {
            LOGGER.error("索引{}检查是否存在时程序发生异常", index);
            LOGGER.error("", e);
        }

        return false;
    }
    
    /**
     * 创建索引
     *
     * @param restHighLevelClient
     * @param index
     * @param shards
     * @param replicas
     */
    public static void createIndex(RestHighLevelClient restHighLevelClient, String index, Integer shards, Integer replicas) {
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        // 用于索引搜索的 from+size 的最大值，默认为 10000
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", shards)
                .put("index.number_of_replicas", replicas)
                .put("index.max_result_window", 2000000000));

        // 同步执行
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            // 返回的CreateIndexResponse允许检索有关执行的操作的信息，如下所示
            // 指示是否所有节点都已确认请求
            boolean acknowledged = createIndexResponse.isAcknowledged();

            if (acknowledged) {
                LOGGER.info("索引{}创建成功", index);
            } else {
                LOGGER.info("索引{}创建失败", index);
            }
        } catch (ResourceAlreadyExistsException | IOException e) {
            LOGGER.error("索引{}创建时程序发生异常", index);
            LOGGER.error("", e);
        }
    }
    

    /**
     * 创建索引
     */
    public static void createIndex(RestHighLevelClient restHighLevelClient, String index) {
        // 创建索引
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(index);
        // 用于索引搜索的 from+size 的最大值，默认为 10000
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", SHARDS)
                .put("index.number_of_replicas", REPLICAS)
                .put("index.max_result_window", 2000000000));

        // 同步执行
        try {
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);

            // 返回的CreateIndexResponse允许检索有关执行的操作的信息，如下所示
            // 指示是否所有节点都已确认请求
            boolean acknowledged = createIndexResponse.isAcknowledged();

            if (acknowledged) {
                LOGGER.info("索引{}创建成功", index);
            } else {
                LOGGER.info("索引{}创建失败", index);
            }
        } catch (ResourceAlreadyExistsException | IOException e) {
            LOGGER.error("索引{}创建时程序发生异常", index);
            LOGGER.error("", e);
        }
    }
    

    /**
     * 语音情报分析系统话单Mapping
     *
     * @param restHighLevelClient
     * @param index
     * @param type
     */
    public static void formatMapping(RestHighLevelClient restHighLevelClient, String index, String type) {
        PutMappingRequest putMappingRequest = new PutMappingRequest(index);
        putMappingRequest.type(type);

        try {
            XContentBuilder xContentBuilder = XContentFactory.jsonBuilder();
            xContentBuilder.startObject()
                    .startObject("properties")
                    .startObject("serialNumber").field("type", "keyword").endObject()
                    .startObject("insertTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("callStartTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("callEndTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("callNumber").field("type", "keyword").endObject()
                    .startObject("calledNumber").field("type", "keyword").endObject()
                    .startObject("callDirection").field("type", "long").endObject()
                    .startObject("isEachRecord").field("type", "long").endObject()
                    .startObject("lineType").field("type", "long").endObject()
                    .startObject("channelNumber").field("type", "long").endObject()
                    .startObject("signalType").field("type", "long").endObject()
                    .startObject("holdDuration").field("type", "double").endObject()
                    .startObject("isImportance").field("type", "long").endObject()
                    .startObject("handleState").field("type", "long").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "keyword").endObject()
                    .startObject("reserve3").field("type", "keyword").endObject()
                    .startObject("reserve4").field("type", "keyword").endObject()
                    .startObject("reserve5").field("type", "keyword").endObject()
                    .startObject("reserve6").field("type", "long").endObject()
                    .startObject("reserve7").field("type", "long").endObject()
                    .startObject("reserve8").field("type", "double").endObject()
                    .startObject("reserve9").field("type", "double").endObject()
                    .startObject("reserve10").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("reserve11").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                   // .startObject("yiWuReason").field("type", "keyword").endObject()
                   // .startObject("yiLouYiWuClassTop").field("type", "keyword").endObject()
                   // .startObject("yiLouYiWuType").field("type", "keyword").endObject()
                    .startObject("audioList").field("type", "nested")
                    .startObject("properties")
                    .startObject("audioNumber").field("type", "keyword").endObject()
                    .startObject("audioPath").field("type", "keyword").endObject()
                    .startObject("audioLength").field("type", "double").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "keyword").endObject()
                    .startObject("reserve3").field("type", "keyword").endObject()
                    .startObject("reserve4").field("type", "long").endObject()
                    .startObject("reserve5").field("type", "double").endObject()
                    .startObject("reserve6").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("engineList").field("type", "nested")
                    .startObject("properties")
                    .startObject("engineName").field("type", "keyword").endObject()
                    .startObject("engineStatus").field("type", "long").endObject()
                    .startObject("confirmTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("confirmEndTime").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "keyword").endObject()
                    .startObject("reserve3").field("type", "long").endObject()
                    .startObject("reserve4").field("type", "double").endObject()
                    .startObject("reserve5").field("type", "date").field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd").endObject()
                    .startObject("engineResult").field("type", "nested")
                    .startObject("properties")
                    .startObject("serialNumber").field("type", "long").endObject()
                    .startObject("result").field("type", "keyword").endObject()
                    .startObject("remark").field("type", "keyword").endObject()
                    .startObject("startTime").field("type", "double").endObject()
                    .startObject("duration").field("type", "double").endObject()
                    .startObject("confidence").field("type", "double").endObject()
                    .startObject("judgeState").field("type", "long").endObject()
                    .startObject("reserve1").field("type", "keyword").endObject()
                    .startObject("reserve2").field("type", "long").endObject()
                    .startObject("reserve3").field("type", "double").endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject()
                    .endObject();

            putMappingRequest.source(xContentBuilder);

            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().putMapping(putMappingRequest, RequestOptions.DEFAULT);
            if (acknowledgedResponse.isAcknowledged()) {
                LOGGER.info("{}.{}格式化成功", index, type);
            } else {
                LOGGER.info("{}.{}格式化失败", index, type);
            }
        } catch (ElasticsearchException | IOException e) {
            LOGGER.error("{}.{}格式化时程序发生异常", index, type);
            LOGGER.error("", e);
        }
    }


    /**
     * 根据index、type、id 修改ES数据
     */
    public static RestApiResult2  updateDoc(RestHighLevelClient restHighLevelClient, String index, String type, String id, String json) {
        UpdateRequest updateRequest = new UpdateRequest(index, type, id);
        updateRequest.doc(json, XContentType.JSON);

        UpdateResponse updateResponse;
        try {
        	 updateRequest.setRefreshPolicy(RefreshPolicy.IMMEDIATE);
        	 
            updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                LOGGER.info("ES更新成功，version :{} " , updateResponse.getVersion());
            } else {
                LOGGER.info("ES更新失败，version {} " , updateResponse.getVersion());
            }
        } catch (ElasticsearchStatusException e) {
            if (e.status().getStatus() == 409) {
                LOGGER.info("捕捉到版本冲突异常，进行延时更新。。。");
                for (int i = 0; i < 3; i++) {
                    try {
                        Thread.sleep(200);
                        updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
                        LOGGER.info("第{}次延时更新。。。", i);
                        if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
                            LOGGER.info("更新成功！");
                            return new RestApiResultBuilder().respCode(1).respData("更新成功").build();
                        } else {
                            LOGGER.info("更新失败");
                        }
                    } catch (InterruptedException | IOException e1) {
                        LOGGER.error("ES更新时程序发生异常");
                        LOGGER.error(e1.getMessage());
                    }
                }
                
                return new RestApiResultBuilder().respCode(0).respData("更新es失败").build();
            } else {
                LOGGER.info("捕捉到ES状态异常，状态码：{};状态信息：{}", e.status().getStatus(), e.status().name());
                return new RestApiResultBuilder().respCode(0).respData("更新es失败").build();
            }

        } catch (IOException e) {
            LOGGER.error("ES更新时程序发生异常");
            LOGGER.error("", e);
            
            return new RestApiResultBuilder().respCode(0).respData("更新es失败").build();
        }
        
        return new RestApiResultBuilder().respCode(1).respData("更新成功").build();
    }

    /**
     * 根据id查询信息
     */
    public static String get(RestHighLevelClient restHighLevelClient, String index, String type, String id) {
        GetRequest getRequest = new GetRequest(index, type, id);
        try {
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);

            if (getResponse.isExists()) {
                LOGGER.info("【{}】查询成功！", id);
                return getResponse.getSourceAsString();
            } else {
                boolean selected = false;
                for (int i = 0; i < 3; i++) {
                    getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
                    if (getResponse.isExists()) {
                        selected = true;
                        break;
                    } else {
                        Thread.sleep(1000);
                    }
                }
                if (selected) {
                    LOGGER.info("【{}】查询成功！", id);
                    return getResponse.getSourceAsString();
                } else {
                    LOGGER.info("未找到【{}】", id);
                    return null;
                }
            }
        } catch (ElasticsearchException | IOException | InterruptedException e) {
            LOGGER.error("【{}】查询时程序发生异常", id);
            LOGGER.error("", e);

            return null;
        }
    }

    /**
     * 根据id删除信息
     */
    public static boolean deleteDoc(RestHighLevelClient restHighLevelClient, String index, String type, String id) {
        DeleteRequest deleteRequest = new DeleteRequest(index, type, id);

        try {
            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);

            if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
                LOGGER.info("ES删除成功，version :{} " , deleteResponse.getVersion());
                return true;
            } else {
                LOGGER.info("ES删除失败，version : {} " , deleteResponse.getVersion());
                return false;
            }
        } catch (ElasticsearchException | IOException e) {
            LOGGER.error("ES删除发生异常");
            LOGGER.error("", e);
            return false;
        }
    }

    /**
     * 条件查询
     */
    public static List<String> queryByQBuilder(RestHighLevelClient restHighLevelClient, String index, Integer from, Integer size, QueryBuilder queryBuilder) {
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.from(from);
        searchSourceBuilder.size(size);
        searchRequest.source(searchSourceBuilder);

        try {
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);

            SearchHits searchHits = searchResponse.getHits();

            List<String> docList = new ArrayList<>();

            for (SearchHit hit : searchHits) {
                docList.add(hit.getSourceAsString());
            }

            return docList;
        } catch (ElasticsearchException | IOException e) {
            LOGGER.error("条件查询索引{}中数据失败", index);
            LOGGER.error("", e);
            return new ArrayList<>();
        }
    }

}
