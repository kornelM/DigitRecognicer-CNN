package cnn;

public class NetworkParams {
    private int inputChannels;
    private int outputChannels;
    private int testBatchSize;
    private int epochs;
    private int iterations;
    private int seed;

    public int getInputChannels() {
        return inputChannels;
    }

    public void setInputChannels(int inputChannels) {
        this.inputChannels = inputChannels;
    }

    public int getOutputChannels() {
        return outputChannels;
    }

    public void setOutputChannels(int outputChannels) {
        this.outputChannels = outputChannels;
    }

    public int getTestBatchSize() {
        return testBatchSize;
    }

    public void setTestBatchSize(int testBatchSize) {
        this.testBatchSize = testBatchSize;
    }

    public int getEpochs() {
        return epochs;
    }

    public void setEpochs(int epochs) {
        this.epochs = epochs;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) {
        this.seed = seed;
    }

    public NetworkParamsBuilder getBuilder() {
        return new NetworkParamsBuilder();
    }

    public static class NetworkParamsBuilder {
        //default values
        private int inputChannels = 1;
        private int outputChannels = 10;
        private int testBatchSize = 64;
        private int epochs = 20;
        private int iterations = 1;
        private int seed = 123;
        private NetworkParams networkParams;

        public NetworkParamsBuilder setInputChannels(int inputChannels) {
            this.inputChannels = inputChannels;
            return this;
        }

        public NetworkParamsBuilder setOutputChannels(int outputChannels) {
            this.outputChannels = outputChannels;
            return this;
        }

        public NetworkParamsBuilder setTestBatchSize(int testBatchSize) {
            this.testBatchSize = testBatchSize;
            return this;
        }

        public NetworkParamsBuilder setEpochs(int epochs) {
            this.epochs = epochs;
            return this;
        }

        public NetworkParamsBuilder setIterations(int iterations) {
            this.iterations = iterations;
            return this;
        }

        public NetworkParamsBuilder setSeed(int seed) {
            this.seed = seed;
            return this;
        }

        public NetworkParams build() {
            this.networkParams = new NetworkParams();
            this.networkParams.setEpochs(epochs);
            this.networkParams.setInputChannels(inputChannels);
            this.networkParams.setOutputChannels(outputChannels);
            this.networkParams.setIterations(iterations);
            this.networkParams.setSeed(seed);
            this.networkParams.setTestBatchSize(testBatchSize);

            return networkParams;
        }

        public NetworkParams getNetworkParams() {
            return this.networkParams;
        }
    }
}