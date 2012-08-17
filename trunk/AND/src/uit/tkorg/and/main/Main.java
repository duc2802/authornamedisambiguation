/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.main;

import uit.tkorg.and.models.Vector;
import weka.classifiers.Classifier;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.Evaluation;
import weka.core.Instances;
import weka.core.Utils;

/**
 *
 * @author Admin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
        String pathTrain = "DataTrain";
        String pathTest = "DataTest";
        
        Instances train = Vector.buildDataVetor(pathTrain, "same");
        Instances test = Vector.buildDataVetor(pathTest, "same"); 
        
        Classifier cModel = (Classifier)new RandomForest();  
        cModel.buildClassifier(train);        
        
        // Test the model
        Evaluation eTest = new Evaluation(test);
        eTest.evaluateModel(cModel, test);
        
        System.out.println("# \t actual \t predicted \t error \t distribution");
        for (int i = 0; i < test.numInstances(); i++) {
            double pred = cModel.classifyInstance(test.instance(i));
            double actual = test.instance(i).classValue();
            double[] dist = cModel.distributionForInstance(test.instance(i));
            System.out.print((i + 1));
            System.out.print(" \t ");
            System.out.print(test.instance(i).toString(test.classIndex()));
            System.out.print(" \t ");
            System.out.print(test.classAttribute().value((int) pred));
            System.out.print(" \t ");
            if (pred != test.instance(i).classValue()) {
                System.out.print("false");
            } else {
                System.out.print("correct");
            }
            System.out.print(" \t ");
            System.out.print(Utils.arrayToString(dist));
            System.out.println();
        }
        System.out.println(eTest.toSummaryString());
        System.out.println(eTest.toClassDetailsString());
        System.out.println(eTest.toMatrixString());
    }
}
