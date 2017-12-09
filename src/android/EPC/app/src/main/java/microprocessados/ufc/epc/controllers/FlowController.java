package microprocessados.ufc.epc.controllers;

/**
 * Created by Icriarli on 08/12/2017.
 */

public class FlowController {

    private double aquaConsume;

    public FlowController(){
        aquaConsume =0;
    }
    public double getAquaConsume(){
        return aquaConsume;
    }
    public void setAquaConsume(double aquaConsume){
        this.aquaConsume = aquaConsume;
    }

    public void addAquaConsume(double aquaConsume){
        this.aquaConsume += aquaConsume;
    }

}
