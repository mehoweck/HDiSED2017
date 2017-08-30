package pl.meho.fuel.utils;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.LayoutBase;

public class LogSuperLayout extends LayoutBase<ILoggingEvent> {

    @Override
    public String doLayout(ILoggingEvent event) {
        StringBuffer sbuf = new StringBuffer(128);
        return sbuf
            .append(event.getTimeStamp())
            .append(" ")
            .append(event.getLevel())
            .append(" [")
            .append(event.getThreadName())
            .append("] ")
            .append(event.getLoggerName())
            .append(" - ")
            .append(event.getFormattedMessage())
            .append(CoreConstants.LINE_SEPARATOR)
            .toString();
    }

}
