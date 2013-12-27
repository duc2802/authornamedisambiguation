/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.classifications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.encog.Encog;
import org.encog.engine.network.activation.ActivationElliottSymmetric;
import org.encog.engine.network.activation.ActivationLinear;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.end.EarlyStoppingStrategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import org.encog.util.simple.EncogUtility;
import uit.tkorg.and.gui.Main;

/**
 *
 * @author THNghiep
 */
public class DNN {
    private BasicNetwork[] networks;

    public DNN() {
        this.networks = new BasicNetwork[5];
    }
    
    public DNN(BasicNetwork[] network) {
        this.networks = network;
    }

    /**
     * @return the network
     */
    public BasicNetwork[] getNetwork() {
        return networks;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(BasicNetwork[] network) {
        this.networks = network;
    }
    
    public void train(MLDataSet trainingSets, MLDataSet testSet, int numHiddenLayer, int numHiddenUnit) {
        try {
//// Test data:
//double testArrayInput[][] = 
//{{0.1883, 0.3033, 0.1517, 0.1046, 0.0523, 0.0418, 0.0157, 0.0000},
//{0.0000, 0.0105, 0.0575, 0.1412, 0.2458, 0.3295, 0.3138, 0.2040},
//{0.1464, 0.1360, 0.1151, 0.0575, 0.1098, 0.2092, 0.4079, 0.6381},
//{0.5387, 0.3818, 0.2458, 0.1831, 0.0575, 0.0262, 0.0837, 0.1778},
//{0.3661, 0.4236, 0.5805, 0.5282, 0.3818, 0.2092, 0.1046, 0.0837},
//{0.0262, 0.0575, 0.1151, 0.2092, 0.3138, 0.4231, 0.4362, 0.2495},
//{0.2500, 0.1606, 0.0638, 0.0502, 0.0534, 0.1700, 0.2489, 0.2824},
//{0.3290, 0.4493, 0.3201, 0.2359, 0.1904, 0.1093, 0.0596, 0.1977},
//{0.1883, 0.3033, 0.1517, 0.1046, 0.3358, 0.3504, 0.3708, 0.2500},
//{0.3651, 0.5549, 0.5272, 0.4268, 0.3478, 0.1820, 0.1600, 0.0366},
//{0.1036, 0.4838, 0.8075, 0.6585, 0.4435, 0.3562, 0.2014, 0.1192},
//{0.0534, 0.1260, 0.4336, 0.6904, 0.6846, 0.6177, 0.4702, 0.3483},
//{0.3138, 0.2453, 0.2144, 0.1114, 0.0837, 0.0335, 0.0214, 0.0356},
//{0.6522, 0.5036, 0.3483, 0.3373, 0.2537, 0.2296, 0.0973, 0.0298},
//{0.0758, 0.1778, 0.2354, 0.2254, 0.2484, 0.2207, 0.1470, 0.0528},
//{0.0424, 0.0131, 0.0000, 0.0073, 0.0262, 0.0638, 0.0727, 0.1851},
//{0.2395, 0.2150, 0.1574, 0.1250, 0.0816, 0.0345, 0.0209, 0.0094},
//{0.0445, 0.0868, 0.1898, 0.2594, 0.3358, 0.3504, 0.3708, 0.2500},
//{0.1438, 0.0445, 0.0690, 0.2976, 0.6354, 0.7233, 0.5397, 0.4482},
//{0.3379, 0.1919, 0.1266, 0.0560, 0.0785, 0.2097, 0.3216, 0.5152},
//{0.6522, 0.5036, 0.3483, 0.3373, 0.2829, 0.2040, 0.1077, 0.0350},
//{0.0225, 0.1187, 0.2866, 0.4906, 0.5010, 0.4038, 0.3091, 0.2301},
//{0.2458, 0.1595, 0.0853, 0.0382, 0.1966, 0.3870, 0.7270, 0.5816},
//{0.5314, 0.3462, 0.2338, 0.0889, 0.0591, 0.0649, 0.0178, 0.0314},
//{0.1689, 0.2840, 0.3122, 0.3332, 0.3321, 0.2730, 0.1328, 0.0685},
//{0.0356, 0.0330, 0.0371, 0.1862, 0.3818, 0.4451, 0.4079, 0.3347},
//{0.2186, 0.1370, 0.1396, 0.0633, 0.0497, 0.0141, 0.0262, 0.1276},
//{0.2197, 0.3321, 0.2814, 0.3243, 0.2537, 0.2296, 0.0973, 0.0298},
//{0.0188, 0.0073, 0.0502, 0.2479, 0.2986, 0.5434, 0.4215, 0.3326},
//{0.1966, 0.1365, 0.0743, 0.0303, 0.0873, 0.2317, 0.3342, 0.3609},
//{0.4069, 0.3394, 0.1867, 0.1109, 0.0581, 0.0298, 0.0455, 0.1888},
//{0.4168, 0.5983, 0.5732, 0.4644, 0.3546, 0.2484, 0.1600, 0.0853}};
//
//double testArrayIdeal[][] = 
//{{0.3379}, {0.1919}, {0.1266}, {0.0560}, {0.0785}, {0.2097}, {0.3216}, {0.5152},
//{0.6522}, {0.5036}, {0.3483}, {0.3373}, {0.2829}, {0.2040}, {0.1077}, {0.0350},
//{0.0225}, {0.1187}, {0.2866}, {0.4906}, {0.5010}, {0.4038}, {0.3091}, {0.2301},
//
//{0.0262}, {0.0575}, {0.0837}, {0.1203}, {0.0659}, {0.1428}, {0.4838}, {0.8127}};
//
//// create training data
//trainingSet = new BasicMLDataSet(testArrayInput, testArrayIdeal);
//
////{0.0502, 0.1736}, {0.4843, 0.7929}, {0.7128, 0.7045}, {0.4388, 0.3630},
////{0.1647, 0.0727}, {0.0230, 0.1987}, {0.7411, 0.9947}, {0.9665, 0.8316},
////{0.5873, 0.2819}, {0.1961, 0.1459}, {0.0534, 0.0790}, {0.2458, 0.4906},
////{0.5539, 0.5518}, {0.5465, 0.3483}, {0.3603, 0.1987}, {0.1804, 0.0811},

//        // Tune size of DNN
//        int numHiddenLayer = 1;
//        int numHiddenUnit = 100;
            BasicMLDataSet[] data = separateData(trainingSets);
            for (int k = 0; k < 5; k++) {
                // Construct DNN
                BasicNetwork network = new BasicNetwork();
                BasicMLDataSet trainingSet = getTrainingSet(data, k);
                BasicMLDataSet cvSet = data[k];
                network.addLayer(new BasicLayer(null,true,trainingSet.getInputSize()));
                // Add hidden layers
                for (int i = 0; i < numHiddenLayer; i++)
                {
                    // Softsign activation function
                    network.addLayer(new BasicLayer(new ActivationElliottSymmetric(),true,numHiddenUnit));
//                    // Design rectifier activation function
//                    // network.addLayer(new BasicLayer(new ActivationRamp(Double.POSITIVE_INFINITY, 0, Double.POSITIVE_INFINITY, 0),true,numHiddenUnit));
                }
                network.addLayer(new BasicLayer(new ActivationSigmoid(),false,trainingSet.getIdealSize()));
                // Finalize structure.
                network.getStructure().finalizeStructure();
                network.reset();
                // NguyenWidrow default.
                //(new NguyenWidrowRandomizer()).randomize(network);


                // Train DNN, rprop trainer will modify network.
//                final FoldedDataSet folded = new FoldedDataSet(trainingSet); 
//                final ResilientPropagation train = new ResilientPropagation(network, folded);
                final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
                // Set 0 for Auto set number of threads for multi-threading
                train.setThreadCount(0);
                // MSE cost.
                // Validation early stopping when validation set no longer improve.
//                train.addStrategy(new EarlyStoppingStrategy(cvSet, testSet));
                // Encog k-fold.
//                final CrossValidationKFold trainFolded = new CrossValidationKFold(train,5);

                int epoch = 1;
                int countBad = 0;
                int countClassBad = 0;
                int badThreshold = 1;
                double oldError = 1;
                double oldClassificationAccuracy = 0;

                do {
//                        trainFolded.iteration();
//                        double error = trainFolded.getError();

                        train.iteration();
                        double error = train.getError();
                        double trainingClassificationAccuracy = DNN.singleAccuracy(network, cvSet);
                        double classificationAccuracy = DNN.singleAccuracy(network, testSet);

                        System.out.println("Epoch #" + epoch + " MSE Error: " + error + " Training error: " + (1 - trainingClassificationAccuracy) + " Classification error: " + (1 - classificationAccuracy));
                        epoch++;



//                        if (error <= oldError) {
//                            countBad = 0;
//                        }
//                        else {
//                            countBad++;
//                            if (countBad >= badThreshold) {
//                                countBad = Integer.MIN_VALUE;
//                                System.out.println("Boom countBad");
//                                if (error < 0.1) {
//                                    Main.taLog.append("1. Bad. Epoch #" + epoch + " MSE Error: " + error + " Training error: " + (1 - trainingClassificationAccuracy) + " Classification error: " + (1 - classificationAccuracy) + "\n");
//                                }
//                            }
//                        }
                        if (classificationAccuracy >= oldClassificationAccuracy) {
                            countClassBad = 0;
                        }
                        else {
                            countClassBad++;
                            if (countClassBad >= badThreshold) {
                                countClassBad = Integer.MIN_VALUE;
                                System.out.println("Boom countClassBad");
                                if (classificationAccuracy > 0.95) {
                                    Main.taLog.append("2. Class Bad. Epoch #" + epoch + " MSE Error: " + error + " Training error: " + (1 - trainingClassificationAccuracy) + " Classification error: " + (1 - classificationAccuracy) + "\n");
                                }
                            }
                        }

                        oldError = error;
                        oldClassificationAccuracy = classificationAccuracy;

//                } while (countBad < badThreshold && countClassBad < badThreshold && epoch <= 10000);
                } while (train.getError() > (2^(-6)));
//                } while (epoch <= 10000);

//                trainFolded.finishTraining();
                train.finishTraining();

                Encog.getInstance().shutdown();
                
                networks[k] = network;
            }
        
        } catch (Exception ex) {
            Main.taLog.append(DNN.class.getName() + " -EXCEPTION: " + ex.getMessage());
            Main.taLog.append("\n");
            throw ex;
        }
    }
    
    public static double calculateAccuracy(BasicNetwork[] net, MLDataSet dataset) {
        try {
            // Manually test network.
            double accuracy = 0;
            long rightClass = 0;
            MLData output = null;
            double prediction = 0;
            for(MLDataPair pair: dataset ) {
                // Bagging.
                prediction = 0;
                for (int k = 0; k < 5; k++) {
                    output = net[k].compute(pair.getInput());
                    prediction += output.getData(0);
                }
                prediction /= 5;

                if ((prediction >= 0.5) && (pair.getIdeal().getData(0) == 1)) {
                    rightClass++;
                }
                else if ((prediction < 0.5) && (pair.getIdeal().getData(0) == 0)) {
                    rightClass++;
                }
            }
            accuracy = (double) rightClass / dataset.getRecordCount();
            return accuracy;
         } catch (Exception ex) {
            Main.taLog.append(DNN.class.getName() + " -EXCEPTION: " + ex.getMessage());
            Main.taLog.append("\n");
            throw ex;
        }

    }
    
    public static double singleAccuracy(BasicNetwork net, MLDataSet dataset) {
        try {
            // Manually test network.
            double accuracy = 0;
            long rightClass = 0;
            for(MLDataPair pair: dataset ) {
                final MLData output = net.compute(pair.getInput());
                if ((output.getData(0) >= 0.5) && (pair.getIdeal().getData(0) == 1)) {
                    rightClass++;
                }
                else if ((output.getData(0) < 0.5) && (pair.getIdeal().getData(0) == 0)) {
                    rightClass++;
                }
            }
            accuracy = (double) rightClass / dataset.getRecordCount();
            return accuracy;
         } catch (Exception ex) {
            Main.taLog.append(DNN.class.getName() + " -EXCEPTION: " + ex.getMessage());
            Main.taLog.append("\n");
            throw ex;
        }

    }

    private BasicMLDataSet[] separateData(MLDataSet trainingSets) {
        // chia ra 5 fold bang nhau.
        // moi fold chia ngau nhien.
        // moi class co ti le cac fold nhu nhau.
        // tach rieng class, tron ngau nhien, chia 5 fold, ket hop, tron ngau nhien.
        
        BasicMLDataSet[] foldingSets = new BasicMLDataSet[5];
        BasicMLDataSet sameSet = new BasicMLDataSet();
        BasicMLDataSet diffSet = new BasicMLDataSet();
        BasicMLDataSet[] foldingSameSets = new BasicMLDataSet[5];
        BasicMLDataSet[] foldingDiffSets = new BasicMLDataSet[5];
        
        long numItemTotal = trainingSets.getRecordCount();
        for (int i = 0; i < numItemTotal; i++) {
            if (trainingSets.get(i).getIdeal().getData(0) == 1) {
                sameSet.add(trainingSets.get(i));
            } else {
                diffSet.add(trainingSets.get(i));
            }
        }

        sameSet = permutation(sameSet);
        diffSet = permutation(diffSet);
        
        foldingSameSets = divide(sameSet);
        foldingDiffSets = divide(diffSet);
        
        List l = null;
        for (int i = 0; i < 5; i++) {
            l = foldingSameSets[i].getData();
            l.addAll(foldingDiffSets[i].getData());
            foldingSets[i].setData(l);
            foldingSets[i] = permutation(foldingSets[i]);
        }
        
        return foldingSets;
    }

    private BasicMLDataSet permutation(BasicMLDataSet set) {
        List l = set.getData();
        Collections.shuffle(l);
        set.setData(l);
        return set;
    }

    private BasicMLDataSet[] divide(BasicMLDataSet set) {
        BasicMLDataSet[] s = new BasicMLDataSet[5];
        List l = set.getData();
        int size = (int) set.getRecordCount();
        int foldSize = size / 5;
        for (int i = 0; i < 4; i++) {
            s[i].setData(l.subList(i * foldSize, (i + 1) * foldSize));
        }
        s[4].setData(l.subList(4 * foldSize, size - 1));
        return s;
    }

    private BasicMLDataSet getTrainingSet(BasicMLDataSet[] data, int k) {
        BasicMLDataSet d = new BasicMLDataSet();
        List l = new ArrayList();
        for (int i = 0; i < 5; i++) {
            if (i != k) {
                l.addAll(data[i].getData());
            }
        }
        d.setData(l);
        
        return d;
    }

    public static void main(String args[]) {
            DNN dnn = new DNN();
            dnn.train(null, null, 0, 0);
            Encog.getInstance().shutdown();
    }
}
