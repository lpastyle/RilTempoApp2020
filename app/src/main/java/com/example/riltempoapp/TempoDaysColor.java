package com.example.riltempoapp;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * This class was created with a POJO generator (http://www.jsonschema2pojo.org/) from the
 * following JSON sample:
 * {
 *   "JourJ1": {
 *     "Tempo":"TEMPO_ROUGE"
 *   }
 *   ,
 *   "JourJ":{
 *     "Tempo":"TEMPO_ROUGE"
 *   }
 * }
 */
public class TempoDaysColor {
    @SerializedName("JourJ1")
    @Expose
    private JourJ1 jourJ1;
    @SerializedName("JourJ")
    @Expose
    private JourJ jourJ;

    public JourJ1 getJourJ1() {
        return jourJ1;
    }

    public void setJourJ1(JourJ1 jourJ1) {
        this.jourJ1 = jourJ1;
    }

    public JourJ getJourJ() {
        return jourJ;
    }

    public void setJourJ(JourJ jourJ) {
        this.jourJ = jourJ;
    }

    public static class JourJ {

        @SerializedName("Tempo")
        @Expose
        private TempoColor tempo;

        public TempoColor getTempo() {
            return tempo;
        }

        public void setTempo(TempoColor tempo) {
            this.tempo = tempo;
        }

    }

    public static class JourJ1 {

        @SerializedName("Tempo")
        @Expose
        private TempoColor tempo;

        public TempoColor getTempo() {
            return tempo;
        }

        public void setTempo(TempoColor tempo) {
            this.tempo = tempo;
        }


    }
}