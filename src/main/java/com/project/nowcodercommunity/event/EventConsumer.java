package com.project.nowcodercommunity.event;

import com.alibaba.fastjson.JSONObject;
import com.project.nowcodercommunity.entity.Event;
import com.project.nowcodercommunity.entity.Message;
import com.project.nowcodercommunity.service.MessageService;
import com.project.nowcodercommunity.util.CommunityConstant;
import com.project.nowcodercommunity.util.CommunityUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 消费者
 */
@Component
public class EventConsumer implements CommunityConstant {

    private static final Logger logger = LoggerFactory.getLogger(EventConsumer.class);//记日志

    @Autowired
    private MessageService messageService;

    @KafkaListener(topics = {TOPIC_COMMENT, TOPIC_LIKE, TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record) {
        if (record == null || record.value() == null) {
            logger.error("消息的内容为空！");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(), Event.class);//将Json恢复成字符串
        if (event == null) {
            logger.error("消息格式错误！");
            return;
        }

        //发送站内系统通知
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);//发布者是系统
        message.setToId(event.getEntityUserId());//发送对象
        message.setConversationId(event.getTopic());//存主题
        message.setCreateTime(new Date());//设置当前时间

        Map<String, Object> content = new HashMap<>();
        content.put("userId", event.getUserId());
        content.put("entityType", event.getEntityType());
        content.put("entityId", event.getEntityId());

        if (!event.getData().isEmpty()) {
            for (Map.Entry<String, Object> entry : event.getData().entrySet()) {
                content.put(entry.getKey(), entry.getValue());
            }
        }

        message.setContent(JSONObject.toJSONString(content));
        messageService.addMessage(message);

    }
}
