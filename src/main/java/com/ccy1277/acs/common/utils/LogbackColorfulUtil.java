package com.ccy1277.acs.common.utils;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.pattern.color.ANSIConstants;
import ch.qos.logback.core.pattern.color.ForegroundCompositeConverterBase;

/**
 * 自定义控制台日志等级颜色
 * created by ccy on 2022/5/8
 */
public class LogbackColorfulUtil extends ForegroundCompositeConverterBase<ILoggingEvent> {

    @Override
    protected String getForegroundColorCode(ILoggingEvent event) {
        Level level = event.getLevel();
        switch (level.toInt()) {
            //ERROR等级为红色
            case Level.ERROR_INT:
                return ANSIConstants.RED_FG;
            //WARN等级为黄色
            case Level.WARN_INT:
                return ANSIConstants.YELLOW_FG;
            //INFO等级为蓝色
            case Level.INFO_INT:
                return ANSIConstants.BLUE_FG;
            //DEBUG等级为绿色
            case Level.DEBUG_INT:
                return ANSIConstants.GREEN_FG;
            //其他为默认颜色
            default:
                return ANSIConstants.DEFAULT_FG;
        }
    }
}