package cnn;

import cnn.model.AccuracyCalculator;
import cnn.model.LabeledImage;
import logger.NeuralNetworkLogger;
import org.deeplearning4j.datasets.iterator.impl.MnistDataSetIterator;
import org.deeplearning4j.earlystopping.EarlyStoppingConfiguration;
import org.deeplearning4j.earlystopping.EarlyStoppingResult;
import org.deeplearning4j.earlystopping.saver.LocalFileGraphSaver;
import org.deeplearning4j.earlystopping.saver.LocalFileModelSaver;
import org.deeplearning4j.earlystopping.termination.MaxEpochsTerminationCondition;
import org.deeplearning4j.earlystopping.termination.MaxTimeIterationTerminationCondition;
import org.deeplearning4j.earlystopping.trainer.EarlyStoppingTrainer;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.activations.Activation;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

//todo add documentation
public class ConvolutionalNeuralNetwork {

    private final NeuralNetworkLogger networkLogger = new NeuralNetworkLogger();
    private MultiLayerNetwork multiLayerNetwork;

    private static final String OUTPUT_DIRECTORY = "resources/cnnCurrentTrainingModels";
    private static final String TRAINED_MODEL_FILE = "resources/cnnTrainedModels/bestModel.bin";


    //todo add documentation
    public void init() {
        try {
            multiLayerNetwork = ModelSerializer.restoreMultiLayerNetwork(new File(TRAINED_MODEL_FILE));
        } catch (IOException e) {
            networkLogger.logException("File with pretrained model does not exists! Train model before calling this method!", e);
        }
    }

    //todo add documentation
    public int predict(LabeledImage labeledImage) {
        double[] pixels = labeledImage.getPixels();

        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = pixels[i] / 255d;
        }
        int[] predictedNumbers = multiLayerNetwork.predict(Nd4j.create(pixels));

        return predictedNumbers[0];
    }

    //todo add documentation
