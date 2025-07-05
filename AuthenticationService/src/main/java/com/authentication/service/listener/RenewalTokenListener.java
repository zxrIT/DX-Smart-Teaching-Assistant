package com.authentication.service.listener;

import com.common.rabbitmq.constant.RenewalTokenConstant;
import com.common.rabbitmq.entity.RenewalTokenEntity;
import com.common.redis.content.RedisAuthenticationConstant;
import com.common.redis.service.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
@SuppressWarnings("all")
@RequiredArgsConstructor
public class RenewalTokenListener {
    @Autowired
    private final RedisService redisService;

    @RabbitListener(queues = RenewalTokenConstant.QUEUE_NAME)
    public void renewalToken(RenewalTokenEntity renewalTokenEntity) {
        try {
            log.info("用户->{}续期Token->{}", renewalTokenEntity.getUserId(), renewalTokenEntity.getToken());
            redisService.setString(RedisAuthenticationConstant.redisAuthenticationKey + renewalTokenEntity.getUserId(),
                    renewalTokenEntity.getToken(), RedisAuthenticationConstant.redisAuthenticationExpire, TimeUnit.SECONDS);
        } catch (Exception exception) {
            log.error("用户->{}续期Token->{}续期失败原因{}", renewalTokenEntity.getUserId(), renewalTokenEntity.getToken(),
                    exception.getMessage());
        }
    }
}
