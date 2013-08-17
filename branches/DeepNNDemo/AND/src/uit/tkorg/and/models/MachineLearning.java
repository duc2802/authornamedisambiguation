/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.models;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.trees.J48;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.lazy.IBk;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.Utils;

/**
 *
 * @author DucHuynh
 */
public class MachineLearning {
    
    public Classifier cModel;
    
    public Instances train;
    public Instances test;
    public LibSVM svm;
    
    public MachineLearning(TypeClassifier type)
    {        
        switch(type){
            case RF:
                cModel = (Classifier)new RandomForest();
                break;
            case BY:
                cModel = (Classifier)new NaiveBayes();
                break;
            case SVM:
                {     svm = new LibSVM();
//                    SelectedTag kt = new SelectedTag(0, LibSVM.TAGS_KERNELTYPE);
//                    SelectedTag svmt = new SelectedTag(0, LibSVM.TAGS_SVMTYPE);
//                    svm.setKernelType(kt);
//                    svm.setSVMType(svmt);
//                    svm.setProbabilityEstimates(true);
                }    
                cModel = svm;
                break;
            case C45:
                cModel = (Classifier)new J48();
                break; 
            case KNN:
                cModel = (Classifier)new IBk();
                break; 
        }
    }
    
    public String execute() throws Exception
    {   
           
        return null;
    }

    public Instances getTest() {
        return test;
    }

    public void setTest(Instances test) {
        this.test = test;
    }

    public Instances getTrain() {
        return train;
    }

    public void setTrain(Instances train) {
        this.train = train;
    }
    
    public enum TypeClassifier {
       RF, SVM, BY,C45, KNN
     }
}
