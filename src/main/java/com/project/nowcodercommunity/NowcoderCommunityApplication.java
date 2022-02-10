package com.project.nowcodercommunity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NowcoderCommunityApplication {

    private final Logger logger = LoggerFactory.getLogger(NowcoderCommunityApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NowcoderCommunityApplication.class, args);
    }


}
