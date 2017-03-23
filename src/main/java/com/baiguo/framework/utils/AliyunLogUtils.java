package com.baiguo.framework.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.openservices.log.Client;
import com.aliyun.openservices.log.common.Consts.CursorMode;
import com.aliyun.openservices.log.common.Histogram;
import com.aliyun.openservices.log.common.LogContent;
import com.aliyun.openservices.log.common.LogGroupData;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.exception.LogException;
import com.aliyun.openservices.log.request.GetHistogramsRequest;
import com.aliyun.openservices.log.request.GetLogsRequest;
import com.aliyun.openservices.log.request.ListLogStoresRequest;
import com.aliyun.openservices.log.request.PutLogsRequest;
import com.aliyun.openservices.log.response.BatchGetLogResponse;
import com.aliyun.openservices.log.response.GetCursorResponse;
import com.aliyun.openservices.log.response.GetHistogramsResponse;
import com.aliyun.openservices.log.response.GetLogsResponse;


public class AliyunLogUtils {
	private static Logger log = LoggerFactory.getLogger(AliyunLogUtils.class);
	
	private static Client client = null;
	
	private static String endpoint = ""; // 选择与上面步骤创建Project所属区域匹配的Endpoint
	private static String accessKeyId = ""; // 使用你的阿里云访问秘钥AccessKeyId
	private static String accessKeySecret = ""; // 使用你的阿里云访问秘钥AccessKeySecret
	private static String project = ""; // 上面步骤创建的项目名称
	private static String logstore = ""; // 上面步骤创建的日志库名称

	static {
		Properties prop = new Properties();
		try {
			prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("aliyunlog.properties"));
			endpoint = prop.getProperty("endpoint").trim();
			accessKeyId = prop.getProperty("accessKeyId").trim();
			accessKeySecret = prop.getProperty("accessKeySecret").trim();
			project = prop.getProperty("project").trim();
			logstore = prop.getProperty("logstore").trim();
			createClient();
		} catch (IOException e) {
			log.error("初始化数据失败", e);
		}
	}
	
	/**
	 * 构建一个客户端实例
	 * @return
	 */
	public static Client createClient() {
		if(client == null) {
			client = new Client(endpoint, accessKeyId, accessKeySecret);
		}
        return client;
	}
	/**
	 * 列出当前Project下的所有日志库名称
	 * @param offset 获取日志库下标，即从哪里开始
	 * @param size 获取日志库数量
	 * @return
	 */
	public static List<String> showListLogStoreNames(int offset, int size, String logStoreSubName) {
        ListLogStoresRequest req1 = new ListLogStoresRequest(project, offset, size, logStoreSubName);
        List<String> logStores = null;
		try {
			logStores = client.ListLogStores(req1).GetLogStores();
			log.info("获取到的阿里云日志库名称：" + logStores.toString());
		} catch (LogException e) {
			log.error("获取阿里云日志库名称失败", e);
		}
        return logStores;
	}
	/**
	 * 写日志
	 * @param topic
	 * @param source
	 */
	public static void writeLogs(String index, String topic, String source, Date now) {
		log.info("source:---->"+source);
        Vector<LogItem> logGroup = new Vector<LogItem>();
        LogItem logItem = new LogItem((int) (now.getTime() / 1000));
        logItem.PushBack("index", index);
        logGroup.add(logItem);
        PutLogsRequest req2 = new PutLogsRequest(project, logstore, topic, source, logGroup);
        try {
			client.PutLogs(req2);
		} catch (LogException e) {
			log.error("写入阿里云日志失败", e);
		}
	}
	
	public static void getLog(int shard_id, Date curTime, int fromTime) throws LogException {
		// 把0号shard中，最近1分钟写入的数据都读取出来。
        long curTimeInSec = curTime.getTime() / 1000;
        GetCursorResponse cursorRes = client.GetCursor(project, logstore, shard_id, curTimeInSec - fromTime);
        String beginCursor = cursorRes.GetCursor();
        cursorRes = client.GetCursor(project, logstore, shard_id, CursorMode.END);
        String endCursor = cursorRes.GetCursor();
        String curCursor = beginCursor;
        while (curCursor.equals(endCursor) == false) {
            int loggroup_count = 2; // 每次读取两个loggroup
            BatchGetLogResponse logDataRes = client.BatchGetLog(project, logstore, shard_id, loggroup_count, curCursor);
            List<LogGroupData> logGroups = logDataRes.GetLogGroups();
            for (LogGroupData logGroup : logGroups) {
                System.out.println("Source:" + logGroup.GetSource());
                System.out.println("Topic:" + logGroup.GetTopic());
                for (LogItem log : logGroup.GetAllLogs()) {
                    System.out.println("LogTime:" + log.GetTime());
                    List<LogContent> contents = log.GetLogContents();
                    for (LogContent content : contents) {
                        System.out.println(content.GetKey() + ":" + content.GetValue());
                    }
                }
            }
            String next_cursor = logDataRes.GetNextCursor();
            log.info("下一个MD5码：" + next_cursor);
            curCursor = next_cursor;
        }
	}
	
	public static void getLogs(String topic) throws LogException, InterruptedException {
		// 查询日志分布情况
        String query = "index";
        int from = (int) (new Date().getTime() / 1000 - 60*60*6);
        int to = (int) (new Date().getTime() / 1000);
        GetHistogramsResponse res3 = null;
        while (true) {
            GetHistogramsRequest req3 = new GetHistogramsRequest(project, logstore, topic, query, from, to);
            res3 = client.GetHistograms(req3);
            if (res3 != null && res3.IsCompleted()) {// IsCompleted()返回true，表示查询结果是准确的，如果返回false，则重复查询
                break;
            }
            Thread.sleep(200);
        }
        System.out.println("Total count of logs is " + res3.GetTotalCount());
        for (Histogram ht : res3.GetHistograms()) {
            System.out.printf("from %d, to %d, count %d.\n", ht.GetFrom(), ht.GetTo(), ht.GetCount());
        }
        // 查询日志数据
        long total_log_lines = res3.GetTotalCount();
        int log_offset = 0;
        int log_line = 10;
        while (log_offset <= total_log_lines) {
            GetLogsResponse res4 = null;
            // 对于每个log offset,一次读取10行log，如果读取失败，最多重复读取3次。
            for (int retry_time = 0; retry_time < 3; retry_time++) {
                GetLogsRequest req4 = new GetLogsRequest(project, logstore, from, to, topic, query, log_offset, log_line, false);
                res4 = client.GetLogs(req4);
                if (res4 != null && res4.IsCompleted()) {
                    break;
                }
                Thread.sleep(200);
            }
            System.out.println("Read log count:" + String.valueOf(res4.GetCount()));
            log_offset += log_line;
        }
	}
	
	public static void writeLogs(String index, String topic, String[] keys, Object[] values, Date now) {
		JSONObject json = new JSONObject();
		for(int i = 0; i < keys.length; i++) {
			json.put(keys[i], values[i]);
		}
		writeLogs(index, topic, json.toString(), now);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//showListLogStoreNames(0, 100, "");
		
		//writeLogs("ToTaskDetail", "任务详情", "{\"user\":34760,\"child\":10000,\"userTest\":1000,\"addr\":\"127.127.127.127\",\"agent\":\"iPhone; CPU iPhone OS 9_3_2 like Mac OS X\"}", new Date());
//		try {
//			getLog(0, new Date(), 60*60*24);
//		} catch (LogException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
