package logger;

import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class NeuralNetworkLogger implements NetworkLogger {

    private final Logger logger = LoggerFactory.getLogger("NeuralNetworkLogger");

    @Override
    public void logDetails(EarlyStoppingResult result) {
        logger.info("Termination reason: {}\n" +
                        "Termination details: {}\n" +
                        "Total epochs: {}\n" +
                        "Best epoch number: {}\n" +
                        "Score at best epoch: {}\n",
                result.getTerminationReason(),
                result.getTerminationDetails(),
                result.getTotalEpochs(),
                result.getBestModelEpoch(),
                result.getBestModelScore());
    }

    @Override
    public void logException(String message, Exception e) {
        logger.error("{}; {}",
                message,
                Arrays.toString(e.getStackTrace()));
    }

    @Override
    public void logAccuracy(double accuracy, int iteration) {
        logger.debug("Iteration: {}; Accuracy: {}",
                iteration,
                accuracy);
    }
}
