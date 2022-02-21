package com.project.nowcodercommunity.event;

import com.alibaba.fastjson.JSONObject;
import com.project.nowcodercommunity.entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import sun.dc.pr.PRError;

/**
 * 生产者
 */
@Component
public class EventProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    //处理事件(本质是发送一个消息)
    public void fireEvent(Event event) {
        //将事件到发布到指定的主题
        kafkaTemplate.send(event.getTopic(), JSONObject.toJSONString(event));
    }

}
