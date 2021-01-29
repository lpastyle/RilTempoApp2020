package com.example.riltempoapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class was created with a POJO generator (http://www.jsonschema2pojo.org/) from the
 * following JSON sample:
 *
 * {
 *   "PARAM_NB_J_BLANC":19,
 *   "PARAM_NB_J_ROUGE":4,
 *   "PARAM_NB_J_BLEU":193
 * }
 *
 */
public class TempoDaysLeft {
    @SerializedName("PARAM_NB_J_BLANC")
    @Expose
    private Integer pARAMNBJBLANC;
    @SerializedName("PARAM_NB_J_ROUGE")
    @Expose
    private Integer pARAMNBJROUGE;
    @SerializedName("PARAM_NB_J_BLEU")
    @Expose
    private Integer pARAMNBJBLEU;

    public Integer getPARAMNBJBLANC() {
        return pARAMNBJBLANC;
    }

    public void setPARAMNBJBLANC(Integer pARAMNBJBLANC) {
        this.pARAMNBJBLANC = pARAMNBJBLANC;
    }

    public Integer getPARAMNBJROUGE() {
        return pARAMNBJROUGE;
    }

    public void setPARAMNBJROUGE(Integer pARAMNBJROUGE) {
        this.pARAMNBJROUGE = pARAMNBJROUGE;
    }

    public Integer getPARAMNBJBLEU() {
        return pARAMNBJBLEU;
    }

    public void setPARAMNBJBLEU(Integer pARAMNBJBLEU) {
        this.pARAMNBJBLEU = pARAMNBJBLEU;
    }
}
