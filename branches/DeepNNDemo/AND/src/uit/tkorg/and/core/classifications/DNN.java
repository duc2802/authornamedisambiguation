/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.classifications;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationElliottSymmetric;
import org.encog.engine.network.activation.ActivationSoftMax;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;
import org.encog.ml.train.MLTrain;
import org.encog.ml.train.strategy.end.EarlyStoppingStrategy;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.cross.CrossValidationKFold;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

/**
 *
 * @author THNghiep
 */
public class DNN {
    private BasicNetwork network;

    public DNN() {
        this.network = new BasicNetwork();
    }
    
    public DNN(BasicNetwork network) {
        this.network = network;
    }

    /**
     * @return the network
     */
    public BasicNetwork getNetwork() {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(BasicNetwork network) {
        this.network = network;
    }
    
    public void train(MLDataSet trainingSet) {
        
        // Tune size of DNN
        int numHiddenLayer = 1;
        int numHiddenUnit = 100;
        
        
        // Construct DNN
        network.addLayer(new BasicLayer(null,true,trainingSet.getInputSize()));
        // Add hidden layers
        for (int i = 0; i < numHiddenLayer; i++)
        {
            // Softsign activation function
            network.addLayer(new BasicLayer(new ActivationElliottSymmetric(),true,numHiddenUnit));
//            // Design rectifier activation function
//            network.addLayer(new BasicLayer(new ActivationRamp(Double.POSITIVE_INFINITY, 0, Double.POSITIVE_INFINITY, 0),true,numHiddenUnit));
        }
        network.addLayer(new BasicLayer(new ActivationSoftMax(),false,trainingSet.getIdealSize()));
        // Finalize structure.
        network.getStructure().finalizeStructure();
        network.reset();
        // NguyenWidrow default.
        //(new NguyenWidrowRandomizer()).randomize(network);

        
        // Train DNN, rprop trainer will modify network.
        final FoldedDataSet folded = new FoldedDataSet(trainingSet); 
        final ResilientPropagation train = new ResilientPropagation(network, folded);
        // Auto set number of threads for multi-threading
        train.setThreadCount(0);
        // MSE cost.
//        // Early stopping when validation set no longer improve.
//        train.addStrategy(new EarlyStoppingStrategy(trainingSet, trainingSet));

        final CrossValidationKFold trainFolded = new CrossValidationKFold(train,5);

        int epoch = 1;
        boolean isFinish = false;
        double oldError = 1;
        do {
                trainFolded.iteration();
                
                System.out.println("Epoch #" + epoch + " Error:" + trainFolded.getError());
                epoch++;
                
                double error = trainFolded.getError();
                if (error - oldError > 0) {
                    isFinish = true;
                }
                
        } while (!isFinish);
        
        // EncogUtility.calculateClassificationError(network, CVSet);
        trainFolded.finishTraining();
        train.finishTraining();
        

        Encog.getInstance().shutdown();
    }
}
