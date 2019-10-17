
package com.example.firebase.moiveguide;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Movietrailer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Newresults> results;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Newresults> getResults() {
        return results;
    }

    public void setResults(List<Newresults> results) {
        this.results = results;
    }

}
