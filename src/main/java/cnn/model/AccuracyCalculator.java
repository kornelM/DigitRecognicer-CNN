package cnn.model;

import logger.NeuralNetworkLogger;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.earlystopping.scorecalc.ScoreCalculator;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

public class AccuracyCalculator implements ScoreCalculator<MultiLayerNetwork> {

    private NeuralNetworkLogger networkLogger = new NeuralNetworkLogger();
    private final MnistDataSetIterator mnistDataSetIterator;
    private int iterationNumber = 0;

    public AccuracyCalculator(MnistDataSetIterator mnistDataSetIterator) {
        this.mnistDataSetIterator = mnistDataSetIterator;
    }

    @Override
    public double calculateScore(MultiLayerNetwork multiLayerNetwork) {
        Evaluation evaluation = multiLayerNetwork.evaluate(mnistDataSetIterator);
        double accuracy = evaluation.accuracy();
        networkLogger.logAccuracy(accuracy, iterationNumber++);
        return 1 - evaluation.accuracy();
    }
}
