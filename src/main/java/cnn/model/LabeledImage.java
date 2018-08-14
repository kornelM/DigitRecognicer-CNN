package cnn.model;


import org.apache.spark.ml.linalg.Vector;
import org.apache.spark.ml.linalg.Vectors;


public class LabeledImage {
    private final double[] meanNormalizedPixel;
    private final double[] pixels;
    private double label;
    private Vector features;

    public LabeledImage(int label, double[] pixels) {
        meanNormalizedPixel = averageNormalizeFeatures(pixels);
        this.pixels = pixels;
        features = Vectors.dense(meanNormalizedPixel);
        this.label = label;
    }

    private double[] averageNormalizeFeatures(double[] pixels) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        double sum = 0;

        for (double pixel : pixels) {
            sum += pixel;
            if (pixel > max) {
                max = pixel;
            }
            if (pixel < min) {
                min = pixel;
            }
        }

        double average = sum / pixels.length;

        double[] normalizedPixels = new double[pixels.length];

        for (int i = 0; i < pixels.length; i++) {
            normalizedPixels[i] = (pixels[i] - average) / (max - min);
        }

        return normalizedPixels;
    }

    public double[] getPixels() {
        return pixels;
    }

    public double getLabel() {
        return label;
    }

    public Vector getFeatures() {
        return features;
    }

    public void setLabel(double label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "LabeledImage{" +
                "label=" + label +
                '}';
    }
}