//    public void train(int trainDataSize, int testDataSize) {
//        NetworkParams networkParams = new NetworkParams.NetworkParamsBuilder()
//                .setInputChannels(1)
//                .setOutputChannels(10)
//                .setTestBatchSize(64)
//                .setEpochs(20)
//                .setIterations(1)
//                .setSeed(123)
//                .build();
//
//        boolean binarize = false;
//        boolean train = true, shuffle = true;
//
//
//        int randomSeed = 12345;
//        try {
//            MnistDataSetIterator mnistTrain = new MnistDataSetIterator(networkParams.getTestBatchSize(), trainDataSize, binarize, train, shuffle, randomSeed);
//
//
//            MultiLayerConfiguration configuration = new NeuralNetConfiguration.Builder()
//                    .seed(networkParams.getSeed())
//                    .iterations(networkParams.getIterations())
//                    .regularization(false)
//                    .learningRate(0.01)
//                    .weightInit(WeightInit.SIGMOID_UNIFORM)
//                    .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
//                    .updater(Updater.ADAM)
//                    .list()
//                    .layer(0, new ConvolutionLayer.Builder(5, 5) //first layer
//                            .nIn(networkParams.getInputChannels())
//                            .stride(1, 1)
//                            .nOut(networkParams.getOutputChannels())
//                            .activation(Activation.IDENTITY)
//                            .build())
//                    .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
//                            .kernelSize(2, 2)
//                            .stride(2, 2)
//                            .build())
//                    .layer(2, new ConvolutionLayer.Builder(5, 5)
//                            .nIn(20)
//                            .stride(1, 1)
//                            .nOut(50)
//                            .activation(Activation.IDENTITY)
//                            .build())
//                    .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
//                            .kernelSize(2, 2)
//                            .stride(2, 2)
//                            .build())
//                    .layer(4, new DenseLayer.Builder().activation(Activation.RELU)
//                            .nIn(800)
//                            .nOut(128)
//                            .build())
//                    .layer(5, new DenseLayer.Builder().activation(Activation.RELU)
//                            .nIn(128)
//                            .nOut(64)
//                            .build())
//                    .layer(6, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
//                            .nOut(networkParams.getOutputChannels())
//                            .activation(Activation.SOFTMAX)
//                            .build())
//                    .setInputType(InputType.convolutionalFlat(28, 28, 1))
//                    .backprop(true).pretrain(false).build();
//
//            EarlyStoppingConfiguration esConf = new EarlyStoppingConfiguration.Builder()
//                    .epochTerminationConditions(new MaxEpochsTerminationCondition(networkParams.getEpochs()))
//                    .iterationTerminationConditions(new MaxTimeIterationTerminationCondition(75, TimeUnit.MINUTES))
//                    .scoreCalculator(new AccuracyCalculator(
//                            new MnistDataSetIterator(testDataSize, testDataSize, false, false, true, 12345)))
//                    .evaluateEveryNEpochs(1)
//                    .modelSaver(new LocalFileModelSaver(OUTPUT_DIRECTORY))
//                    .build();
//            EarlyStoppingTrainer stoppingTrainer = new EarlyStoppingTrainer(esConf, configuration, mnistTrain);
//
//            EarlyStoppingResult stoppingResult = stoppingTrainer.fit();
//
//            networkLogger.logDetails(stoppingResult);
//
//        } catch (IOException e) {
//            //todo add logger and exception handling
//            e.printStackTrace();
//        }
//    }

    public void train(Integer trainDataSize, Integer testDataSize) throws IOException {
        int nChannels = 1; // Number of input channels
        int outputNum = 10; // The number of possible outcomes
        int batchSize = 64; // Test batch size
        int nEpochs = 20; // Number of training epochs
        int iterations = 1; // Number of training iterations
        int seed = 123; //

        MnistDataSetIterator mnistTrain = new MnistDataSetIterator(batchSize, trainDataSize, false, true, true, 12345);

        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
                .seed(seed)
                .iterations(iterations)
                .regularization(false)
                .learningRate(0.01)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.NESTEROVS)
                .list()
                .layer(0, new ConvolutionLayer.Builder(5, 5)
                        .nIn(nChannels)
                        .stride(1, 1)
                        .nOut(20)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(2, new ConvolutionLayer.Builder(5, 5)
                        .nIn(20)
                        .stride(1, 1)
                        .nOut(50)
                        .activation(Activation.IDENTITY)
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(2, 2)
                        .stride(2, 2)
                        .build())
                .layer(4, new DenseLayer.Builder().activation(Activation.RELU)
                        .nIn(800)
                        .nOut(128).build())
                .layer(5, new DenseLayer.Builder().activation(Activation.RELU)
                        .nIn(128)
                        .nOut(64).build())
                .layer(6, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(outputNum)
                        .activation(Activation.SOFTMAX)
                        .build())
                .setInputType(InputType.convolutionalFlat(28, 28, 1))
                .backprop(true).pretrain(false).build();

        EarlyStoppingConfiguration esConf = new EarlyStoppingConfiguration.Builder()
                .epochTerminationConditions(new MaxEpochsTerminationCondition(nEpochs))
                .iterationTerminationConditions(new MaxTimeIterationTerminationCondition(75, TimeUnit.MINUTES))
                .scoreCalculator(new AccuracyCalculator(
                        new MnistDataSetIterator(testDataSize, testDataSize, false, false, true, 12345)))
                .evaluateEveryNEpochs(1)
                .modelSaver(new LocalFileModelSaver(OUTPUT_DIRECTORY))
                .build();

        EarlyStoppingTrainer trainer = new EarlyStoppingTrainer(esConf, conf, mnistTrain);

        EarlyStoppingResult result = trainer.fit();

        System.out.println("Termination reason: " + result.getTerminationReason());
        System.out.println("Termination details: " + result.getTerminationDetails());
        System.out.println("Total epochs: " + result.getTotalEpochs());
        System.out.println("Best epoch number: " + result.getBestModelEpoch());
        System.out.println("Score at best epoch: " + result.getBestModelScore());
        networkLogger.logDetails(result);
    }
    public static void main(String[] args) throws IOException {
        new ConvolutionalNeuralNetwork().train(10000, 1000);
    }
}
