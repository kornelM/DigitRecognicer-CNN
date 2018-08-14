package logger;

import org.deeplearning4j.earlystopping.EarlyStoppingResult;

public interface NetworkLogger {

    void logDetails(EarlyStoppingResult result);

    void logException(String message, Exception e);

    void logAccuracy(double accuracy, int iteration);
}
