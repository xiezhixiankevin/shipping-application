package com.ship.order.service.impl;

import cn.itcast.feign.pojo.LogisticsRecord;
import com.ship.order.mapper.LogisticsRecordMapper;
import com.ship.order.service.LogisticsRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xzx
 * @since 2023-04-29
 */
@Service
public class LogisticsRecordServiceImpl extends ServiceImpl<LogisticsRecordMapper, LogisticsRecord> implements LogisticsRecordService {

}
