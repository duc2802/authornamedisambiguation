/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uit.tkorg.and.core.classifications;

import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.ml.data.folded.FoldedDataSet;

/**
*
* @author jauten
* @author THNghiep
*/
public class ModifiedFoldedDataSet extends FoldedDataSet {

private int numFolds;
private int foldSize;
//private int lastFoldSize;

/**
* Create a folded dataset.
*
* @param theUnderlying The underlying folded dataset.
*/
public ModifiedFoldedDataSet(final MLDataSet theUnderlying) {
super(theUnderlying);

}

public ModifiedFoldedDataSet(final MLDataSet theUnderlying,int numFolds,int foldSize) {
super(theUnderlying);
this.numFolds=numFolds;
this.foldSize=foldSize;
}

@Override
public void getRecord(long index, MLDataPair pair) {
int currentOffset = this.foldSize * super.getCurrentFold();
if (index >= currentOffset) {
super.getUnderlying().getRecord(index + this.foldSize, pair);
} else {
super.getUnderlying().getRecord(index, pair);
}

}

@Override
public long getRecordCount() {
int total = (int) super.getUnderlying().getRecordCount();
return total - super.getCurrentFoldSize();
}

public MLDataSet getValidationDataSet(){
BasicMLDataSet validationDataSet=new BasicMLDataSet();

for(int i=super.getCurrentFoldOffset();i<(super.getCurrentFoldOffset()+super.getCurrentFoldSize());i++){
final MLDataPair pair = BasicMLDataPair.createPair(super.getInputSize(),super.getIdealSize());
super.getUnderlying().getRecord(i, pair);
validationDataSet.add(pair);
}
return validationDataSet;
}

@Override
public MLDataSet openAdditional() {
final ModifiedFoldedDataSet folded = new ModifiedFoldedDataSet(super.getUnderlying().openAdditional(),numFolds,foldSize);
folded.setOwner(this);
return folded;
}

@Override
public void fold(int theNumFolds) {

this.numFolds = (int) Math.min(theNumFolds, super.getUnderlying().getRecordCount());
this.foldSize = (int) (super.getUnderlying().getRecordCount() / this.numFolds);
// this.lastFoldSize = (int) this.foldSize;
// this.lastFoldSize += (int) (super.getUnderlying().getRecordCount() - (this.foldSize * this.numFolds));
super.fold(theNumFolds);
}
}