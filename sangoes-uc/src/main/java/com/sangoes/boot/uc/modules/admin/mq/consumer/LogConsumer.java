package com.sangoes.boot.uc.modules.admin.mq.consumer;

import com.sangoes.boot.uc.constants.RabbitConstants;
import com.sangoes.boot.uc.modules.admin.entity.SysLog;
import com.sangoes.boot.uc.modules.admin.service.ISysLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Copyright (c) sangoes 2018
 * https://github.com/sangoes
 *
 * @author jerrychir
 * @date 2019 2019/1/12 10:07 PM
 */
@Slf4j
@Component
public class LogConsumer {

    @Autowired
    private ISysLogService logService;

    /**
     * 日志保存
     *
     * @param sysLog
     */
    @RabbitListener(queues = {RabbitConstants.LOG_DIRECT_QUEUE})
    public void logListenerAutoAck(SysLog sysLog) {
        // 保存数据库
        logService.save(sysLog);
    }
}